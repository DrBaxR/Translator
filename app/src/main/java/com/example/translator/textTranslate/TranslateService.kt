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
        "From"; "Afrikaans"; "Albanian"; "Amharic"; "Arabic"; "Armenian"; "Azerbaijani"; "Basque"; "Belarusian"; "Bengali"; "Bosnian"; "Bulgarian"; "Catalan"; "Cebuano"; "Chinese"; "Chinese"; "Corsican"; "Croatian"; "Czech"; "Danish"; "Dutch"; "English"; "Esperanto"; "Estonian"; "Finnish"; "French"; "Frisian"; "Galician"; "Georgian"; "German"; "Greek"; "Gujarati"; "Haitian"; "Hausa"; "Hawaiian"; "Hebrew"; "Hindi"; "Hmong"; "Hungarian"; "Icelandic"; "Igbo"; "Indonesian"; "Irish"; "Italian"; "Japanese"; "Javanese"; "Kannada"; "Kazakh"; "Khmer"; "Kinyarwanda"; "Korean"; "Kurdish"; "Kyrgyz"; "Lao"; "Latvian"; "Lithuanian"; "Luxembourgish"; "Macedonian"; "Malagasy"; "Malay"; "Malayalam"; "Maltese"; "Maori"; "Marathi"; "Mongolian"; "Myanmar"; "Nepali"; "Norwegian"; "Nyanja"; "Odia"; "Pashto"; "Persian"; "Polish"; "Portuguese"; "Punjabi"; "Romanian"; "Russian"; "Samoan"; "Scots"; "Serbian"; "Sesotho"; "Shona"; "Sindhi"; "Sinhala"; "Slovak"; "Slovenian"; "Somali"; "Spanish"; "Sundanese"; "Swahili"; "Swedish"; "Tagalog"; "Tajik"; "Tamil"; "Tatar"; "Telugu"; "Thai"; "Turkish"; "Turkmen"; "Ukrainian"; "Urdu"; "Uyghur"; "Uzbek"; "Vietnamese"; "Welsh"; "Xhosa"; "Yiddish"; "Yoruba"; "Zulu";
    }

    private var toLanguage = {
        "To"; "Afrikaans"; "Albanian"; "Amharic"; "Arabic"; "Armenian"; "Azerbaijani"; "Basque"; "Belarusian"; "Bengali"; "Bosnian"; "Bulgarian"; "Catalan"; "Cebuano"; "Chinese"; "Chinese"; "Corsican"; "Croatian"; "Czech"; "Danish"; "Dutch"; "English"; "Esperanto"; "Estonian"; "Finnish"; "French"; "Frisian"; "Galician"; "Georgian"; "German"; "Greek"; "Gujarati"; "Haitian"; "Hausa"; "Hawaiian"; "Hebrew"; "Hindi"; "Hmong"; "Hungarian"; "Icelandic"; "Igbo"; "Indonesian"; "Irish"; "Italian"; "Japanese"; "Javanese"; "Kannada"; "Kazakh"; "Khmer"; "Kinyarwanda"; "Korean"; "Kurdish"; "Kyrgyz"; "Lao"; "Latvian"; "Lithuanian"; "Luxembourgish"; "Macedonian"; "Malagasy"; "Malay"; "Malayalam"; "Maltese"; "Maori"; "Marathi"; "Mongolian"; "Myanmar"; "Nepali"; "Norwegian"; "Nyanja"; "Odia"; "Pashto"; "Persian"; "Polish"; "Portuguese"; "Punjabi"; "Romanian"; "Russian"; "Samoan"; "Scots"; "Serbian"; "Sesotho"; "Shona"; "Sindhi"; "Sinhala"; "Slovak"; "Slovenian"; "Somali"; "Spanish"; "Sundanese"; "Swahili"; "Swedish"; "Tagalog"; "Tajik"; "Tamil"; "Tatar"; "Telugu"; "Thai"; "Turkish"; "Turkmen"; "Ukrainian"; "Urdu"; "Uyghur"; "Uzbek"; "Vietnamese"; "Welsh"; "Xhosa"; "Yiddish"; "Yoruba"; "Zulu";
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
            "Amharic" -> languageCode = FirebaseTranslateLanguage.
            "Arabic" -> languageCode = FirebaseTranslateLanguage.AR
            "Armenian" -> languageCode = FirebaseTranslateLanguage.HI
            "Azerbaijani" -> languageCode = FirebaseTranslateLanguage.
            "Basque" -> languageCode = FirebaseTranslateLanguage.
            "Belarusian" -> languageCode = FirebaseTranslateLanguage.BE
            "Bengali" -> languageCode = FirebaseTranslateLanguage.BN
            "Bosnian" -> languageCode = FirebaseTranslateLanguage.
            "Bulgarian" -> languageCode = FirebaseTranslateLanguage.BG
            "Catalan" -> languageCode = FirebaseTranslateLanguage.CA
            "Cebuano" -> languageCode = FirebaseTranslateLanguage.
            "Chinese" -> languageCode = FirebaseTranslateLanguage.ZH
            "Corsican" -> languageCode = FirebaseTranslateLanguage.
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
            "Hausa" -> languageCode = FirebaseTranslateLanguage.
            "Hawaiian" -> languageCode = FirebaseTranslateLanguage.
            "Hebrew" -> languageCode = FirebaseTranslateLanguage.HE
            "Hindi" -> languageCode = FirebaseTranslateLanguage.HI
            "Hmong" -> languageCode = FirebaseTranslateLanguage.
            "Hungarian" -> languageCode = FirebaseTranslateLanguage.HU
            "Icelandic" -> languageCode = FirebaseTranslateLanguage.IS
            "Igbo" -> languageCode = FirebaseTranslateLanguage.
            "Indonesian" -> languageCode = FirebaseTranslateLanguage.ID
            "Irish" -> languageCode = FirebaseTranslateLanguage.GA
            "Italian" -> languageCode = FirebaseTranslateLanguage.IT
            "Japanese" -> languageCode = FirebaseTranslateLanguage.JA
            "Javanese" -> languageCode = FirebaseTranslateLanguage.
            "Kannada" -> languageCode = FirebaseTranslateLanguage.KN
            "Kazakh" -> languageCode = FirebaseTranslateLanguage.
            "Khmer" -> languageCode = FirebaseTranslateLanguage.
            "Kinyarwanda" -> languageCode = FirebaseTranslateLanguage.
            "Korean" -> languageCode = FirebaseTranslateLanguage.KO
            "Kurdish" -> languageCode = FirebaseTranslateLanguage.
            "Kyrgyz" -> languageCode = FirebaseTranslateLanguage.
            "Lao" -> languageCode = FirebaseTranslateLanguage.
            "Latvian" -> languageCode = FirebaseTranslateLanguage.LV
            "Lithuanian" -> languageCode = FirebaseTranslateLanguage.LT
            "Luxembourgish" -> languageCode = FirebaseTranslateLanguage.
            "Macedonian" -> languageCode = FirebaseTranslateLanguage.MK
            "Malagasy" -> languageCode = FirebaseTranslateLanguage.
            "Malay" -> languageCode = FirebaseTranslateLanguage.MS
            "Malayalam" -> languageCode = FirebaseTranslateLanguage.
            "Maltese" -> languageCode = FirebaseTranslateLanguage.MT
            "Maori" -> languageCode = FirebaseTranslateLanguage.
            "Marathi" -> languageCode = FirebaseTranslateLanguage.MR
            "Mongolian" -> languageCode = FirebaseTranslateLanguage.
            "Myanmar" -> languageCode = FirebaseTranslateLanguage.
            "Nepali" -> languageCode = FirebaseTranslateLanguage.
            "Norwegian" -> languageCode = FirebaseTranslateLanguage.NO
            "Nyanja" -> languageCode = FirebaseTranslateLanguage.
            "Odia" -> languageCode = FirebaseTranslateLanguage.
            "Pashto" -> languageCode = FirebaseTranslateLanguage.
            "Persian" -> languageCode = FirebaseTranslateLanguage.FA
            "Polish" -> languageCode = FirebaseTranslateLanguage.PL
            "Portuguese" -> languageCode = FirebaseTranslateLanguage.PT
            "Punjabi" -> languageCode = FirebaseTranslateLanguage.
            "Romanian" -> languageCode = FirebaseTranslateLanguage.RO
            "Russian" -> languageCode = FirebaseTranslateLanguage.RU
            "Samoan" -> languageCode = FirebaseTranslateLanguage.
            "Scots" -> languageCode = FirebaseTranslateLanguage.
            "Serbian" -> languageCode = FirebaseTranslateLanguage.
            "Sesotho" -> languageCode = FirebaseTranslateLanguage.
            "Shona" -> languageCode = FirebaseTranslateLanguage.
            "Sindhi" -> languageCode = FirebaseTranslateLanguage.
            "Sinhala" -> languageCode = FirebaseTranslateLanguage.
            "Slovak" -> languageCode = FirebaseTranslateLanguage.SK
            "Slovenian" -> languageCode = FirebaseTranslateLanguage.SL
            "Somali" -> languageCode = FirebaseTranslateLanguage.
            "Spanish" -> languageCode = FirebaseTranslateLanguage.ES
            "Sundanese" -> languageCode = FirebaseTranslateLanguage.
            "Swahili" -> languageCode = FirebaseTranslateLanguage.SW
            "Swedish" -> languageCode = FirebaseTranslateLanguage.SV
            "Tagalog" -> languageCode = FirebaseTranslateLanguage.TL
            "Tajik" -> languageCode = FirebaseTranslateLanguage.
            "Tamil" -> languageCode = FirebaseTranslateLanguage.TA
            "Tatar" -> languageCode = FirebaseTranslateLanguage.
            "Telugu" -> languageCode = FirebaseTranslateLanguage.TE
            "Thai" -> languageCode = FirebaseTranslateLanguage.EN
            "Turkish" -> languageCode = FirebaseTranslateLanguage.EN
            "Turkmen" -> languageCode = FirebaseTranslateLanguage.EN
            "Ukrainian" -> languageCode = FirebaseTranslateLanguage.EN
            "Urdu" -> languageCode = FirebaseTranslateLanguage.EN
            "Uyghur" -> languageCode = FirebaseTranslateLanguage.EN
            "Uzbek" -> languageCode = FirebaseTranslateLanguage.EN
            "Vietnamese" -> languageCode = FirebaseTranslateLanguage.EN
            "Welsh" -> languageCode = FirebaseTranslateLanguage.EN
            "Xhosa" -> languageCode = FirebaseTranslateLanguage.EN
            "Yiddish" -> languageCode = FirebaseTranslateLanguage.EN
            "Yoruba" -> languageCode = FirebaseTranslateLanguage.EN
            "Zulu" -> languageCode = FirebaseTranslateLanguage.EN





        }

        return languageCode
    }
}

