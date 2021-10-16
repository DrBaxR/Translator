package com.example.translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import org.opencv.android.OpenCVLoader
import android.content.Intent


class MainActivity : AppCompatActivity() {
    private lateinit var cameraButton:Button
    private companion object Private private val tag = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(OpenCVLoader.initDebug()) {
            Log.d(tag, "OpenCv installed successfully")
        } else {
            Log.d(tag, "opencv is not installed, aia e")
        }

        cameraButton = findViewById(R.id.camera_button)
        cameraButton.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    Recognition::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }

    }
}