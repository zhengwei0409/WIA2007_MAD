package com.example.madpart1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfilePage extends AppCompatActivity {

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

        // TODO 1: Fetch the current user details and display it on the textView etc.

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
    }
}