package com.example.translator.fragment

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import com.example.translator.R
import com.example.translator.imageRecognition.Recognition
import com.example.translator.imageRecognition.StorageRecognitionActivity
import com.example.translator.locale.LocaleAdapter
import com.example.translator.locale.LocaleSpinnerSelectionListener
import com.example.translator.locale.LocaleSpinnerSelectionListenerWithExtra
import com.google.android.material.textfield.TextInputLayout

class ImageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)

        if (view != null) {
            val textField = view.findViewById<TextInputLayout>(R.id.iTextField)
            textField.editText?.isFocusable = false
            textField.editText?.text = SpannableStringBuilder("Translated text will be here")

            val spinner = view.findViewById<Spinner>(R.id.iSpinner)
            spinner.onItemSelectedListener = LocaleSpinnerSelectionListenerWithExtra("i1") { }
            spinner.adapter = LocaleAdapter(view.context, LocaleSpinnerSelectionListener.locales)

            val cameraButton = view.findViewById<Button>(R.id.iCameraButton)
            cameraButton.setOnClickListener {
                startActivity(
                    Intent(
                        context,
                        Recognition::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
            }

            val storage_ImageP_button = view.findViewById<Button>(R.id.iStorageButton)
            storage_ImageP_button.setOnClickListener {
                startActivity(
                    Intent(
                        context,
                        StorageRecognitionActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
            }
        }

        return view
    }
}