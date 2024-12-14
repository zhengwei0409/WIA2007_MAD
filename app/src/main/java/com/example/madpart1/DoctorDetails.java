package com.example.madpart1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class DoctorDetails extends AppCompatActivity {

    private TextView doctorNameTextView;
    private TextView doctorSpecialtyTextView;
    private TextView ratingsTextView;
    private TextView reviewsTextView;
    private TextView numberReviewsTextView;
    private TextView patientsTextView;
    private TextView experienceTextView;
    private TextView demographyContentTextView;
    private ImageView doctorImageView; // Added: ImageView for displaying doctor image
    private FrameLayout loadingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadingPage = findViewById(R.id.loading_overlay);
        loadingPage.setVisibility(View.VISIBLE);

        String doctorUID = getIntent().getStringExtra("doctorUID");

        doctorNameTextView = findViewById(R.id.doctorNameTextView);
        doctorSpecialtyTextView = findViewById(R.id.doctorSpecialtyTextView);
        ratingsTextView = findViewById(R.id.ratingsTextView);
        reviewsTextView = findViewById(R.id.reviewsTextView);
        numberReviewsTextView = findViewById(R.id.numberReviewsTextView);
        patientsTextView = findViewById(R.id.patientsTextView);
        experienceTextView = findViewById(R.id.experienceTextView);
        demographyContentTextView = findViewById(R.id.demographyContentsTextView);
        doctorImageView = findViewById(R.id.doctorImage); // Added: Initialize the ImageView

        FirebaseFirestore.getInstance().collection("Doctors").document(doctorUID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String doctorName = documentSnapshot.getString("name");
                String department = documentSnapshot.getString("department");
                Double ratings = documentSnapshot.getDouble("ratings");
                long reviews = documentSnapshot.getLong("numberOfRatingsReceived");
                long patients = documentSnapshot.getLong("numberOfPatients");
                long experience = documentSnapshot.getLong("yearOfExperience");
                String description = documentSnapshot.getString("description");

                doctorNameTextView.setText(doctorName);
                doctorSpecialtyTextView.setText(department);
                ratingsTextView.setText(ratings.toString());
                reviewsTextView.setText(reviews + " reviews");
                numberReviewsTextView.setText(reviews + "\nReviews");
                patientsTextView.setText(patients + "\nPatients");
                experienceTextView.setText(experience + "\nYears");
                demographyContentTextView.setText(description);

                // Fetch and display the doctor's image
                fetchDoctorImage(doctorName); // Added: Call method to fetch the image
            }
        });

        // TODO 1: Implement the book now logic here + notification


    }

    // Added: Method to fetch doctor image from Firebase Storage
    private void fetchDoctorImage(String nameForImage) {

        // Construct the full path for the image in Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://medconnect-d88bd.firebasestorage.app").child(nameForImage + ".png");

        try {
            final File files = File.createTempFile("img", "png");
            storageRef.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(files.getAbsolutePath());
                    doctorImageView.setImageBitmap(bitmap);

                    loadingPage.setVisibility(View.GONE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DoctorDetails.this, "Image Fail to load", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}