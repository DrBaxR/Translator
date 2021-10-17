package com.example.translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher

class MainActivity : AppCompatActivity() {

    private var tvResult: TextView? = null
    private var button: ImageView? = null

    private val getSpeechLauncher = registerForActivityResult(RecognizeSpeech()) { result ->
        tvResult?.text = result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.btnSpeak)
        tvResult = findViewById(R.id.tvResult)
        button?.setOnClickListener { getSpeechLauncher.launch(Unit) } // input locale will be given here
    }
}