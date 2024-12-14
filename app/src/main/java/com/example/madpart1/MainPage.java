package com.example.madpart1;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private TextView doctorName1;
    private TextView doctorName2;
    private TextView doctorName3;
    private TextView doctorDepartment1;
    private TextView doctorDepartment2;
    private TextView doctorDepartment3;
    private TextView doctorRating1;
    private TextView doctorRating2;
    private TextView doctorRating3;

    //private RecyclerView recyclerView;
    //private DoctorAdapter doctorAdapter;
    //private List<Doctor> doctorList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        firstDoctorButton = view.findViewById(R.id.top1_btn);
        secondDoctorButton = view.findViewById(R.id.top2_btn);
        thirdDoctorButton = view.findViewById(R.id.top3_btn);

        seeAllButton = view.findViewById(R.id.doctor_list_btn);

        findDoctorButton = view.findViewById(R.id.doctor_btn);

        doctorName1 = view.findViewById(R.id.dr_name);
        doctorName2 = view.findViewById(R.id.dr_name2);
        doctorName3 = view.findViewById(R.id.dr_name3);
        doctorDepartment1 = view.findViewById(R.id.dr_type);
        doctorDepartment2 = view.findViewById(R.id.dr_type2);
        doctorDepartment3 = view.findViewById(R.id.dr_type3);
        doctorRating1 = view.findViewById(R.id.ratings);
        doctorRating2 = view.findViewById(R.id.ratings2);
        doctorRating3 = view.findViewById(R.id.ratings3);

        FrameLayout loadingPage = view.findViewById(R.id.loading_overlay);
        loadingPage.setVisibility(View.VISIBLE);


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

        ImageButton imageButton = view.findViewById(R.id.profile_btn);

        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),ProfilePage.class);
            startActivity(intent);
        });

        FirebaseFirestore.getInstance().collection("Doctors").document("2iyRLsCJGT8Fvoa4ZvQV").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name1 = documentSnapshot.getString("name");
                String department1 = documentSnapshot.getString("department");
                Double ratings1 = documentSnapshot.getDouble("ratings");

                doctorName1.setText(name1);
                doctorDepartment1.setText(department1);
                doctorRating1.setText(ratings1.toString());
            }
        });

        FirebaseFirestore.getInstance().collection("Doctors").document("PPB7BRjvKriQkcs5bRuy").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name2 = documentSnapshot.getString("name");
                String department2 = documentSnapshot.getString("department");
                Double ratings2 = documentSnapshot.getDouble("ratings");

                doctorName2.setText(name2);
                doctorDepartment2.setText(department2);
                doctorRating2.setText(ratings2.toString());
            }
        });

        FirebaseFirestore.getInstance().collection("Doctors").document("W9F5SabcegStwfkA8KIQ").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                loadingPage.setVisibility(View.GONE);

                String name3 = documentSnapshot.getString("name");
                String department3 = documentSnapshot.getString("department");
                Double ratings3 = documentSnapshot.getDouble("ratings");

                doctorName3.setText(name3);
                doctorDepartment3.setText(department3);
                doctorRating3.setText(ratings3.toString());
            }
        });

        firstDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoctorDetails.class);
                intent.putExtra("doctorUID", "2iyRLsCJGT8Fvoa4ZvQV");
                startActivity(intent);
            }
        });

        secondDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoctorDetails.class);
                intent.putExtra("doctorUID", "PPB7BRjvKriQkcs5bRuy");
                startActivity(intent);
            }
        });

        thirdDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoctorDetails.class);
                intent.putExtra("doctorUID", "W9F5SabcegStwfkA8KIQ");
                startActivity(intent);
            }
        });




        return view;
    }
}