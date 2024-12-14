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

public class NotificationsMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notifications_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Sample data
        List<Item> items = new ArrayList<>();
        items.add(new Item("Dr.Bean Bean","Dec 7 2024", R.drawable.doc2));
        items.add(new Item("Dr.2 Bean 2 ","Dec 11 2026", R.drawable.doc2));

        // Find the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycleView);

        // Set the LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the Adapter
        ItemAdapter adapter = new ItemAdapter(items);
        recyclerView.setAdapter(adapter);

        // Find the ImageButton by its ID
        ImageButton imageButton = findViewById(R.id.notificationBackBtn);

        // Set an OnClickListener
        imageButton.setOnClickListener(v -> {
            // Start the new activity using an Intent
            Intent intent = new Intent(NotificationsMenu.this, MainActivity.class);
            startActivity(intent);
        });
    }
}