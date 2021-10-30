package com.example.translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.translator.contracts.RecognizeSpeech
import com.example.translator.locale.LocaleAdapter
import com.example.translator.locale.LocaleSpinnerSelectionListener
import com.example.translator.state.LocaleState
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var button: Button
    private var locales: List<Locale> = getUniqueLocales()

    private val getSpeechLauncher = registerForActivityResult(RecognizeSpeech()) { result ->
        tvResult.text = result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // spinner
        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.onItemSelectedListener = LocaleSpinnerSelectionListener(locales)
        spinner.adapter = LocaleAdapter(this, locales)

        // other
        button = findViewById(R.id.btnSpeak)
        tvResult = findViewById(R.id.tvResult)
        button.setOnClickListener { getSpeechLauncher.launch(LocaleState.selectedLocale) }
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