package com.example.translator.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import com.example.translator.R
import com.example.translator.locale.LocaleAdapter
import com.example.translator.locale.LocaleSpinnerSelectionListener
import com.example.translator.locale.LocaleSpinnerSelectionListenerWithExtra
import com.example.translator.state.LocaleState
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates


class TextFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference
    private var counter by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseReference = FirebaseDatabase.getInstance().reference
        val uid = FirebaseAuth.getInstance().uid
        val current = LocalDate.now()

        initializeUserData(uid, current);
        readCounterFromDatabase(uid)

        val view = inflater.inflate(R.layout.fragment_text, container, false)

        if (view != null) {
            val tTextField2 = view.findViewById<TextInputLayout>(R.id.tTextField2)
            tTextField2.editText?.isFocusable = false
            val tTextField1 = view.findViewById<TextInputLayout>(R.id.tTextField1)

            val spinner = view.findViewById<Spinner>(R.id.tSpinner)
            spinner.onItemSelectedListener = LocaleSpinnerSelectionListenerWithExtra("t1") { }
            spinner.adapter = LocaleAdapter(view.context, LocaleSpinnerSelectionListener.locales)

            val button = view.findViewById<Button>(R.id.tButton)

            button.setOnClickListener {
                if(counter < 3) {
                    counter++;
                    databaseReference.child("Users").child("$uid").child("Count").setValue(counter)

                    translateText(
                        tTextField1.editText?.text?.toString(),
                        tTextField2
                    )
                }
            }
        }

        return view
    }

    private fun initializeUserData(uid: String?, current: LocalDate){
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dateValue = snapshot.child("Users").child("$uid").child("Day").getValue()
                lateinit var date: LocalDate

                if(dateValue == null){
                    getInitializationData(databaseReference, uid, current)
                }
                else
                {
                    date = LocalDate.parse(dateValue.toString(), DateTimeFormatter.ISO_LOCAL_DATE)

                    if(current.isAfter(date))
                    {
                        getInitializationData(databaseReference, uid, current)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "onCancelled", error.toException());
            }
        })
    }

    private fun getInitializationData(databaseReference: DatabaseReference, uid: String?, current: LocalDate){
        counter = 0;
        databaseReference.child("Users").child("$uid").child("Day").setValue("$current")
        databaseReference.child("Users").child("$uid").child("Count").setValue(counter)
        databaseReference.child("Users").child("$uid").child("isPremium").setValue(false)
    }

    private fun readCounterFromDatabase(uid: String?) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val counterValue = snapshot.child("Users").child("$uid").child("Count").getValue()
                counter = counterValue.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "onCancelled", error.toException());
            }
        })
    }

    private fun translateText(text: String?, textField: TextInputLayout) {
        if (text != null) {
            LocaleState.googleApi.getTranslateService(context!!)
            textField.editText?.text = SpannableStringBuilder(
                LocaleState.googleApi.translate(text, LocaleState.selectedTextLocale1.language)
            )
        }
    }
}