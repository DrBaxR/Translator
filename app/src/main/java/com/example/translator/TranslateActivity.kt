package com.example.translator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.translator.services.PremiumService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate

class TranslateActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var logoutButton: FloatingActionButton
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_translate)

        firebaseAuth = FirebaseAuth.getInstance()
        logoutButton = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            logoutAction()
        }

        setUpNavigation()

        val databaseReference = FirebaseDatabase.getInstance().reference
        val uid = FirebaseAuth.getInstance().uid
        val current = LocalDate.now()
        PremiumService.initializeUserData(databaseReference, uid, current)
        PremiumService.readCounterFromDatabase(databaseReference, uid)
        PremiumService.readPremiumFromDatabase(databaseReference, uid)
    }

    private fun setUpNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
    }

    private fun logoutAction() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}