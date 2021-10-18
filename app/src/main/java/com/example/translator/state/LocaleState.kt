package com.example.translator.state

import java.util.*

class LocaleState {
    companion object {
        var selectedLocale: Locale = Locale.getDefault()
    }
}