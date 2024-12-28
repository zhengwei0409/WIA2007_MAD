package com.example.madpart1;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            updateUserOnlineStatus(true); // Mark user as online
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                selectedFragment = new HomePage();
            } else if (item.getItemId() == R.id.doctor) {
                selectedFragment = new MainPage();
            } else if (item.getItemId() == R.id.map) {
                selectedFragment = new CareMapPage();
            } else if (item.getItemId() == R.id.explore) {
                selectedFragment = new InfoHubPage();
            } else if (item.getItemId() == R.id.record) {
                selectedFragment = new EHRRecord();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Set default fragment
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.home);
        }


    }

    public void updateUserOnlineStatus(boolean isOnline) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("users").document(uid)
                .update("online", isOnline)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("User online status updated.");
                })
                .addOnFailureListener(e -> {
                    System.err.println("Failed to update status: " + e.getMessage());
                });
    }

    private void loadFragment(Fragment fragment) {
        // Use the FragmentManager to replace the existing fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment) // Replace with the actual container ID in your layout
                .commit();
    }
}