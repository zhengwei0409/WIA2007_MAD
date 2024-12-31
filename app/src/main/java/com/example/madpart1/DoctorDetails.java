package com.example.madpart1;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DoctorDetails extends AppCompatActivity {

    private ImageView backImageView;
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
    private EditText dateEditText; // Added: EditText for the appointment date
    private Spinner timeSpinner;  // Added: Spinner for selecting the appointment time
    private Button bookNowButton; // Added: Button for booking the appointment

    private String doctorName; // Used dynamically for Firestore and display

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
        // ----- notification part -------
        createNotificationChannel();

//        loadingPage = findViewById(R.id.loading_overlay);
//        loadingPage.setVisibility(View.VISIBLE);

        String doctorUID = getIntent().getStringExtra("doctorUID");


        backImageView = findViewById(R.id.IVBack);
        doctorNameTextView = findViewById(R.id.doctorNameTextView);
        doctorSpecialtyTextView = findViewById(R.id.doctorSpecialtyTextView);
        ratingsTextView = findViewById(R.id.ratingsTextView);
        reviewsTextView = findViewById(R.id.reviewsTextView);
        numberReviewsTextView = findViewById(R.id.numberReviewsTextView);
        patientsTextView = findViewById(R.id.patientsTextView);
        experienceTextView = findViewById(R.id.experienceTextView);
        demographyContentTextView = findViewById(R.id.demographyContentsTextView);
        doctorImageView = findViewById(R.id.doctorImage); // Added: Initialize the ImageView
        dateEditText = findViewById(R.id.dateEditText); // Added: Initialize EditText
        timeSpinner = findViewById(R.id.timeSpinner);   // Added: Initialize Spinner
        bookNowButton = findViewById(R.id.bookNowButton); // Added: Initialize Button

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        FirebaseFirestore.getInstance().collection("Doctors").document(doctorUID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                doctorName = documentSnapshot.getString("name"); // Updated: Store doctor name
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

        // Added: Set OnClickListener for bookNowButton
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBookNow();

                // Schedule the notification for December 7, 2024, at 3:00 PM
                scheduleNotification(2024, 12, 31, 9, 51); // 15:00 is 3:00 PM in 24-hour format


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

    // Define a callback interface
    interface UserNameCallback {
        void onUserNameRetrieved(String userName);
    }

    // Added: Method to handle the booking logic
    private void handleBookNow() {
        String dateInput = dateEditText.getText().toString().trim();
        String selectedTime = timeSpinner.getSelectedItem().toString();

        // Validate date format (YYYY-MM-DD)
        if (!isValidDate(dateInput)) {
            Toast.makeText(this, "Please enter the date in YYYY-MM-DD format.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve user name asynchronously
        getUserNameFromUID(userName -> {
            if (userName == null) {
                Toast.makeText(DoctorDetails.this, "User name not available. Please try again.", Toast.LENGTH_SHORT).show();
                return;
            }


            // Add appointment to Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> appointmentData = new HashMap<>();
            appointmentData.put("date", dateInput);
            appointmentData.put("time", selectedTime);
            appointmentData.put("doctorAssigned", doctorName); // Using doctorName dynamically
            appointmentData.put("status", "UpComing");
            appointmentData.put("user", userName);

            db.collection("Appointments")
                    .add(appointmentData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(DoctorDetails.this, "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(DoctorDetails.this, "Error booking appointment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

                // Updated method to retrieve user name based on UID using a callback
        private void getUserNameFromUID(UserNameCallback callback) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            String uid = auth.getCurrentUser().getUid();

            db.collection("users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String userName = documentSnapshot.getString("Full name");
                            callback.onUserNameRetrieved(userName);
                        } else {
                            Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show();
                            callback.onUserNameRetrieved(null);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error retrieving user name: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        callback.onUserNameRetrieved(null);
                    });
        }

    // Added: Method to validate date format
    private boolean isValidDate(String date) {
        String datePattern = "^(\\d{4})-(\\d{2})-(\\d{2})$";
        return !TextUtils.isEmpty(date) && Pattern.matches(datePattern, date);
    }

    // below method are for notification
    private void createNotificationChannel() {
        CharSequence name = "MyNotificationChannel";
        String description = "Channel for scheduled notifications";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("myChannelId", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleNotification(int year, int month, int day, int hour, int minute) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // API 31+
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return; // Exit the method as the exact alarm is not enabled yet
            }
        }

        // Create a Calendar instance and set the date and time
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, 0); // Note: Month is 0-based in Calendar
        long triggerAtMillis = calendar.getTimeInMillis();

        // Check if the time is in the past
        if (triggerAtMillis < System.currentTimeMillis()) {
            Toast.makeText(this, "The selected time is in the past!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare the intent and PendingIntent as before
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("notificationId", 1);
        intent.putExtra("message", "Don’t forget about appointment!");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }

        Toast.makeText(this, "Notification scheduled for " + day + "/" + month + "/" + year + " " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
    }
}