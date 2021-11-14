package com.example.translator.translate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.example.translator.R
import com.example.translator.services.GoogleApiTranslator

class TranslateText : AppCompatActivity() {

    private lateinit var textView: TextView
//    private var googleApi = GoogleApiTranslator(context = applicationContext)
    private lateinit var spinner: Spinner
    private lateinit var button: Button

    private var arrayOptions = arrayOf("English", "Deutsch")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate_text)

        textView = findViewById(R.id.textView1)
        spinner = findViewById(R.id.spinner)
        button = findViewById(R.id.button)

        var bundle = intent.extras
        var message = bundle!!.getString("message")
        textView.text = message

        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayOptions)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinner[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

    }
}