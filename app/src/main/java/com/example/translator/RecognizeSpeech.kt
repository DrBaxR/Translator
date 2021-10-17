package com.example.translator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContract
import java.util.*

// TODO: make the locale as input?
class RecognizeSpeech : ActivityResultContract<Unit, String?>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
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
            Locale.getDefault() // TODO: let user specify language
        )

        return intent
    }

    override fun parseResult(resultCode: Int, result: Intent?): String? {
        if (resultCode != Activity.RESULT_OK)
            return null

        return result?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
    }

}