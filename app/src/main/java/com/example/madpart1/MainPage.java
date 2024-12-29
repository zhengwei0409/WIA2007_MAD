package com.example.madpart1;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPage.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPage newInstance(String param1, String param2) {
        MainPage fragment = new MainPage();
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

    private ImageButton firstDoctorButton;
    private ImageButton secondDoctorButton;
    private ImageButton thirdDoctorButton;
    private Button seeAllButton;
    private ConstraintLayout findDoctorButton;
    private ConstraintLayout findHostpitalBtn; //ZhengWei
    private TextView doctorName1;
    private TextView doctorName2;
    private TextView doctorName3;
    private TextView doctorDepartment1;
    private TextView doctorDepartment2;
    private TextView doctorDepartment3;
    private TextView doctorRating1;
    private TextView doctorRating2;
    private TextView doctorRating3;

    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctorList;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        recyclerView = view.findViewById(R.id.doctor_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        doctorAdapter = new DoctorAdapter(doctorName -> {
            // Handle doctor item click
            Toast.makeText(getContext(), "Selected: " + doctorName, Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(doctorAdapter);

        firstDoctorButton = view.findViewById(R.id.top1_btn);
        secondDoctorButton = view.findViewById(R.id.top2_btn);
        thirdDoctorButton = view.findViewById(R.id.top3_btn);

        seeAllButton = view.findViewById(R.id.doctor_list_btn);

        findDoctorButton = view.findViewById(R.id.doctor_btn);
        findHostpitalBtn = view.findViewById(R.id.hospital_btn);

        doctorName1 = view.findViewById(R.id.dr_name);
        doctorName2 = view.findViewById(R.id.dr_name2);
        doctorName3 = view.findViewById(R.id.dr_name3);
        doctorDepartment1 = view.findViewById(R.id.dr_type);
        doctorDepartment2 = view.findViewById(R.id.dr_type2);
        doctorDepartment3 = view.findViewById(R.id.dr_type3);
        doctorRating1 = view.findViewById(R.id.ratings);
        doctorRating2 = view.findViewById(R.id.ratings2);
        doctorRating3 = view.findViewById(R.id.ratings3);

//        FrameLayout loadingPage = view.findViewById(R.id.loading_overlay);
//        loadingPage.setVisibility(View.VISIBLE);


        seeAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllDoctor.class);
                startActivity(intent);
            }
        });

        findDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllDoctor.class);
                startActivity(intent);
            }
        });

        findHostpitalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HospitalPage.class);
                startActivity(intent);
            }
        });


        fetchDoctorData("2iyRLsCJGT8Fvoa4ZvQV", doctorName1, doctorDepartment1, doctorRating1);
        fetchDoctorData("PPB7BRjvKriQkcs5bRuy", doctorName2, doctorDepartment2, doctorRating2);
        fetchDoctorData("W9F5SabcegStwfkA8KIQ", doctorName3, doctorDepartment3, doctorRating3);

        firstDoctorButton.setOnClickListener(v -> openDoctorDetails("2iyRLsCJGT8Fvoa4ZvQV"));
        secondDoctorButton.setOnClickListener(v -> openDoctorDetails("PPB7BRjvKriQkcs5bRuy"));
        thirdDoctorButton.setOnClickListener(v -> openDoctorDetails("W9F5SabcegStwfkA8KIQ"));

        return view;
    }


    // Fetch doctor data from Firestore
    private void fetchDoctorData(String doctorId, TextView nameView, TextView departmentView, TextView ratingView) {
        db.collection("Doctors").document(doctorId).get().addOnSuccessListener(documentSnapshot -> {
            String name = documentSnapshot.getString("name");
            String department = documentSnapshot.getString("department");
            Double ratings = documentSnapshot.getDouble("ratings");

            nameView.setText(name);
            departmentView.setText(department);
            ratingView.setText(ratings.toString());
        });
    }

    // Open doctor details page
    private void openDoctorDetails(String doctorUID) {
        Intent intent = new Intent(getActivity(), DoctorDetails.class);
        intent.putExtra("doctorUID", doctorUID);
        startActivity(intent);
    }

    // Search doctors based on user input
    private void searchDoctors(String query) {
        if (query.isEmpty()) {
            return;  // No search text, no action
        }

        db.collection("Doctors")
                .whereGreaterThanOrEqualTo("name", query)
                .whereLessThanOrEqualTo("name", query + "\uf8ff")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshot = task.getResult();
                        if (snapshot != null) {
                            List<Doctor> doctors = snapshot.toObjects(Doctor.class);
                            updateDoctorList(doctors);
                        }
                    } else {
                        Log.d("Search", "Error getting documents: ", task.getException());
                    }
                });
    }

    // Update the doctor list in the RecyclerView
    private void updateDoctorList(List<Doctor> doctors) {
        doctorAdapter.submitList(doctors);  // Update the adapter with the new list
    }
}