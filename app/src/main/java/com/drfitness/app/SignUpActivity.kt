package com.drfitness.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.drfitness.app.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // --- Configure Google Sign-In ---
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // --- Initialize the launcher for the Google Sign-In result ---
        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    Log.w("SignUpActivity", "Google sign in failed", e)
                    Toast.makeText(this, "Google Sign-In failed.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // --- Button Listeners ---
        binding.buttonBack.setOnClickListener { finish() }

        binding.textViewLoginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.buttonSignUp.setOnClickListener {
            performSignUp()
        }

        binding.buttonGoogleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("SignUpActivity", "signInWithCredential:success")
                    navigateToHome()
                } else {
                    Log.w("SignUpActivity", "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Google Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun performSignUp() {
        val name = binding.textFieldName.editText?.text.toString().trim()
        val email = binding.textFieldEmail.editText?.text.toString().trim()
        val password = binding.textFieldPassword.editText?.text.toString().trim()
        val confirmPassword = binding.textFieldConfirmPassword.editText?.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textFieldEmail.error = "Please enter a valid email."
            return
        } else {
            binding.textFieldEmail.error = null
        }
        if (password.length < 6) {
            binding.textFieldPassword.error = "Password must be at least 6 characters."
            return
        } else {
            binding.textFieldPassword.error = null
        }
        if (password != confirmPassword) {
            binding.textFieldConfirmPassword.error = "Passwords do not match."
            return
        } else {
            binding.textFieldConfirmPassword.error = null
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("SignUpActivity", "createUserWithEmail:success")
                    navigateToHome()
                } else {
                    Log.w("SignUpActivity", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun navigateToHome() { // Renamed from navigateToDashboard
        val intent = Intent(this, HomeActivity::class.java) // Changed to HomeActivity
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}