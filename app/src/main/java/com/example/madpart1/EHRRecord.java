package com.example.madpart1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EHRRecord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EHRRecord extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EHRRecord() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EHRRecord.
     */
    // TODO: Rename and change types and number of parameters
    public static EHRRecord newInstance(String param1, String param2) {
        EHRRecord fragment = new EHRRecord();
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
        View view = inflater.inflate(R.layout.fragment_e_h_r_record, container, false);

        ImageView record1 = view.findViewById(R.id.folder1);
        record1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new Record1();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new fragment
                transaction.replace(R.id.fragmentContainerView, newFragment);

                // Optional: Add this transaction to the back stack
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        TextView record1T = view.findViewById(R.id.MRtext1);
        record1T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new Record1();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new fragment
                transaction.replace(R.id.fragmentContainerView, newFragment);

                // Optional: Add this transaction to the back stack
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        ImageView record2 = view.findViewById(R.id.folder2);
        record2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new Record2();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new fragment
                transaction.replace(R.id.fragmentContainerView, newFragment);

                // Optional: Add this transaction to the back stack
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        TextView record2T = view.findViewById(R.id.MRtext2);
        record2T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new Record2();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new fragment
                transaction.replace(R.id.fragmentContainerView, newFragment);

                // Optional: Add this transaction to the back stack
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        ImageView record3 = view.findViewById(R.id.folder3);
        record3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new Record3();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new fragment
                transaction.replace(R.id.fragmentContainerView, newFragment);

                // Optional: Add this transaction to the back stack
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        TextView record3T = view.findViewById(R.id.MRtext3);
        record3T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new Record3();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new fragment
                transaction.replace(R.id.fragmentContainerView, newFragment);

                // Optional: Add this transaction to the back stack
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });


        ImageView record4 = view.findViewById(R.id.folder4);
        record4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new Record4();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new fragment
                transaction.replace(R.id.fragmentContainerView, newFragment);

                // Optional: Add this transaction to the back stack
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        TextView record4T = view.findViewById(R.id.MRtext4);
        record4T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new Record4();

                // Get the FragmentManager
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Begin the transaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new fragment
                transaction.replace(R.id.fragmentContainerView, newFragment);

                // Optional: Add this transaction to the back stack
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });


        return view;
    }
}