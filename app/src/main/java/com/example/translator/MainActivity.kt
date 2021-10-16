package com.example.translator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import org.opencv.android.OpenCVLoader
import java.util.*

class MainActivity : AppCompatActivity() {

    var tvResult: TextView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)
        val button: ImageView = findViewById(R.id.btnSpeak)
        button.setOnClickListener {
            getSpeechInput()
        }
    }

    // TODO: Clean and make it not use deprecated stuff
    // TODO: let user specify language
    private fun getSpeechInput() {
        val intent = Intent(
            RecognizerIntent
                .ACTION_RECOGNIZE_SPEECH
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, 10)
        } else {
            Toast.makeText(
                this,
                "Your Device Doesn't Support Speech Input",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode, data
        )
        when (requestCode) {
            10 -> if (resultCode == RESULT_OK &&
                data != null
            ) {
                val result =
                    data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS
                    )
                tvResult?.text = result?.get(0) ?: ""
            }
        }
    }
}