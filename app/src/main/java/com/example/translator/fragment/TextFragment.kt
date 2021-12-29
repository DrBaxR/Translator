package com.example.translator.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.SpannableStringBuilder
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
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
import com.example.translator.imageRecognition.StorageRecognitionActivity
import com.example.translator.locale.*
import com.example.translator.services.PremiumService
import com.example.translator.state.AppState
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.*
import java.util.*
import androidx.core.app.ActivityCompat.startActivityForResult
import android.R.attr.text
import java.io.*
import java.lang.StringBuilder


class TextFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var tTextField1: TextInputLayout
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
            tTextField1 = view.findViewById<TextInputLayout>(R.id.tTextField1)

            val tAutocomplete = view.findViewById<AutoCompleteTextView>(R.id.tSpinner)
            val adapter = AutocompleteLocaleAdapter(view.context, AutocompleteLocale.locales.toMutableList())
            tAutocomplete.setAdapter(adapter)
            tAutocomplete.setOnItemClickListener { _, _, pos, _ ->
                val selectedItem = adapter.getItem(pos)
                if (selectedItem != null) {
                    AppState.selectedTextLocale1 = Locale(selectedItem.locale)
                }
            }

            val fileFromStorageButton= view.findViewById<Button>(R.id.tFileFromStorage)
            fileFromStorageButton.setOnClickListener {
                file_chooser()
            }

            arrow1.setOnClickListener {
                if (hiddenView1.visibility === View.VISIBLE) {
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
                if (hiddenView2.visibility === View.VISIBLE) {
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

    private fun file_chooser() {
        val i = Intent()
        i.type = "text/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Selected File"), 201)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 201 && resultCode == RESULT_OK) {
            val selectedFile = data?.data //The uri with the location of the file

            try {
                val inputStreamReader = InputStreamReader(this.context?.contentResolver?.openInputStream(selectedFile!!))
                val bufferedReader = BufferedReader(inputStreamReader)
                tTextField1.editText?.text = SpannableStringBuilder(bufferedReader.readText())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
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