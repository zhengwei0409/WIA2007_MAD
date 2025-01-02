package com.example.madpart1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinActivity extends AppCompatActivity {

    TextView meetingID;

    private static final int PERMISSION_REQ_ID = 22;

    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
            return false;
        }
        return true;
    }


    //Replace with the token you generated from the VideoSDK Dashboard
    private String sampleToken ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlrZXkiOiI5MDIzZjExZS0yMGI5LTQ5YjItOGFjNi0zOGEzYTc2MTNiZDAiLCJwZXJtaXNzaW9ucyI6WyJhbGxvd19qb2luIl0sImlhdCI6MTczMzcyOTIzMSwiZXhwIjoxNzQxNTA1MjMxfQ.Sgt6IkaEYuZ-BF7ZRqsj9YEKhO2cpNTDp2PSrjS0kHw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID);
        checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID);

        final Button btnCreate = findViewById(R.id.btnCreateMeeting);
        final Button btnJoin = findViewById(R.id.btnJoinMeeting);
        final EditText etMeetingId = findViewById(R.id.etMeetingId);

        btnCreate.setOnClickListener(v -> {
            createMeeting(sampleToken);
        });

        btnJoin.setOnClickListener(v -> {
            Intent intent = new Intent(JoinActivity.this, MeetingActivity.class);
            intent.putExtra("token", sampleToken);
            intent.putExtra("meetingId", etMeetingId.getText().toString());
            startActivity(intent);
        });
    }

    private void createMeeting(String token) {
        // we will make an API call to VideoSDK Server to get a roomId
        AndroidNetworking.post("https://api.videosdk.live/v2/rooms")
                .addHeaders("Authorization", token) //we will pass the token in the Headers
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // response will contain `roomId`
                            final String meetingId = response.getString("roomId");
                            String userName = getIntent().getStringExtra("userName");

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("users")
                                    .whereEqualTo("Full name", userName)
                                    .get()
                                    .addOnSuccessListener(queryDocumentSnapshots -> {
                                        if (!queryDocumentSnapshots.isEmpty()) {
                                            // Update the first matching document
                                            DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                                            db.collection("users").document(document.getId())
                                                    .update("Meeting ID", meetingId)
                                                    .addOnSuccessListener(aVoid -> {
                                                        Toast.makeText(JoinActivity.this, "Meeting ID updated for " + userName, Toast.LENGTH_SHORT).show();


                                                        // starting the MeetingActivity with received roomId and our sampleToken
                                                        Intent intent = new Intent(JoinActivity.this, MeetingActivity.class);
                                                        intent.putExtra("token", sampleToken);
                                                        intent.putExtra("meetingId", meetingId);
                                                        startActivity(intent);
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(JoinActivity.this, "Failed to update Meeting ID: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    });
                                        } else {
                                            Toast.makeText(JoinActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(JoinActivity.this, "Error fetching user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });

                            db.collection("Appointments")
                                    .whereEqualTo("user", userName) // Use appropriate field to identify the user's appointment
                                    .get()
                                    .addOnSuccessListener(queryDocumentSnapshots -> {
                                        if (!queryDocumentSnapshots.isEmpty()) {
                                            DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                                            db.collection("Appointments").document(document.getId())
                                                    .update("Meeting ID", meetingId)
                                                    .addOnSuccessListener(aVoid -> {
                                                        Toast.makeText(JoinActivity.this, "Meeting ID added to Appointments", Toast.LENGTH_SHORT).show();
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(JoinActivity.this, "Failed to update Appointments: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    });
                                        } else {
                                            Toast.makeText(JoinActivity.this, "No appointment found for the user", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(JoinActivity.this, "Error fetching appointment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        Toast.makeText(JoinActivity.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
