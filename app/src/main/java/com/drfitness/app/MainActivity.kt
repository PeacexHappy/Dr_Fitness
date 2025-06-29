package com.drfitness.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.drfitness.app.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth first thing
        auth = FirebaseAuth.getInstance()

        // --- THE CORE LOGIC (REVISED) ---
        // First, check if a user is already signed in from a previous session.
        if (auth.currentUser != null) {
            // If yes, go straight to the main Home screen.
            navigateToHome()
        } else {
            // If no user is logged in, show the Welcome Screen layout
            // and set up its buttons.
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.buttonLogin.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            binding.buttonSignUp.setOnClickListener {
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        // These flags clear the task stack, so the user can't press "back" to the welcome screen
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        // We must finish this activity so it's removed from the back stack.
        finish()
    }
}