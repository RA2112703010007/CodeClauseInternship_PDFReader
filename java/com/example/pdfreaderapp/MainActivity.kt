package com.example.pdfreaderapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy

class MainActivity : AppCompatActivity() {

    companion object {
    }

    private lateinit var pdfView: PDFView
    private var documentSectionOpened = false

    private val openDocumentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    displayPdf(uri)
                    documentSectionOpened = true
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pdfView = findViewById(R.id.pdfView)
        openDocumentSection()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (documentSectionOpened) {
            openDocumentSection()
        } else {
            super.onBackPressed()
        }
    }

    private fun openDocumentSection() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        openDocumentLauncher.launch(intent)
    }

    private fun displayPdf(uri: Uri) {
        pdfView.fromUri(uri)
            .defaultPage(0)
            .onLoad {
                // PDF loaded successfully
            }
            .enableSwipe(true)
            .swipeHorizontal(false)
            .pageFitPolicy(FitPolicy.BOTH)
            .load()
    }
}
