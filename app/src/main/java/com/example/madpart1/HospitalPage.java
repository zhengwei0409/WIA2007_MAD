package com.example.madpart1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HospitalPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hospital_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Sample data
        List<Hospital> items = new ArrayList<>();
        items.add(new Hospital("UM Specialist Centre (UMSC)","4.0 (1.1K)","Private Hospital","UM Specialist Centre (UMSC), Lot 28, Lorong Universiti, Lembah Pantai, 50603 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur", R.drawable.umsc));
        items.add(new Hospital("Pantai Hospital Kuala Lumpur","4.3 (3.7K)","Private Hospital","8, Jalan Bukit Pantai, Bangsar, 59100 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur", R.drawable.pantai_hospital));
        items.add(new Hospital("Kuala Lumpur Hospital","3.5 (1.4K)","Government Hospital","Jalan Pahang, 50586 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur", R.drawable.kl_hospital));
        items.add(new Hospital("Prince Court Medical Centre","4.5 (4.6K)","Private Hospital","39, Jalan Kia Peng, Kuala Lumpur, 50450 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur", R.drawable.prince_hospital));
        items.add(new Hospital("Gleneagles Hospital Kuala Lumpur","4.5 (4.4K)","Private Hospital","282, 286 & 288 Block A, B and Medical Office Block, Jln Ampang, Kampung Berembang, 50450 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur", R.drawable.gleneagles_hospital));
        items.add(new Hospital("Columbia Asia Hospital - Petaling Jaya","4.8 (4.6K)","Private Hospital","Lot 69, Jln 13/6, Seksyen 13, 46200 Petaling Jaya, Selangor", R.drawable.columbia_hospital));
        items.add(new Hospital("Sunway Medical Centre","4.6 (9.2K)","Private Hospital","5, Jalan Lagoon Selatan, Bandar Sunway, 47500 Subang Jaya, Selangor", R.drawable.sunway_hospital));


        // Find the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.HospitalrecycleView);

        // Set the LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the Adapter
        HospitalAdapter adapter = new HospitalAdapter(this, items);
        recyclerView.setAdapter(adapter);

        // Find the ImageButton by its ID
        ImageButton imageButton = findViewById(R.id.hospitalBackBtn);

        // Set an OnClickListener
        imageButton.setOnClickListener(v -> {
            // Close the current activity
            finish();
        });

    }
}