package com.example.translator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.translator.contracts.RecognizeSpeech
import com.example.translator.locale.LocaleAdapter
import com.example.translator.state.AppState

import java.util.*
import android.util.Log
import android.widget.Button
import org.opencv.android.OpenCVLoader
import com.example.translator.imageRecognition.Recognition
import com.example.translator.imageRecognition.StorageRecognitionActivity
import com.example.translator.translate.TranslateText
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var cameraButton: Button
    private companion object Private private val tag = "MainActivity"
    private lateinit var storage_ImageP_button: Button

    private lateinit var tvResult: TextView
    private lateinit var button: Button
    private var locales: List<Locale> = getUniqueLocales()
    private lateinit var logoutButton: Button
    private lateinit var firebaseAuth: FirebaseAuth

    private val getSpeechLauncher = registerForActivityResult(RecognizeSpeech()) { result ->
        tvResult.text = result
        val intend = Intent(this, TranslateText::class.java)
        intend.putExtra("message", result)
        startActivity(intend)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(OpenCVLoader.initDebug()) {
            Log.d(tag, "OpenCv installed successfully")
        } else {
            Log.d(tag, "opencv is not installed, aia e")
        }

        // spinner
        val spinner: Spinner = findViewById(R.id.spinner)
//        spinner.onItemSelectedListener = LocaleSpinnerSelectionListener(locales)
        spinner.adapter = LocaleAdapter(this, locales)

        // other
        button = findViewById(R.id.btnSpeak)
        tvResult = findViewById(R.id.tvResult)
        button.setOnClickListener { getSpeechLauncher.launch(AppState.selectedSpeechLocale1) }

        firebaseAuth = FirebaseAuth.getInstance()
        logoutButton = findViewById(R.id.logoutBtn)
        logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            logoutAction()
        }

        cameraButton = findViewById(R.id.camera_button)
        cameraButton.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    Recognition::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }

        storage_ImageP_button = findViewById(R.id.storage_ImageP_button)
        storage_ImageP_button.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    StorageRecognitionActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }

    }
    private fun getUniqueLocales(): List<Locale> {
        val uniqueLanguageLocales = mutableListOf<Locale>()
        Locale.getAvailableLocales().forEach { locale ->
            if (!uniqueLanguageLocales.any { it.displayLanguage == locale.displayLanguage }) {
                uniqueLanguageLocales.add(locale)
            }
        }

        uniqueLanguageLocales.sortWith(compareBy({ it.displayLanguage }, { it.displayLanguage }))

        return uniqueLanguageLocales
    }

    private fun logoutAction() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}