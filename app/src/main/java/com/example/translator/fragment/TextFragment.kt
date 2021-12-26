package com.example.translator.fragment

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
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
            val cardView1 = view.findViewById<CardView>(R.id.base_cardview1)
            val hiddenView1 = view.findViewById<LinearLayout>(R.id.hidden_view1)
            val arrow1 = view.findViewById<ImageButton>(R.id.arrow_button1)

            val cardView2 = view.findViewById<CardView>(R.id.base_cardview2)
            val hiddenView2 = view.findViewById<LinearLayout>(R.id.hidden_view2)
            val arrow2 = view.findViewById<ImageButton>(R.id.arrow_button2)

            val tTextField2 = view.findViewById<TextInputLayout>(R.id.tTextField2)
            tTextField2.editText?.isFocusable = false
            val tTextField1 = view.findViewById<TextInputLayout>(R.id.tTextField1)

            val tAutocomplete = view.findViewById<AutoCompleteTextView>(R.id.tSpinner)
            val adapter = AutocompleteLocaleAdapter(view.context, AutocompleteLocale.locales.toMutableList())
            tAutocomplete.setAdapter(adapter)
            tAutocomplete.setOnItemClickListener { _, _, pos, _ ->
                val selectedItem = adapter.getItem(pos)
                if (selectedItem != null) {
                    AppState.selectedTextLocale1 = Locale(selectedItem.locale)
                }
            }

            arrow1.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (hiddenView1.visibility === View.VISIBLE) {

                    // The transition of the hiddenView is carried out
                    //  by the TransitionManager class.
                    // Here we use an object of the AutoTransition
                    // Class to create a default transition.
                    TransitionManager.beginDelayedTransition(
                        cardView1,
                        AutoTransition()
                    )
                    hiddenView1.visibility = View.GONE
                    arrow1.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
                } else {
                    TransitionManager.beginDelayedTransition(
                        cardView1,
                        AutoTransition()
                    )
                    hiddenView1.visibility = View.VISIBLE
                    arrow1.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
                }
            }

            arrow2.setOnClickListener {
                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (hiddenView2.visibility === View.VISIBLE) {

                    // The transition of the hiddenView is carried out
                    //  by the TransitionManager class.
                    // Here we use an object of the AutoTransition
                    // Class to create a default transition.
                    TransitionManager.beginDelayedTransition(
                        cardView2,
                        AutoTransition()
                    )
                    hiddenView2.visibility = View.GONE
                    arrow2.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
                } else {
                    TransitionManager.beginDelayedTransition(
                        cardView2,
                        AutoTransition()
                    )
                    hiddenView2.visibility = View.VISIBLE
                    arrow2.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
                }
            }


            tAutocomplete.text = SpannableStringBuilder(AppState.selectedTextLocale1.displayLanguage)

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