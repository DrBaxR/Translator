package com.example.translator.locale

import android.view.View
import android.widget.AdapterView
import com.example.translator.state.LocaleState
import java.util.*

class LocaleSpinnerSelectionListener(
    private val locales: List<Locale>
) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        LocaleState.selectedLocale = locales[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {  }

}