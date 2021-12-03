package com.example.translator.fragment

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import com.example.translator.R
import com.example.translator.locale.LocaleAdapter
import com.example.translator.locale.LocaleSpinnerSelectionListener
import com.example.translator.locale.LocaleSpinnerSelectionListenerWithExtra
import com.example.translator.services.PremiumService
import com.example.translator.state.LocaleState
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.*

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

            val spinner = view.findViewById<Spinner>(R.id.tSpinner)
            spinner.onItemSelectedListener = LocaleSpinnerSelectionListenerWithExtra("t1") { }
            spinner.adapter = LocaleAdapter(view.context, LocaleSpinnerSelectionListener.locales)

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
            LocaleState.googleApi.getTranslateService(context!!)
            textField.editText?.text = SpannableStringBuilder(
                LocaleState.googleApi.translate(text, LocaleState.selectedTextLocale1.language)
            )
        }
    }
}