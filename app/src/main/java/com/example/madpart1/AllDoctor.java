package com.example.madpart1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_doctor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView and list
        recyclerView = findViewById(R.id.recyclerViewDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorList = new ArrayList<>();
        doctorAdapter = new DoctorAdapter(doctorList);
        recyclerView.setAdapter(doctorAdapter);

        // Fetch data from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Doctors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle the error
                    Log.w("Firestore Error", "Listen failed.", e);
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
                    doctorAdapter.notifyDataSetChanged(); // Notify adapter of changes
                }
            }
        });

        ImageButton imageButton = findViewById(R.id.doctor_back_btn);

        // Set an OnClickListener
        imageButton.setOnClickListener(v -> {
            // Close the current activity
            finish();
        });

    }
}