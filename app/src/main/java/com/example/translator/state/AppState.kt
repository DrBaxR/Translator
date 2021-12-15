package com.example.translator.state

import com.example.translator.locale.AutocompleteLocale
import com.example.translator.services.GoogleApiTranslator
import java.util.*

class AppState {
    companion object {
        val googleApi = GoogleApiTranslator()

        var selectedSpeechLocale1: Locale = Locale.getDefault()
        var selectedSpeechLocale2: Locale = Locale.getDefault()
        var selectedTextLocale1: Locale = Locale.getDefault()
        var selectedImageLocale1: Locale = Locale.getDefault()
    }
}