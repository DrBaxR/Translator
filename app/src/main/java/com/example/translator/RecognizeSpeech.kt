package com.example.translator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import com.example.translator.state.LocaleState
import java.util.*

class RecognizeSpeech : ActivityResultContract<Locale, String?>() {
    override fun createIntent(context: Context, input: Locale?): Intent {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            input.toString()
        )

        return intent
    }

    override fun parseResult(resultCode: Int, result: Intent?): String? {
        if (resultCode != Activity.RESULT_OK)
            return null

        return result?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
    }

}