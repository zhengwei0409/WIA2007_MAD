package com.example.madpart1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class AllUsers extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<Users> userList;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Initialize RecyclerView and list
        recyclerView = findViewById(R.id.recyclerViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();

        // Initialize the adapter with an OnItemClickListener
        userAdapter = new UserAdapter(name -> {
            // Fetch the userUID from Firestore using
            FirebaseFirestore.getInstance().collection("users")
                    .get()
                    .addOnFailureListener(e -> Toast.makeText(AllUsers.this, "Error fetching userUID: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });

        userAdapter = new UserAdapter(user -> {
            // This is triggered when a user item is clicked
            Intent intent = new Intent(AllUsers.this, JoinActivity.class);
            intent.putExtra("userName", user.getName());
            startActivity(intent);
        });

        recyclerView.setAdapter(userAdapter);

        // Back arrow functionality
        backArrow = findViewById(R.id.user_back_arrow);
        backArrow.setOnClickListener(v -> {
            finish();
        });

        // Fetch data from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    System.out.println("Error in fetching online Status");
                    return;
                }

                if (snapshots != null) {
                    userList.clear(); // Clear the list before adding new data
                    for (DocumentSnapshot document : snapshots) {
                        // Fetch data and map to Doctor object
                        String name = document.getString("Full name");
                        String phoneNo= document.getString("Phone Number");
                        Boolean loginStatus = document.getBoolean("Online Status"); // Fetch loginStatus

                        // Create new Doctor object with loginStatus field
                        Users user = new Users(name, phoneNo , loginStatus);
                        userList.add(user); // Add doctor to list
                    }
                    userAdapter.submitList(userList); // Update adapter with new list
                }
            }
        });


    }
}
