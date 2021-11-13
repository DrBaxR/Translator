package com.example.translator.translate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.translator.R

class TranslateText : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate_text)

        textView = findViewById(R.id.textView1)

        var bundle = getIntent().extras
        var message = bundle!!.getString("message")

        textView.setText(message)


    }
}