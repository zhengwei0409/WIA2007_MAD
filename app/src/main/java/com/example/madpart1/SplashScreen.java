package com.example.madpart1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetOnbStatus(); // use only when it is first time user
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!checkOnboardingStatus()) {
                    // If onboarding is not complete, go to Onboarding
                    startActivity(new Intent(SplashScreen.this, OnboardingScreen.class));
                } else if (!checkLoginStatus()) {
                    // If onboarding is complete but not logged in, go to Login
                    startActivity(new Intent(SplashScreen.this, Login.class));
                } else {
                    // If already logged in, go to HomeScreen or MainActivity
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
                finish();
            }
        }, 2000);
    }

    private boolean checkOnboardingStatus() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return prefs.getBoolean("isOnboardingComplete", false);  // Default is false
    }

    private boolean checkLoginStatus() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return prefs.getBoolean("isLoggedIn", false);  // Default is false
    }

    private void resetOnbStatus() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isOnboardingComplete", false);  // Reset onboarding
        editor.apply();  // Save changes
    }

}
