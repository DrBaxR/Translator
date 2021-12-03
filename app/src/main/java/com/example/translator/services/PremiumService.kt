package com.example.translator.services

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

class PremiumService {
    companion object {
        var counter by Delegates.notNull<Int>()
        var isPremium by Delegates.notNull<Boolean>()

        fun incrementCounter(databaseReference: DatabaseReference, uid: String, context: Context, extra: () -> Unit) {
            if(counter < 3 || isPremium) {
                counter++
                databaseReference.child("Users").child("$uid").child("Count").setValue(counter)

                extra()
            }
            else{
                MaterialAlertDialogBuilder(context)
                    .setTitle("Warning")
                    .setMessage("You've reached the maximum translate attempts for today. Would you want to upgrade your account to Premium?")
                    .setNegativeButton("No thank you :(") { dialog, which ->
                    }
                    .setPositiveButton("Yes please") { dialog, which ->
                        databaseReference.child("Users").child("$uid").child("isPremium").setValue(true)
                    }
                    .show()
            }
        }

        fun readCounterFromDatabase(databaseReference: DatabaseReference, uid: String?) {
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val counterValue = snapshot.child("Users").child("$uid").child("Count").getValue()
                    counter = counterValue.toString().toInt()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(ContentValues.TAG, "onCancelled", error.toException());
                }
            })
        }

        fun readPremiumFromDatabase(databaseReference: DatabaseReference, uid: String?) {
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val isPremiumValue = snapshot.child("Users").child("$uid").child("isPremium").getValue()
                    isPremium = isPremiumValue.toString().toBoolean()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(ContentValues.TAG, "onCancelled", error.toException());
                }
            })
        }

        private fun getInitializationData(databaseReference: DatabaseReference, uid: String?, current: LocalDate){
            counter = 0;
            databaseReference.child("Users").child("$uid").child("Day").setValue("$current")
            databaseReference.child("Users").child("$uid").child("Count").setValue(counter)
        }

        fun initializeUserData(databaseReference: DatabaseReference, uid: String?, current: LocalDate){
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
                    Log.e(ContentValues.TAG, "onCancelled", error.toException());
                }
            })
        }
    }
}