package com.example.madpart1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ProfilePage extends AppCompatActivity {

    FirebaseUser user;
    TextView userName, userEmail, userPhone;
    Button logout;
    FirebaseAuth mAuth;
    private static final String TAG = "ProfilePage";
    private FirebaseFirestore db;
    String userID;
    ImageView proPic;

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

            userID = user.getUid();
            proPic = findViewById(R.id.profilePic);
            loadProfilePicture();
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

    private void loadProfilePicture() {
        db.collection("users").document(userID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String dob = documentSnapshot.getString("Date of Birth");
                        if (dob != null) {
                            // Extract day (DD) from "DD / MM / YYYY"
                            String[] dobParts = dob.split(" / ");
                            if (dobParts.length > 0) {
                                try {
                                    int day = Integer.parseInt(dobParts[0]);
                                    int imageIndex = day % 10; // Calculate DD % 10
                                    fetchProfileImage(imageIndex);
                                } catch (NumberFormatException e) {
                                    Log.e(TAG, "Invalid day format in Date of Birth", e);
                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error fetching Date of Birth", e));
    }

    private void fetchProfileImage(int imageIndex) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String imageName = imageIndex + ".jpg";
        StorageReference storageRef = storage.getReference().child(imageName);

        try {
            final File tempFile = File.createTempFile("profile", "jpg");
            storageRef.getFile(tempFile).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
                proPic.setImageBitmap(bitmap);
            }).addOnFailureListener(e -> {
                Toast.makeText(ProfilePage.this, "Failed to load profile picture", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error fetching profile picture", e);
            });
        } catch (IOException e) {
            Log.e(TAG, "Error creating temporary file", e);
        }
    }
}
