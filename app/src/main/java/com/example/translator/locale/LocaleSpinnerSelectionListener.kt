package com.example.translator.locale

import android.view.View
import android.widget.AdapterView
import com.example.translator.state.LocaleState
import java.util.*

open class LocaleSpinnerSelectionListener(private val localeToSet: String) :
    AdapterView.OnItemSelectedListener {
    companion object {
        var locales: List<Locale> = getUniqueLocales()

        private fun getUniqueLocales(): List<Locale> {
            val uniqueLanguageLocales = mutableListOf<Locale>()
            Locale.getAvailableLocales().forEach { locale ->
                if (!uniqueLanguageLocales.any { it.displayLanguage == locale.displayLanguage }) {
                    uniqueLanguageLocales.add(locale)
                }
            }

            uniqueLanguageLocales.sortWith(
                compareBy(
                    { it.displayLanguage },
                    { it.displayLanguage })
            )

            return uniqueLanguageLocales
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (localeToSet) {
            "s1" -> LocaleState.selectedSpeechLocale1 = locales[position]
            "s2" -> LocaleState.selectedSpeechLocale2 = locales[position]
            "t1" -> LocaleState.selectedTextLocale1 = locales[position]
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}