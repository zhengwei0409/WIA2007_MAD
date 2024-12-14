package com.example.madpart1;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
    private List<Doctor> doctorList;

    public DoctorAdapter(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_layout, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.name.setText(doctor.getName());
        holder.department.setText(doctor.getDepartment());

        // Set login status text and color based on loginStatus
        String loginStatus = doctor.getLoginStatus();  // assuming loginStatus is a String (online/offline)
        holder.loginStatus.setText("Status: " + loginStatus);  // Display status

        // Set the color based on login status
        if ("online".equalsIgnoreCase(loginStatus)) {
            holder.loginStatus.setTextColor(Color.GREEN);  // You can set any color for online status
        } else {
            holder.loginStatus.setTextColor(Color.RED);    // You can set any color for offline status
        }
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView name, department, loginStatus;  // Change availability to loginStatus

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewDoctorName);
            department = itemView.findViewById(R.id.textViewDoctorDepartment);
            loginStatus = itemView.findViewById(R.id.textViewDoctorLoginStatus);  // Bind loginStatus
        }
    }
}
