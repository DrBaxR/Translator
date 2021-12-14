package com.example.translator.fragment

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.translator.R
import com.example.translator.imageRecognition.Recognition
import com.example.translator.imageRecognition.StorageRecognitionActivity
import com.example.translator.locale.*
import com.example.translator.services.PremiumService
import com.example.translator.state.AppState
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ImageFragment : Fragment() {
    lateinit var textField: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        val databaseReference = FirebaseDatabase.getInstance().reference
        val uid = FirebaseAuth.getInstance().uid

        if (view != null) {
            textField = view.findViewById(R.id.iTextField)
            textField.editText?.isFocusable = false
            textField.editText?.text = SpannableStringBuilder("Translated text will be here")

            val autocomplete = view.findViewById<AutoCompleteTextView>(R.id.iSpinner)
            val adapter = AutocompleteLocaleAdapter(view.context, AutocompleteLocale.locales)
            autocomplete.setAdapter(adapter)
            autocomplete.setOnItemClickListener { _, _, pos, _ ->
                val selectedItem = adapter.getItem(pos)
                if (selectedItem != null) {
                    AppState.selectedImageLocale1 = Locale(selectedItem.locale)
                }
            }

            autocomplete.text = SpannableStringBuilder(AppState.selectedImageLocale1.displayLanguage)

            val cameraButton = view.findViewById<Button>(R.id.iCameraButton)
            cameraButton.setOnClickListener {
                PremiumService.incrementCounter(databaseReference, uid!!, view.context) {
                    val intent = Intent(
                        context,
                        Recognition::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)

                    startActivityForResult(intent, 2)
                }
            }

            val storage_ImageP_button = view.findViewById<Button>(R.id.iStorageButton)
            storage_ImageP_button.setOnClickListener {
                PremiumService.incrementCounter(databaseReference, uid!!, view.context) {
                    val intent = Intent(
                        context,
                        StorageRecognitionActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)

                    startActivityForResult(intent, 2)
                }
            }
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            if (context != null) {
                val bundle = data?.extras
                val message = bundle!!.getString("message") ?: ""
                Log.d("here", message)

                AppState.googleApi.getTranslateService(context!!)

                textField.editText?.text = SpannableStringBuilder(
                    AppState.googleApi.translate(
                        message,
                        AppState.selectedImageLocale1.language
                    )
                )
            }
        }
    }
}