package com.example.translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.translator.contracts.RecognizeSpeech
import com.example.translator.locale.LocaleAdapter
import com.example.translator.locale.LocaleSpinnerSelectionListener
import com.example.translator.state.LocaleState
import java.util.*
import android.util.Log
import android.widget.Button
import org.opencv.android.OpenCVLoader
import android.content.Intent
import com.example.translator.imageRecognition.Recognition
import com.example.translator.imageRecognition.StorageRecognitionActivity


class MainActivity : AppCompatActivity() {
    private lateinit var cameraButton: Button
    private companion object Private private val tag = "MainActivity"
    private lateinit var storage_ImageP_button: Button

    private lateinit var tvResult: TextView
    private lateinit var button: Button
    private var locales: List<Locale> = getUniqueLocales()

    private val getSpeechLauncher = registerForActivityResult(RecognizeSpeech()) { result ->
        tvResult.text = result
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
        spinner.onItemSelectedListener = LocaleSpinnerSelectionListener(locales)
        spinner.adapter = LocaleAdapter(this, locales)

        // other
        button = findViewById(R.id.btnSpeak)
        tvResult = findViewById(R.id.tvResult)
        button.setOnClickListener { getSpeechLauncher.launch(LocaleState.selectedLocale) }


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
}