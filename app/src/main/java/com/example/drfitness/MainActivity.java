package com.example.drfitness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "OnboardingPrefs";
    private static final String KEY_FIRST_LAUNCH = "firstLaunch";
    private FirebaseAuth mAuth; // FirebaseAuth instance
    private Button buttonSignOut; // Sign-out button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load shared preferences to check if it's the first launch
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean(KEY_FIRST_LAUNCH, true);  // default is true

        if (isFirstLaunch) {
            // Redirect to OnboardingActivity
            Intent intent = new Intent(MainActivity.this, OnboardingActivity.class);
            startActivity(intent);

            // Mark that the app has been launched before
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(KEY_FIRST_LAUNCH, false);  // Set to false after first launch
            editor.apply();

            // Finish MainActivity to prevent going back to it
            finish();
        } else {
            // Normal behavior if not first launch
            setContentView(R.layout.activity_main);

            // Initialize FirebaseAuth
            mAuth = FirebaseAuth.getInstance();
            buttonSignOut = findViewById(R.id.buttonSignOut); // Ensure this ID matches your XML layout

            // Set OnClickListener for the sign-out button
            buttonSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signOut();
                }
            });
        }
    }

    private void signOut() {
        mAuth.signOut(); // Sign out from Firebase

        // Redirect to LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close MainActivity
    }
}
