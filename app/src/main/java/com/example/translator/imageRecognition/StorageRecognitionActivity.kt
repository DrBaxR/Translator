package com.example.translator.imageRecognition

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.translator.R
import com.example.translator.translate.TranslateText
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.devanagari.DevanagariTextRecognizerOptions
import java.io.IOException

class StorageRecognitionActivity : AppCompatActivity() {

    private lateinit var select_image: Button
    private lateinit var recognizeImage_button: ImageView
    private lateinit var image_view: ImageView
    private lateinit var text_view: TextView

    private var Selected_Picture = 200
    private lateinit var textRecognizer: TextRecognizer
    private var show_image_or_text = "image"

    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_recognition)

        select_image = findViewById(R.id.select_image)
        select_image.setOnClickListener {
                image_chooser()
        }

    textRecognizer = TextRecognition.getClient(DevanagariTextRecognizerOptions.Builder().build())
        text_view = findViewById(R.id.text_view)
        text_view.visibility = View.GONE
        recognizeImage_button = findViewById(R.id.recognizeImage_button)
        recognizeImage_button.setOnTouchListener(object :View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                if(p1?.action == MotionEvent.ACTION_DOWN) {
                    recognizeImage_button.setColorFilter(Color.DKGRAY)
                    return true
                }
                if(p1?.action == MotionEvent.ACTION_UP) {
                    recognizeImage_button.setColorFilter(Color.WHITE)
                    if(show_image_or_text == "text") {
                        text_view.visibility = View.GONE
                        image_view.visibility = View.VISIBLE
                        show_image_or_text = "image"
                    } else {
                        text_view.visibility = View.VISIBLE
                        image_view.visibility = View.GONE
                        show_image_or_text = "text"
                    }
                    return true
                }
                return false
            }
        })
    }

    private fun image_chooser() {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(i, "Selected Picture"), Selected_Picture)
        }

    override fun onActivityResult(resultCode: Int, requestCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RESULT_OK) {
            if(resultCode == Selected_Picture){
                val selectedImageUri = data?.data
                if(selectedImageUri != null) {
                    Log.d("storage_Activity", "Output Uri: $selectedImageUri")

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    val image = InputImage.fromBitmap(bitmap, 0)
                    textRecognizer.process(image).addOnSuccessListener { p0 ->
                        Log.d("StorageAction", "Out: " + p0.text)
                        text_view.text = p0.text
                        show_image_or_text = "text" //new Intend where we can send discovered text

                        var intend = Intent(this, TranslateText::class.java)
                        intend.putExtra("message", p0.text)
                        startActivity(intend)
                    }
                }
            }
        }
    }
}