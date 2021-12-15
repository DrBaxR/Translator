package com.example.translator.fragment

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.translator.R
import com.example.translator.contracts.RecognizeSpeech
import com.example.translator.locale.*
import com.example.translator.services.PremiumService
import com.example.translator.state.AppState
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class SpeechFragment : Fragment() {
    private var sTextField1: TextInputLayout? = null
    private var sTextField2: TextInputLayout? = null
    private var translateLTR: Boolean = true

    private val getSpeechLauncher1 = registerForActivityResult(RecognizeSpeech()) { result ->
        sTextField1?.editText?.text = SpannableStringBuilder(result ?: "")
        if (context != null && result != null) {
            AppState.googleApi.getTranslateService(context!!)
            sTextField2?.editText?.text = SpannableStringBuilder(
                AppState.googleApi.translate(result, AppState.selectedSpeechLocale2.language)
            )
        }
    }

    private val getSpeechLauncher2 = registerForActivityResult(RecognizeSpeech()) { result ->
        sTextField2?.editText?.text = SpannableStringBuilder(result ?: "")
        if (context != null && result != null) {
            AppState.googleApi.getTranslateService(context!!)
            sTextField1?.editText?.text = SpannableStringBuilder(
                AppState.googleApi.translate(result, AppState.selectedSpeechLocale1.language)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_speech, container, false)
        val databaseReference = FirebaseDatabase.getInstance().reference
        val uid = FirebaseAuth.getInstance().uid

        sTextField1 = view?.findViewById(R.id.sTextField1)
        sTextField1?.editText?.isFocusable = false
        sTextField2 = view?.findViewById(R.id.sTextField2)
        sTextField2?.editText?.isFocusable = false

        val swapButton = view?.findViewById<ImageButton>(R.id.swapButton)
        swapButton?.setOnClickListener {
            translateLTR = !translateLTR
            if (translateLTR)
                swapButton.setImageResource(R.drawable.ic_arrow_forward)
            else
                swapButton.setImageResource(R.drawable.ic_arrow_back)
        }

        val speakButton = view?.findViewById<Button>(R.id.speakButton)
        speakButton?.setOnClickListener {
            PremiumService.incrementCounter(databaseReference, uid!!, view.context) {
                if (translateLTR) {
                    getSpeechLauncher1.launch(AppState.selectedSpeechLocale1)
                }
                else {
                    getSpeechLauncher2.launch(AppState.selectedSpeechLocale2)
                }
            }
        }

        val autocomplete1 = view.findViewById<AutoCompleteTextView>(R.id.slSpinner1)
        val adapter1 = AutocompleteLocaleAdapter(view.context, AutocompleteLocale.locales.toMutableList())
        autocomplete1.setAdapter(adapter1)
        autocomplete1.setOnItemClickListener { _, _, pos, _ ->
            val selectedItem = adapter1.getItem(pos)
            if (selectedItem != null) {
                AppState.selectedSpeechLocale1 = Locale(selectedItem.locale)
                resetTextViews(sTextField1, sTextField2)
            }
        }

        val autocomplete2 = view.findViewById<AutoCompleteTextView>(R.id.slSpinner2)
        val adapter2 = AutocompleteLocaleAdapter(view.context, AutocompleteLocale.locales.toMutableList())
        autocomplete2.setAdapter(adapter2)
        autocomplete2.setOnItemClickListener { _, _, pos, _ ->
            val selectedItem = adapter2.getItem(pos)
            if (selectedItem != null) {
                AppState.selectedSpeechLocale2 = Locale(selectedItem.locale)
                resetTextViews(sTextField1, sTextField2)
            }
        }

        autocomplete1.text = SpannableStringBuilder(AppState.selectedSpeechLocale1.displayLanguage)
        autocomplete2.text = SpannableStringBuilder(AppState.selectedSpeechLocale2.displayLanguage)

        return view
    }

    private fun resetTextViews(vararg textViews: TextInputLayout?) {
        textViews.forEach {
            it?.editText?.text = SpannableStringBuilder("")
        }
    }
}