package com.example.madpart1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

public class EditProfilePage extends AppCompatActivity {

    ImageButton backBtn;
    TextView saveBtn,nameDisplay,detailsDisplay;
    ImageView proPic;
    EditText fullName,emailAdd,mobileNum,nationality,gender,dob;
    FirebaseAuth mAuth;
    private static final String TAG = "ProfilePage";
    private FirebaseFirestore db;
    FirebaseUser user;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // TODO 1: Fetch the current user details and display it on the textView etc.
        backBtn= findViewById(R.id.backBtn2);
        saveBtn = findViewById(R.id.saveBtn);
        nameDisplay = findViewById(R.id.name);
        detailsDisplay = findViewById(R.id.details); // email and phone number

        proPic = findViewById(R.id.proPic);
        fullName = findViewById(R.id.fullName); //editText
        emailAdd = findViewById(R.id.emailAdd);//editText
        mobileNum = findViewById(R.id.mobileNum);//editText
        nationality = findViewById(R.id.nationality);//editText
        gender = findViewById(R.id.gender);//editText
        dob = findViewById(R.id.dob);//editText

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            userID = user.getUid();
            loadProfilePicture();
            db.collection("users").document(userID).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            UserDetails userDetails = documentSnapshot.toObject(UserDetails.class);
                            if (userDetails != null) {
                                nameDisplay.setText(String.valueOf(userDetails.getFullName()));
                                String details = userDetails.getEmail() + " " + userDetails.getPhoneNumber();
                                detailsDisplay.setText(details);
                                fullName.setText(String.valueOf(userDetails.getFullName()));
                                emailAdd.setText(String.valueOf(userDetails.getEmail()));
                                mobileNum.setText(String.valueOf(userDetails.getPhoneNumber()));
                                nationality.setText(String.valueOf(userDetails.getNationality()));
                                gender.setText(String.valueOf(userDetails.getGender()));
                                dob.setText(String.valueOf(userDetails.getDateOfBirth()));
                            }
                        }
                    }).addOnFailureListener(e -> Log.e(TAG, "Error fetching document", e));
        }
        ImageButton backBtn = findViewById(R.id.backBtn2); // Use your button's ID

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back
                finish();
            }
        });

        TextView saveBtn = findViewById(R.id.saveBtn); // Use your button's ID

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String saveFullName = String.valueOf(fullName.getText());
                String saveEmailAdd = String.valueOf(emailAdd.getText());
                String saveMobileNo= String.valueOf(mobileNum.getText());
                String saveNationality = String.valueOf(nationality.getText());
                String saveGender = String.valueOf(gender.getText());
                String saveDob = String.valueOf(dob.getText());


                Map<String, Object> updatedDetails = new HashMap<>();
                updatedDetails.put("Full name", saveFullName);
                updatedDetails.put("Email", saveEmailAdd);
                updatedDetails.put("Phone Number", saveMobileNo);
                updatedDetails.put("Nationality", saveNationality);
                updatedDetails.put("Gender", saveGender);
                updatedDetails.put("Date of Birth", saveDob);

                db.collection("users").document(userID)
                        .update(updatedDetails)
                        .addOnSuccessListener(aVoid -> {
                            // Success callback
                            Toast.makeText(EditProfilePage.this, "User details updated successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProfilePage.this, HomePage.class);
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                            // Failure callback
                            Toast.makeText(EditProfilePage.this, "Failed to update user details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Firestore Update", "Error updating document", e);
                        });


                // Navigate back
                finish();
            }
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
                Toast.makeText(EditProfilePage.this, "Failed to load profile picture", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error fetching profile picture", e);
            });
        } catch (IOException e) {
            Log.e(TAG, "Error creating temporary file", e);
        }
    }
}