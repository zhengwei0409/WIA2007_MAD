package com.example.madpart1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_page, container, false);

        // Find the ImageButton by ID
        ImageButton notificationBtn = view.findViewById(R.id.notification);

        // Set an OnClickListener
        notificationBtn.setOnClickListener(v -> {
            // Start a new activity using an Intent
            Intent intent = new Intent(getActivity(), NotificationsMenu.class);
            startActivity(intent);
        });

        ImageButton profileBtn = view.findViewById(R.id.profile_pic);

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),ProfilePage.class);
            startActivity(intent);
        });

        ImageButton btnAppointment = view.findViewById(R.id.btnAppointment);

        btnAppointment.setOnClickListener(v -> {
            // Start a new activity using an Intent
            Intent intent = new Intent(getActivity(), AppointmentPage.class);
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

        db.collection("Appointments")
                .limit(2) // Fetch the first 2 documents
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        appointmentList.clear(); // Clear the current list to avoid duplicates
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            Appointment appointment = document.toObject(Appointment.class);
                            appointmentList.add(appointment);
                        }
                        appointmentAdapter.notifyDataSetChanged(); // Notify adapter about the updated list
                    } else {
                        Toast.makeText(getContext(), "No appointments found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore Error", e.getMessage(), e);
                    Toast.makeText(getContext(), "Failed to load appointments.", Toast.LENGTH_SHORT).show();
                });
    }

}