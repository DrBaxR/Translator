package com.example.translator.fragment

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.translator.R
import com.example.translator.contracts.RecognizeSpeech
import com.example.translator.locale.LocaleAdapter
import com.example.translator.locale.LocaleSpinnerSelectionListener
import com.example.translator.locale.LocaleSpinnerSelectionListenerWithExtra
import com.example.translator.services.PremiumService
import com.example.translator.state.LocaleState
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SpeechFragment : Fragment() {
    private var sTextField1: TextInputLayout? = null
    private var sTextField2: TextInputLayout? = null

    private val getSpeechLauncher1 = registerForActivityResult(RecognizeSpeech()) { result ->
        sTextField1?.editText?.text = SpannableStringBuilder(result ?: "")
        if (context != null && result != null) {
            LocaleState.googleApi.getTranslateService(context!!)
            sTextField2?.editText?.text = SpannableStringBuilder(
                LocaleState.googleApi.translate(result, LocaleState.selectedSpeechLocale2.language)
            )
        }
    }

    private val getSpeechLauncher2 = registerForActivityResult(RecognizeSpeech()) { result ->
        sTextField2?.editText?.text = SpannableStringBuilder(result ?: "")
        if (context != null && result != null) {
            LocaleState.googleApi.getTranslateService(context!!)
            sTextField1?.editText?.text = SpannableStringBuilder(
                LocaleState.googleApi.translate(result, LocaleState.selectedSpeechLocale1.language)
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

        val button1 = view?.findViewById<Button>(R.id.bSpeak1)
        button1?.setOnClickListener {
            PremiumService.incrementCounter(databaseReference, uid!!, view.context) {
                getSpeechLauncher1.launch(LocaleState.selectedSpeechLocale1)
            }
        }
        val button2 = view?.findViewById<Button>(R.id.bSpeak2)
        button2?.setOnClickListener {
            PremiumService.incrementCounter(databaseReference, uid!!, view.context) {
                getSpeechLauncher2.launch(LocaleState.selectedSpeechLocale2)
            }
        }

        val spinner1 = view?.findViewById<Spinner>(R.id.slSpinner1)
        spinner1?.onItemSelectedListener = LocaleSpinnerSelectionListenerWithExtra("s1") {
            button1?.text = it.displayLanguage
            resetTextViews(sTextField1, sTextField2)
        }
        spinner1?.adapter = LocaleAdapter(view.context, LocaleSpinnerSelectionListener.locales)
        val spinner2 = view?.findViewById<Spinner>(R.id.slSpinner2)
        spinner2?.onItemSelectedListener = LocaleSpinnerSelectionListenerWithExtra("s2") {
            button2?.text = it.displayLanguage
            resetTextViews(sTextField1, sTextField2)
        }
        spinner2?.adapter = LocaleAdapter(view.context, LocaleSpinnerSelectionListener.locales)

        return view
    }

    private fun resetTextViews(vararg textViews: TextInputLayout?) {
        textViews.forEach {
            it?.editText?.text = SpannableStringBuilder("")
        }
    }
}