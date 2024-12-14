package com.example.madpart1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoHubPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoHubPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfoHubPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoHubPage.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoHubPage newInstance(String param1, String param2) {
        InfoHubPage fragment = new InfoHubPage();
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
        View view = inflater.inflate(R.layout.fragment_info_hub_page, container, false);

        //to covid page
        ImageView covidImage = view.findViewById(R.id.CovidImage);
        View.OnClickListener OCLCovid = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new InfoCovidPage();

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
        };
        covidImage.setOnClickListener(OCLCovid);

        //to coffee page
        ImageView coffeeImage = view.findViewById(R.id.CoffeeImg);
        View.OnClickListener OCLCoffee = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new InfoCoffeeHealth();

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
        };
        coffeeImage.setOnClickListener(OCLCoffee);

        //to walk page
        ImageView walkImage = view.findViewById(R.id.WalkImg);
        View.OnClickListener OCLWalk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new InfoDailyWalk();

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
        };
        walkImage.setOnClickListener(OCLWalk);

        //to dengue fever page
        TextView dengueText = view.findViewById(R.id.DengueFever);
        View.OnClickListener OCLDengue = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new InfoDangueFever();

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
        };
        dengueText.setOnClickListener(OCLDengue);

        ImageView dengueImage = view.findViewById(R.id.MosquitoImg);
        View.OnClickListener OCLMosquito = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new InfoDangueFever();

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
        };
        dengueImage.setOnClickListener(OCLMosquito);

        //to virus mutate page
        TextView virusText = view.findViewById(R.id.VirusMutates);
        View.OnClickListener OCLVirus = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new InfoVirusMutate();

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
        };
        virusText.setOnClickListener(OCLVirus);

        ImageView sneezeImage = view.findViewById(R.id.SneezeImg);
        View.OnClickListener OCLSneeze = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new InfoVirusMutate();

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
        };
        sneezeImage.setOnClickListener(OCLSneeze);

        //to fight bacteria page
        TextView fightText = view.findViewById(R.id.FightBacteria);
        View.OnClickListener OCLFight = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new InfoFightBacteria();

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
        };
        fightText.setOnClickListener(OCLFight);

        ImageView bacteriaImage = view.findViewById(R.id.FightBacteriaImg);
        View.OnClickListener OCLBacteria = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the new fragment instance
                Fragment newFragment = new InfoFightBacteria();

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
        };
        bacteriaImage.setOnClickListener(OCLBacteria);

        //button to save articles
        Button saveButton1 = view.findViewById(R.id.SaveButton1);
        saveButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Article saved.", Toast.LENGTH_SHORT).show();
            }
        });

        Button saveButton2 = view.findViewById(R.id.SaveButton2);
        saveButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Article saved.", Toast.LENGTH_SHORT).show();
            }
        });

        Button saveButton3 = view.findViewById(R.id.SaveButton3);
        saveButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Article saved.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}