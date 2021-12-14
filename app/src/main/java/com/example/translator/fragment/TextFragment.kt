package com.example.translator.fragment

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner
import com.example.translator.R
import com.example.translator.locale.*
import com.example.translator.services.PremiumService
import com.example.translator.state.AppState
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.*
import java.util.*

class TextFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseReference = FirebaseDatabase.getInstance().reference
        val uid = FirebaseAuth.getInstance().uid

        val view = inflater.inflate(R.layout.fragment_text, container, false)

        if (view != null) {
            val tTextField2 = view.findViewById<TextInputLayout>(R.id.tTextField2)
            tTextField2.editText?.isFocusable = false
            val tTextField1 = view.findViewById<TextInputLayout>(R.id.tTextField1)

            val autocomplete = view.findViewById<AutoCompleteTextView>(R.id.tSpinner)
            val adapter = AutocompleteLocaleAdapter(view.context, AutocompleteLocale.locales)
            autocomplete.setAdapter(adapter)
            autocomplete.setOnItemClickListener { _, _, pos, _ ->
                val selectedItem = adapter.getItem(pos)
                if (selectedItem != null) {
                    AppState.selectedTextLocale1 = Locale(selectedItem.locale)
                }
            }

            autocomplete.text = SpannableStringBuilder(AppState.selectedTextLocale1.displayLanguage)

            val button = view.findViewById<Button>(R.id.tButton)

            button.setOnClickListener {
                PremiumService.incrementCounter(databaseReference, uid!!, view.context) {
                    translateText(
                        tTextField1.editText?.text?.toString(),
                        tTextField2
                    )
                }
            }
        }

        return view
    }

    private fun translateText(text: String?, textField: TextInputLayout) {
        if (text != null) {
            AppState.googleApi.getTranslateService(context!!)
            textField.editText?.text = SpannableStringBuilder(
                AppState.googleApi.translate(text, AppState.selectedTextLocale1.language)
            )
        }
    }
}