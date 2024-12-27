package com.example.madpart1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilePage extends AppCompatActivity {

    FirebaseUser user;
    TextView userName, userEmail, userPhone;
    Button logout;
    FirebaseAuth mAuth;
    private static final String TAG = "ProfilePage";
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logout = findViewById(R.id.logOutButton);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        // TODO 1: Fetch the current user details and display it on the textView etc.

        if (user != null) {

            String userID = user.getUid();
            db.collection("users").document(userID).get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()){
                    UserDetails userDetails = documentSnapshot.toObject(UserDetails.class);
                    if (userDetails != null) {
                        // Populate the UI with user details
                        userName.setText(userDetails.getFullName());
                        userEmail.setText(userDetails.getEmail());
                        userPhone.setText(userDetails.getPhoneNumber());
                    }
                }else{
                    Log.d(TAG,"No such document existed");
                }
            }).addOnFailureListener(e -> Log.e(TAG, "Error fetching document", e));
        }

        ImageButton backBtn = findViewById(R.id.profile_backBtn);
        backBtn.setOnClickListener(v -> {
            finish();
        });


        ImageButton imageButton = findViewById(R.id.editBtn); // Use your button's ID

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back.
                Intent intent = new Intent(ProfilePage.this, EditProfilePage.class);
                startActivity(intent);
            }
        });

        // TODO 2: Implement the logout logic here

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out the Firebase user
                if (mAuth != null) {
                    updateloginStatus(); // Update shared preferences
                    updateUserOnlineStatus(false); // Mark user as offline
                    mAuth.signOut();
                    Intent intent = new Intent(ProfilePage.this, Login.class);
                    System.out.println("Testing");
                    startActivity(intent);
                    finish(); // Close the current activity
                }
            }
        });
    }

    private void updateloginStatus() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", false);  // User is logged in
        editor.apply(); // Save changes
    }

    public void updateUserOnlineStatus(boolean isOnline) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("users").document(uid)
                .update("Online Status", isOnline)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("User online status updated.");
                })
                .addOnFailureListener(e -> {
                    System.err.println("Failed to update status: " + e.getMessage());
                });
    }

}
