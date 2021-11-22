package com.example.translator.state

import com.example.translator.services.GoogleApiTranslator
import java.util.*

class LocaleState {
    companion object {
        val googleApi = GoogleApiTranslator()

        var selectedSpeechLocale1: Locale = Locale.getDefault()
        var selectedSpeechLocale2: Locale = Locale.getDefault()

    }
}