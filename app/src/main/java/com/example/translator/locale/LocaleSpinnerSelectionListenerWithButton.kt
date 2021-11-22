package com.example.translator.locale

import android.view.View
import android.widget.AdapterView
import java.util.*

class LocaleSpinnerSelectionListenerWithButton(
    localeToSet: String,
    val extraBehavior: (locale: Locale) -> Unit
) : LocaleSpinnerSelectionListener(localeToSet) {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        super.onItemSelected(parent, view, position, id)
        extraBehavior(locales[position])
    }
}