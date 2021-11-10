package com.example.translator.textTranslate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.example.translator.R
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage


class TranslateService : AppCompatActivity() {

    private lateinit var spinner1 :Spinner
    private lateinit var spinner2 :Spinner

    private var languageCode: Int = 0
    private var fromLanguageCode: Int = 0
    private var toLanguageCode: Int = 0
    private final val REQUEST_PERMISSION_CODE = 1


    private var fromLanguage = {
        "From"; "Afrikaans"; "Albanian"; "Arabic"; "Armenian"; "Belarusian"; "Bengali"; "Bulgarian"; "Catalan"; "Chinese"; "Chinese"; "Croatian"; "Czech"; "Danish"; "Dutch"; "English"; "Esperanto"; "Estonian"; "Finnish"; "French"; "Frisian"; "Galician"; "Georgian"; "German"; "Greek"; "Gujarati"; "Haitian"; "Hebrew"; "Hindi"; "Hungarian"; "Icelandic"; "Indonesian"; "Irish"; "Italian"; "Japanese"; "Kannada"; "Korean"; "Latvian"; "Lithuanian"; "Macedonian"; "Malay"; "Maltese"; "Marathi"; "Norwegian"; "Persian"; "Polish"; "Portuguese"; "Romanian"; "Russian"; "Slovak"; "Slovenian"; "Spanish"; "Swahili"; "Swedish"; "Tagalog"; "Tamil"; "Telugu"; "Thai"; "Turkish"; "Ukrainian"; "Urdu"; "Vietnamese"; "Welsh"
    }

    private var toLanguage = {
        "To"; "Afrikaans"; "Albanian"; "Arabic"; "Armenian"; "Belarusian"; "Bengali"; "Bulgarian"; "Catalan"; "Chinese"; "Chinese"; "Croatian"; "Czech"; "Danish"; "Dutch"; "English"; "Esperanto"; "Estonian"; "Finnish"; "French"; "Frisian"; "Galician"; "Georgian"; "German"; "Greek"; "Gujarati"; "Haitian"; "Hebrew"; "Hindi"; "Hungarian"; "Icelandic"; "Indonesian"; "Irish"; "Italian"; "Japanese"; "Kannada"; "Korean"; "Latvian"; "Lithuanian"; "Macedonian"; "Malay"; "Maltese"; "Marathi"; "Norwegian"; "Persian"; "Polish"; "Portuguese"; "Romanian"; "Russian"; "Slovak"; "Slovenian"; "Spanish"; "Swahili"; "Swedish"; "Tagalog"; "Tamil"; "Telugu"; "Thai"; "Turkish"; "Ukrainian"; "Urdu"; "Vietnamese"; "Welsh"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate_service)

        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                fromLanguageCode = getLanguageCode(arrayOf(fromLanguage)[p2].toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


    }

    private fun getLanguageCode(language: String): Int {
        languageCode = 0;
        when(language) {
            "Afrikaans" -> languageCode = FirebaseTranslateLanguage.AF
            "Albanian" -> languageCode = FirebaseTranslateLanguage.SQ
            "Arabic" -> languageCode = FirebaseTranslateLanguage.AR
            "Armenian" -> languageCode = FirebaseTranslateLanguage.HI
            "Belarusian" -> languageCode = FirebaseTranslateLanguage.BE
            "Bengali" -> languageCode = FirebaseTranslateLanguage.BN
            "Bulgarian" -> languageCode = FirebaseTranslateLanguage.BG
            "Catalan" -> languageCode = FirebaseTranslateLanguage.CA
            "Chinese" -> languageCode = FirebaseTranslateLanguage.ZH
            "Croatian" -> languageCode = FirebaseTranslateLanguage.HR
            "Czech" -> languageCode = FirebaseTranslateLanguage.CS
            "Danish" -> languageCode = FirebaseTranslateLanguage.DA
            "Dutch" -> languageCode = FirebaseTranslateLanguage.NL
            "English" -> languageCode = FirebaseTranslateLanguage.EN
            "Esperanto" -> languageCode = FirebaseTranslateLanguage.EO
            "Estonian" -> languageCode = FirebaseTranslateLanguage.ET
            "Finnish" -> languageCode = FirebaseTranslateLanguage.FI
            "French" -> languageCode = FirebaseTranslateLanguage.FR
            "Frisian" -> languageCode = FirebaseTranslateLanguage.FI
            "Galician" -> languageCode = FirebaseTranslateLanguage.GL
            "Georgian" -> languageCode = FirebaseTranslateLanguage.KA
            "German" -> languageCode = FirebaseTranslateLanguage.DE
            "Greek" -> languageCode = FirebaseTranslateLanguage.EL
            "Gujarati" -> languageCode = FirebaseTranslateLanguage.GU
            "Haitian" -> languageCode = FirebaseTranslateLanguage.HT
            "Hebrew" -> languageCode = FirebaseTranslateLanguage.HE
            "Hindi" -> languageCode = FirebaseTranslateLanguage.HI
            "Hungarian" -> languageCode = FirebaseTranslateLanguage.HU
            "Icelandic" -> languageCode = FirebaseTranslateLanguage.IS
            "Indonesian" -> languageCode = FirebaseTranslateLanguage.ID
            "Irish" -> languageCode = FirebaseTranslateLanguage.GA
            "Italian" -> languageCode = FirebaseTranslateLanguage.IT
            "Japanese" -> languageCode = FirebaseTranslateLanguage.JA
            "Kannada" -> languageCode = FirebaseTranslateLanguage.KN
            "Korean" -> languageCode = FirebaseTranslateLanguage.KO
            "Latvian" -> languageCode = FirebaseTranslateLanguage.LV
            "Lithuanian" -> languageCode = FirebaseTranslateLanguage.LT
            "Macedonian" -> languageCode = FirebaseTranslateLanguage.MK
            "Malay" -> languageCode = FirebaseTranslateLanguage.MS
            "Maltese" -> languageCode = FirebaseTranslateLanguage.MT
            "Marathi" -> languageCode = FirebaseTranslateLanguage.MR
            "Norwegian" -> languageCode = FirebaseTranslateLanguage.NO
            "Persian" -> languageCode = FirebaseTranslateLanguage.FA
            "Polish" -> languageCode = FirebaseTranslateLanguage.PL
            "Portuguese" -> languageCode = FirebaseTranslateLanguage.PT
            "Romanian" -> languageCode = FirebaseTranslateLanguage.RO
            "Russian" -> languageCode = FirebaseTranslateLanguage.RU
            "Slovak" -> languageCode = FirebaseTranslateLanguage.SK
            "Slovenian" -> languageCode = FirebaseTranslateLanguage.SL
            "Spanish" -> languageCode = FirebaseTranslateLanguage.ES
            "Swahili" -> languageCode = FirebaseTranslateLanguage.SW
            "Swedish" -> languageCode = FirebaseTranslateLanguage.SV
            "Tagalog" -> languageCode = FirebaseTranslateLanguage.TL
            "Tamil" -> languageCode = FirebaseTranslateLanguage.TA
            "Telugu" -> languageCode = FirebaseTranslateLanguage.TE
            "Thai" -> languageCode = FirebaseTranslateLanguage.TH
            "Turkish" -> languageCode = FirebaseTranslateLanguage.TR
            "Ukrainian" -> languageCode = FirebaseTranslateLanguage.UK
            "Urdu" -> languageCode = FirebaseTranslateLanguage.UR
            "Vietnamese" -> languageCode = FirebaseTranslateLanguage.VI
            "Welsh" -> languageCode = FirebaseTranslateLanguage.CY
        }

        return languageCode
    }
}

