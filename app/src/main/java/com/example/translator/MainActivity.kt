package com.example.translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.opencv.android.OpenCVLoader

class MainActivity : AppCompatActivity() {

    companion object val tag = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(OpenCVLoader.initDebug()) {
            Log.d(tag, "OpenCv installed successfully")
        } else {
            Log.d(tag, "opencf is not installed, aia e")
        }
    }
}