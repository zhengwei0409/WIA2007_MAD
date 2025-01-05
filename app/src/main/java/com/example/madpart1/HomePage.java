package com.example.madpart1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView nameHomePage, meetingID;
    private FirebaseFirestore db;
    FirebaseUser user;
    String userID;
    FirebaseAuth mAuth;
    ImageButton meetNow, userButton;
    private static final String TAG = "ProfilePage";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView appointmentRecyclerView;
    private AppointmentAdapter appointmentAdapter;
    private List<Appointment> appointmentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        meetingID = view.findViewById(R.id.mID);
        db.collection("users").document(userID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Read the "Meeting ID" field
                        String meetingIDdb = documentSnapshot.getString("Meeting ID");
                        if (meetingID != null)
                            meetingID.setText(meetingIDdb);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                });

        nameHomePage = view.findViewById(R.id.homePageName);

        db.collection("users").document(userID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        UserDetails userDetails = documentSnapshot.toObject(UserDetails.class);
                        if (userDetails != null) {
                            String fullName = userDetails.getFullName();
                            nameHomePage.setText(fullName.substring(0, fullName.indexOf(" ")));
                        }
                    } else {
                        Log.d(TAG, "No such document exists.");
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error fetching document", e));

        // Find the ImageButton by ID
        ImageButton notificationBtn = view.findViewById(R.id.notification);

        meetNow = view.findViewById(R.id.MeetingNowButton);

        meetNow.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), JoinActivity.class);
            startActivity(intent);
        });


        userButton = view.findViewById(R.id.user_btn);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllUsers.class);
                startActivity(intent);
            }
        });

        // Set an OnClickListener
        notificationBtn.setOnClickListener(v -> {
            // Start a new activity using an Intent
            Intent intent = new Intent(getActivity(), NotificationsMenu.class);
            startActivity(intent);
        });

        ImageButton profileBtn = view.findViewById(R.id.profile_pic);
        loadProfileImage(profileBtn);

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfilePage.class);
            startActivity(intent);
        });

        ImageButton btnAppointment = view.findViewById(R.id.btnAppointment);
        ImageButton chatroom_btn = view.findViewById(R.id.chatroom_btn);

        btnAppointment.setOnClickListener(v -> {
            // Start a new activity using an Intent
            Intent intent = new Intent(getActivity(), AppointmentPage.class);
            startActivity(intent);
        });

        chatroom_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChatroomActivity.class);
            startActivity(intent);
        });

        appointmentRecyclerView = view.findViewById(R.id.appointmentRecyclerView);
        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Appointment List and Adapter
        appointmentList = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(getContext(), appointmentList);
        appointmentRecyclerView.setAdapter(appointmentAdapter);

        // Load Appointments from Firestore
        loadAppointments();

        return view;
    }

    private void loadAppointments() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String uid = auth.getCurrentUser().getUid();

        // Fetch the logged-in user's name
        db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String userName = documentSnapshot.getString("Full name");
                        db.collection("Appointments")
                                .whereEqualTo("status", "UpComing") // Filter only Upcoming appointments
                                .whereEqualTo("user", userName) // Match appointments for the current user
                                .limit(2) // Fetch the first 2 documents
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        appointmentList.clear(); // Clear the current list to avoid duplicates
                                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                            Appointment appointment = document.toObject(Appointment.class);
                                            appointmentList.add(appointment);
                                        }

                                        // Sort appointments by date
                                        Collections.sort(appointmentList, (a1, a2) -> {
                                            try {
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                                Date date1 = sdf.parse(a1.getDate());
                                                Date date2 = sdf.parse(a2.getDate());
                                                return date1.compareTo(date2); // Ascending order
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                                return 0; // If parsing fails, treat as equal
                                            }
                                        });

                                        // Notify adapter about the updated list
                                        appointmentAdapter.notifyDataSetChanged();
                                    }
//                                    else {
//                                        Toast.makeText(getContext(), "No upcoming appointments found.", Toast.LENGTH_SHORT).show();
//                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Firestore Error", e.getMessage(), e);
                                    Toast.makeText(getContext(), "Failed to load appointments.", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(getContext(), "User not found. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore Error", e.getMessage(), e);
                    Toast.makeText(getContext(), "Failed to retrieve user information.", Toast.LENGTH_SHORT).show();
                });
    }
    private void loadProfileImage(ImageButton profileBtn) {
        db.collection("users").document(userID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String dob = documentSnapshot.getString("Date of Birth");
                        if (dob != null && !dob.trim().isEmpty()) {
                            // Extract day (DD) from "DD / MM / YYYY"
                            String[] dobParts = dob.split(" / ");
                            if (dobParts.length >= 1) {
                                try {
                                    int day = Integer.parseInt(dobParts[0].trim());
                                    int imageIndex = day % 10; // Calculate DD % 10
                                    fetchProfileImage(imageIndex, profileBtn);
                                } catch (NumberFormatException e) {
                                    Log.e(TAG, "Invalid day format in Date of Birth", e);
                                }
                            } else {
                                Log.e(TAG, "Date of Birth format is incorrect: " + dob);
                            }
                        } else {
                            Log.e(TAG, "Date of Birth is null or empty");
                        }
                    } else {
                        Log.e(TAG, "User document does not exist");
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error fetching Date of Birth", e));
    }

    private void fetchProfileImage(int imageIndex, ImageButton profileBtn) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String imageName = imageIndex + ".jpg"; // Example: 0.jpg, 1.jpg, etc.
        StorageReference storageRef = storage.getReference().child(imageName);

        try {
            final File tempFile = File.createTempFile("profile", "jpg");
            storageRef.getFile(tempFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
                        profileBtn.setImageBitmap(bitmap);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to load profile picture", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error fetching profile picture from Firebase", e);
                    });
        } catch (IOException e) {
            Log.e(TAG, "Error creating temporary file for profile picture", e);
        }
    }
}