package com.example.madpart1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

        return view;
    }
}