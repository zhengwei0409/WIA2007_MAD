package com.example.madpart1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllDoctor extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctorList;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_doctor);

        // Initialize RecyclerView and list
        recyclerView = findViewById(R.id.recyclerViewDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorList = new ArrayList<>();

        // Initialize the adapter with an OnItemClickListener
        doctorAdapter = new DoctorAdapter(doctorName -> {
            // Fetch the doctorUID from Firestore using doctorName
            FirebaseFirestore.getInstance().collection("Doctors")
                    .whereEqualTo("name", doctorName)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            String doctorUID = queryDocumentSnapshots.getDocuments().get(0).getId();

                            // Start MainActivity with the doctorUID
                            Intent intent = new Intent(AllDoctor.this, MainActivity.class);
                            intent.putExtra("doctorUID", doctorUID);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AllDoctor.this, "Doctor not found!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(AllDoctor.this, "Error fetching doctorUID: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });

        recyclerView.setAdapter(doctorAdapter);

        // Back arrow functionality
        backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(v -> {
            finish();
        });

        // Fetch data from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Doctors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle the error
                    return;
                }

                if (snapshots != null) {
                    doctorList.clear(); // Clear the list before adding new data
                    for (DocumentSnapshot document : snapshots) {
                        // Fetch data and map to Doctor object
                        String name = document.getString("name");
                        String department = document.getString("department");
                        String loginStatus = document.getString("loginStatus"); // Fetch loginStatus

                        // Create new Doctor object with loginStatus field
                        Doctor doctor = new Doctor(name, department, loginStatus);
                        doctorList.add(doctor); // Add doctor to list
                    }
                    doctorAdapter.submitList(doctorList); // Update adapter with new list
                }
            }
        });
    }
}