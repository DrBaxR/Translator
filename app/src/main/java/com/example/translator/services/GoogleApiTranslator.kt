package com.example.translator.services

import com.google.cloud.translate.Translation

import com.google.cloud.translate.TranslateOptions

import com.google.auth.oauth2.GoogleCredentials

import android.content.Context

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import com.example.translator.R
import com.google.cloud.translate.Translate
import java.io.IOException


class GoogleApiTranslator(private val context: Context) {

    private lateinit var  translate: Translate

    fun getTranslateService() {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            context.resources.openRawResource(R.raw.credentials).use { `is` ->
                val myCredentials = GoogleCredentials.fromStream(`is`)
                val translateOptions =
                    TranslateOptions.newBuilder().setCredentials(myCredentials).build()
                translate = translateOptions.service
            }
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
    }

    fun translate(originalText: String, targetLanguage: String): String {
        var translatedText: String
        val translation: Translation = translate.translate(
            originalText,
            Translate.TranslateOption.targetLanguage(targetLanguage),
            Translate.TranslateOption.model("base")
        )
        translatedText = translation.translatedText

        return translatedText
    }

}