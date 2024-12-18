package com.example.madpart1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class AppointmentPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_page);

        // FloatingActionButton click listener
//        ImageView fabBack = findViewById(R.id.back_arrow);
//        fabBack.setOnClickListener(v -> {
//            Intent intent = new Intent(AppointmentPage.this, HomePage.class); // Replace with your target page
//            startActivity(intent);
//        });


        // Toggle buttons for Upcoming and Finished
        findViewById(R.id.btnUpcoming).setOnClickListener(v -> loadFragment(new UpcomingFragment(), true));
        findViewById(R.id.btnFinished).setOnClickListener(v -> loadFragment(new FinishedFragment(), false));

        // Load default fragment (Upcoming)
        loadFragment(new UpcomingFragment(), true);
    }

    private void loadFragment(Fragment fragment, boolean isUpcoming) {
        // Update button backgrounds
        findViewById(R.id.btnUpcoming).setBackgroundTintList(getColorStateList(isUpcoming ? R.color.dark_blue : R.color.light_blue));
        findViewById(R.id.btnFinished).setBackgroundTintList(getColorStateList(isUpcoming ? R.color.light_blue : R.color.dark_blue));

        // Load the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}