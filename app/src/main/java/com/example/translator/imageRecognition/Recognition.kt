package com.example.translator.imageRecognition

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.devanagari.DevanagariTextRecognizerOptions
import org.opencv.core.Mat
import org.opencv.core.CvType

import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import com.example.translator.R
import com.example.translator.translate.TranslateText
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import org.opencv.android.*
import org.opencv.core.Core
import java.lang.Exception


class Recognition : AppCompatActivity(),  CameraBridgeViewBase.CvCameraViewListener2{

    private val Tag = "MainActivity"

    private lateinit var mRgba: Mat
    private lateinit var mGray: Mat
    private lateinit var mOpenCvCameraView: CameraBridgeViewBase

    private lateinit var translate_button: ImageView
    private lateinit var take_picture_button: ImageView
    private lateinit var show_image_button: ImageView
    private lateinit var current_image: ImageView
    private lateinit var textView: TextView

    private lateinit var textRecognizer: TextRecognizer
    private lateinit var bitmap: Bitmap
    private var camera_recognizeText = "camera"

    private val mLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                SUCCESS -> {
                    run {
                        Log.i(Tag, "OpenCv Is loaded")
                        mOpenCvCameraView.enableView()
                    }
                    run { super.onManagerConnected(status) }
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val MY_PERMISSIONS_REQUEST_CAMERA = 0
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_CAMERA)
        }
        setContentView(R.layout.activity_recognition)

        mOpenCvCameraView=findViewById(R.id.frame_Surface)
        mOpenCvCameraView.visibility = SurfaceView.VISIBLE
        mOpenCvCameraView.setCvCameraViewListener(this)

        textRecognizer = TextRecognition.getClient(DevanagariTextRecognizerOptions.Builder().build())

        textView=findViewById(R.id.textView)
        textView.visibility=View.GONE
        take_picture_button = findViewById(R.id.take_picture)
        take_picture_button.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                if(event.action == MotionEvent.ACTION_DOWN) {
                    return true
                }

                if(event.action == MotionEvent.ACTION_UP) {
                    if(camera_recognizeText == "camera") {
                        take_picture_button.setColorFilter(Color.WHITE)
                        var a = mRgba.t()
                        Core.flip(a, mRgba, 1)
                        a.release()
                        bitmap=Bitmap.createBitmap(mRgba.cols(), mRgba.rows(), Bitmap.Config.ARGB_8888)
                        Utils.matToBitmap(mRgba, bitmap)
                        mOpenCvCameraView.disableView()
                        camera_recognizeText = "recognizeText"
                    } else {
                        Toast.makeText(this@Recognition, "Please take a picture", Toast.LENGTH_LONG).show()
                    }
                    return true
                }
                return false
            }
        })

        translate_button = findViewById(R.id.translate_button)
        translate_button.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                if(event.action == MotionEvent.ACTION_DOWN) {
                    translate_button.setColorFilter(Color.WHITE)
                    return true
                }
                if(event.action == MotionEvent.ACTION_UP) {
                    translate_button.setColorFilter(Color.DKGRAY)
                    if(camera_recognizeText == "recognizeText") {
                        textView.visibility=View.VISIBLE
                        var image = InputImage.fromBitmap(bitmap, 0)
                        var result = textRecognizer.process(image)
                            .addOnSuccessListener(object : OnSuccessListener<Text>{
                                override fun onSuccess(p0: Text) {
                                    textView.text = p0.text
                                    Log.d("CameraActivity", "Out" + p0.text)

                                    var intend = Intent(this@Recognition, TranslateText::class.java)
                                    intend.putExtra("message", p0.text)
                                    startActivity(intend)

                                }
                            })
                            .addOnFailureListener(object: OnFailureListener {
                                override fun onFailure(p0: Exception) {
                                    TODO("Not yet implemented")
                                    Log.d("CameraActivity", "Out not working")
                                }
                            })
                    }else {
                        Toast.makeText(this@Recognition, "Please take a picture", Toast.LENGTH_LONG).show()
                    }
                    return true
                }
                return false
            }
        })

        show_image_button = findViewById(R.id.gallery_button_image)
        show_image_button.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                if(event.action == MotionEvent.ACTION_DOWN) {
                    show_image_button.setColorFilter(Color.WHITE)
                    return true
                }
                if(event.action == MotionEvent.ACTION_UP) {
                    show_image_button.setColorFilter(Color.DKGRAY)
                    if(camera_recognizeText == "recognizeText") {

                    } else {
                        Toast.makeText(this@Recognition, "Please take a picture", Toast.LENGTH_LONG).show()
                    }
                    return true
                }
                return false
            }
        })

    }

    fun CameraActivity() {
        Log.i(Tag, "Instantiated new " + this.javaClass)
    }

    override fun onResume() {
        super.onResume()
        if(OpenCVLoader.initDebug()) {
            Log.d(Tag, "Opencv initialization is done")
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }

    override fun onPause() {
        super.onPause()
        mOpenCvCameraView.disableView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mOpenCvCameraView.disableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        mRgba = Mat(height, width, CvType.CV_8UC4)
        mGray = Mat(height, width, CvType.CV_8UC1)
    }

    override fun onCameraViewStopped() {
        mRgba.release()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        mRgba=inputFrame!!.rgba()
        mGray=inputFrame.gray()
        return mRgba
    }
}