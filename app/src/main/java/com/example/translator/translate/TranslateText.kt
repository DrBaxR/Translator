package com.example.translator.translate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.example.translator.R
import com.example.translator.services.GoogleApiTranslator
import com.google.android.material.textfield.TextInputLayout

class TranslateText : AppCompatActivity() {

    private lateinit var textView: TextInputLayout
    private lateinit var textViewTranslated: TextInputLayout
    private var googleApi = GoogleApiTranslator()
    private lateinit var spinner: Spinner
    private lateinit var button: Button

    private var arrayOptions = arrayOf("Language", "Afrikaans", "Albanian", "Arabic", "Armenian", "Belarusian", "Bengali", "Bulgarian", "Catalan", "Chinese", "Croatian", "Czech", "Danish", "Dutch", "English", "Esperanto", "Estonian", "Finnish", "French", "Frisian", "Galician", "Georgian", "German", "Greek", "Gujarati", "Haitian", "Hebrew", "Hindi", "Hungarian", "Icelandic", "Indonesian", "Irish", "Italian", "Japanese", "Kannada", "Korean", "Latvian", "Lithuanian", "Macedonian", "Malay", "Maltese", "Marathi", "Norwegian", "Persian", "Polish", "Portuguese", "Romanian", "Russian", "Slovak", "Slovenian", "Spanish", "Swahili", "Swedish", "Tagalog", "Tamil", "Telugu", "Thai", "Turkish", "Ukrainian", "Urdu", "Vietnamese", "Welsh")
    private var arraySelected = arrayOf("", "af", "sq", "ar", "hy", "be", "bn", "bg", "ca", "zh", "hr", "cs", "da", "nl", "en", "eo", "et", "fi", "fr", "fy", "gl", "ka", "de", "el", "gu", "ht", "he", "hi", "hu", "is", "id", "ga", "it", "ja", "kn", "ko", "lv", "lt", "mk", "ms", "mt", "mr", "no", "fa", "pl", "pt", "ro", "ru", "sk", "sl", "es", "sw", "sv", "tl", "ta", "te", "th", "tr", "uk", "ur", "vi", "cy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_text)

        textView = findViewById(R.id.tTextField1)
        textViewTranslated = findViewById(R.id.tTextField2)
        spinner = findViewById(R.id.tSpinner)
        button = findViewById(R.id.tButton)

        val bundle = intent.extras
        var message = bundle!!.getString("message")
        textView.editText?.text = SpannableStringBuilder(message)

        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayOptions)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                button.setOnClickListener {
                    googleApi.getTranslateService(context = baseContext)
                    var translatedMessage = googleApi.translate(message!!, arraySelected[p2])
                    textViewTranslated.editText?.text = SpannableStringBuilder(translatedMessage)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

}