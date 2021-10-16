package com.example.translator

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.Window
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.OpenCVLoader
import org.opencv.core.Mat
import org.opencv.core.CvType

import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.BaseLoaderCallback





class Recognition : AppCompatActivity(),  CameraBridgeViewBase.CvCameraViewListener2{

    private val Tag = "MainActivity"

    private lateinit var mRgba: Mat
    private lateinit var mGray: Mat
    private lateinit var mOpenCvCameraView: CameraBridgeViewBase
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