package com.example.madpart1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EHRRecord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EHRRecord extends Fragment {
    private LinearLayout medicalRecord1, medicalRecord2, medicalRecord3, medicalRecord4;
    private SearchView searchBar;
    private LinearLayout rowUpper, rowLower;
    private Spinner sortSpinner;

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

        searchBar = view.findViewById(R.id.SearchView);
        medicalRecord1 = view.findViewById(R.id.medicalRecord1);
        medicalRecord2 = view.findViewById(R.id.medicalRecord2);
        medicalRecord3 = view.findViewById(R.id.medicalRecord3);
        medicalRecord4 = view.findViewById(R.id.medicalRecord4);
        rowUpper = view.findViewById(R.id.medicalRecordsLayoutRow1);
        rowLower = view.findViewById(R.id.medicalRecordsLayoutRow2);
        sortSpinner = view.findViewById(R.id.dropdownSpinner);

        // search bar + spinner
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Get the selected item from the spinner
                String spinnerSelection = sortSpinner.getSelectedItem().toString();

                // Call the appropriate filter method based on spinner selection
                if (spinnerSelection.equals("Earliest")) {
                    filterRecordsEarliest(query);
                } else if (spinnerSelection.equals("Latest")) {
                    filterRecordsLatest(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Get the selected item from the spinner
                String spinnerSelection = sortSpinner.getSelectedItem().toString();

                // Call the appropriate filter method based on spinner selection
                if (spinnerSelection.equals("Earliest")) {
                    filterRecordsEarliest(newText);
                } else if (spinnerSelection.equals("Latest")) {
                    filterRecordsLatest(newText);
                }
                return true;
            }
        });

        // Set up Spinner listener
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Check which option is selected and reorder accordingly
                if (position == 0) { // "Earliest"
                    showEarliestOrder();
                } else if (position == 1) { // "Latest"
                    showLatestOrder();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Optional: handle when no item is selected (if necessary)
            }
        });

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

    private void showEarliestOrder() {
        // Ensure the views are removed from their current parent before adding them to the new parent
        if (medicalRecord1.getParent() != null) {
            ((ViewGroup) medicalRecord1.getParent()).removeView(medicalRecord1);
        }
        if (medicalRecord2.getParent() != null) {
            ((ViewGroup) medicalRecord2.getParent()).removeView(medicalRecord2);
        }
        if (medicalRecord3.getParent() != null) {
            ((ViewGroup) medicalRecord3.getParent()).removeView(medicalRecord3);
        }
        if (medicalRecord4.getParent() != null) {
            ((ViewGroup) medicalRecord4.getParent()).removeView(medicalRecord4);
        }

        // Show records in the default order: 1, 2, 3, 4
        rowUpper.removeAllViews();
        rowUpper.addView(medicalRecord1);
        rowUpper.addView(medicalRecord2);
        rowLower.removeAllViews();
        rowLower.addView(medicalRecord3);
        rowLower.addView(medicalRecord4);
    }

    private void showLatestOrder() {
        // Ensure the views are removed from their current parent before adding them to the new parent
        if (medicalRecord1.getParent() != null) {
            ((ViewGroup) medicalRecord1.getParent()).removeView(medicalRecord1);
        }
        if (medicalRecord2.getParent() != null) {
            ((ViewGroup) medicalRecord2.getParent()).removeView(medicalRecord2);
        }
        if (medicalRecord3.getParent() != null) {
            ((ViewGroup) medicalRecord3.getParent()).removeView(medicalRecord3);
        }
        if (medicalRecord4.getParent() != null) {
            ((ViewGroup) medicalRecord4.getParent()).removeView(medicalRecord4);
        }

        // Show records in reverse order: 4, 3, 2, 1
        rowUpper.removeAllViews();
        rowUpper.addView(medicalRecord4);
        rowUpper.addView(medicalRecord3);
        rowLower.removeAllViews();
        rowLower.addView(medicalRecord2);
        rowLower.addView(medicalRecord1);

        // Ensure margins are applied after re-adding the views
        applyMargins();
    }

    private void filterRecordsEarliest(String query) {
        // Convert query to lowercase for case-insensitive comparison
        query = query.toLowerCase();

        // Show all records by default
        medicalRecord1.setVisibility(View.VISIBLE);
        medicalRecord2.setVisibility(View.VISIBLE);
        medicalRecord3.setVisibility(View.VISIBLE);
        medicalRecord4.setVisibility(View.VISIBLE);

        // If the query is empty or got medical record words, stop filtering
        if (query.isEmpty() || "medical record".toLowerCase().contains(query)) {
            // Reset the layout to show all records
            removeViewFromParent(medicalRecord1);
            removeViewFromParent(medicalRecord2);
            removeViewFromParent(medicalRecord3);
            removeViewFromParent(medicalRecord4);

            rowUpper.removeAllViews();
            rowUpper.addView(medicalRecord1);
            rowUpper.addView(medicalRecord2);
            rowLower.removeAllViews();
            rowLower.addView(medicalRecord3);
            rowLower.addView(medicalRecord4);
            return;
        }

        // Filter based on query
        if (query.contains("1")) {
            // Remove from previous parent
            if (medicalRecord1.getParent() != null) {
                ((ViewGroup) medicalRecord1.getParent()).removeView(medicalRecord1);
            }
            rowUpper.removeAllViews(); // Clear the upper row layout
            rowUpper.addView(medicalRecord1); // Add medicalRecord1 to the upper row
            medicalRecord1.setVisibility(View.VISIBLE);
        } else {
            medicalRecord1.setVisibility(View.GONE);
        }

        if (query.contains("2")) {
            // Remove from previous parent
            if (medicalRecord2.getParent() != null) {
                ((ViewGroup) medicalRecord2.getParent()).removeView(medicalRecord2);
            }
            rowUpper.removeAllViews(); // Clear the upper row layout
            rowUpper.addView(medicalRecord2); // Add medicalRecord2 to the upper row
            medicalRecord2.setVisibility(View.VISIBLE);
        } else {
            medicalRecord2.setVisibility(View.GONE);
        }

        if (query.contains("3")) {
            // Remove from previous parent
            if (medicalRecord3.getParent() != null) {
                ((ViewGroup) medicalRecord3.getParent()).removeView(medicalRecord3);
            }
            rowUpper.removeAllViews(); // Clear the upper row layout
            rowUpper.addView(medicalRecord3); // Add medicalRecord3 to the upper row
            medicalRecord3.setVisibility(View.VISIBLE);
        } else {
            medicalRecord3.setVisibility(View.GONE);
        }

        if (query.contains("4")) {
            // Remove from previous parent
            if (medicalRecord4.getParent() != null) {
                ((ViewGroup) medicalRecord4.getParent()).removeView(medicalRecord4);
            }
            rowUpper.removeAllViews(); // Clear the upper row layout
            rowUpper.addView(medicalRecord4); // Add medicalRecord4 to the upper row
            medicalRecord4.setVisibility(View.VISIBLE);
        } else {
            medicalRecord4.setVisibility(View.GONE);
        }
    }

    private void filterRecordsLatest(String query) {
        // Convert query to lowercase for case-insensitive comparison
        query = query.toLowerCase();

        // Show all records by default
        medicalRecord1.setVisibility(View.VISIBLE);
        medicalRecord2.setVisibility(View.VISIBLE);
        medicalRecord3.setVisibility(View.VISIBLE);
        medicalRecord4.setVisibility(View.VISIBLE);

        // If the query is empty or got medical record words, stop filtering
        if (query.isEmpty() || "medical record".toLowerCase().contains(query)) {
            // Reset the layout to show all records
            removeViewFromParent(medicalRecord1);
            removeViewFromParent(medicalRecord2);
            removeViewFromParent(medicalRecord3);
            removeViewFromParent(medicalRecord4);

            rowUpper.removeAllViews();
            rowUpper.addView(medicalRecord4);
            rowUpper.addView(medicalRecord3);
            rowLower.removeAllViews();
            rowLower.addView(medicalRecord2);
            rowLower.addView(medicalRecord1);
            return;
        }

        // Filter based on query
        if (query.contains("1")) {
            // Remove from previous parent
            if (medicalRecord1.getParent() != null) {
                ((ViewGroup) medicalRecord1.getParent()).removeView(medicalRecord1);
            }
            rowUpper.removeAllViews(); // Clear the upper row layout
            rowUpper.addView(medicalRecord1); // Add medicalRecord1 to the upper row
            medicalRecord1.setVisibility(View.VISIBLE);
        } else {
            medicalRecord1.setVisibility(View.GONE);
        }

        if (query.contains("2")) {
            // Remove from previous parent
            if (medicalRecord2.getParent() != null) {
                ((ViewGroup) medicalRecord2.getParent()).removeView(medicalRecord2);
            }
            rowUpper.removeAllViews(); // Clear the upper row layout
            rowUpper.addView(medicalRecord2); // Add medicalRecord2 to the upper row
            medicalRecord2.setVisibility(View.VISIBLE);
        } else {
            medicalRecord2.setVisibility(View.GONE);
        }

        if (query.contains("3")) {
            // Remove from previous parent
            if (medicalRecord3.getParent() != null) {
                ((ViewGroup) medicalRecord3.getParent()).removeView(medicalRecord3);
            }
            rowUpper.removeAllViews(); // Clear the upper row layout
            rowUpper.addView(medicalRecord3); // Add medicalRecord3 to the upper row
            medicalRecord3.setVisibility(View.VISIBLE);
        } else {
            medicalRecord3.setVisibility(View.GONE);
        }

        if (query.contains("4")) {
            // Remove from previous parent
            if (medicalRecord4.getParent() != null) {
                ((ViewGroup) medicalRecord4.getParent()).removeView(medicalRecord4);
            }
            rowUpper.removeAllViews(); // Clear the upper row layout
            rowUpper.addView(medicalRecord4); // Add medicalRecord4 to the upper row
            medicalRecord4.setVisibility(View.VISIBLE);
        } else {
            medicalRecord4.setVisibility(View.GONE);
        }
    }

    // remove view from parent
    private void removeViewFromParent(View view) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void applyMargins() {
        // margin distance between medical records (in xml is 20dp)
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) medicalRecord1.getLayoutParams();
        params1.setMargins(0, 0, 53, 0);
        medicalRecord1.setLayoutParams(params1);

        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) medicalRecord2.getLayoutParams();
        params2.setMargins(0, 0, 53, 0);
        medicalRecord2.setLayoutParams(params2);

        LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) medicalRecord3.getLayoutParams();
        params3.setMargins(0, 0, 53, 0);
        medicalRecord3.setLayoutParams(params3);

        LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams) medicalRecord4.getLayoutParams();
        params4.setMargins(0, 0, 53, 0);
        medicalRecord4.setLayoutParams(params4);
    }
}