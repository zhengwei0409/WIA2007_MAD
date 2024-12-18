package com.example.madpart1;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorAdapter extends ListAdapter<Doctor, DoctorAdapter.DoctorViewHolder> {

    private OnItemClickListener listener;

    // Interface for handling clicks
    public interface OnItemClickListener {
        void onItemClick(String doctorName);
    }

    public DoctorAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Doctor> DIFF_CALLBACK = new DiffUtil.ItemCallback<Doctor>() {
        @Override
        public boolean areItemsTheSame(@NonNull Doctor oldItem, @NonNull Doctor newItem) {
            return oldItem.getName().equals(newItem.getName());  // Use doctor's name for uniqueness
        }

        @Override
        public boolean areContentsTheSame(@NonNull Doctor oldItem, @NonNull Doctor newItem) {
            return oldItem.equals(newItem);  // Compare the entire object
        }
    };

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the doctor item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_layout, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = getItem(position);
        holder.name.setText(doctor.getName());
        holder.department.setText(doctor.getDepartment());

        // Set login status text and color based on loginStatus
        String loginStatus = doctor.getLoginStatus();
        holder.loginStatus.setText("Status: " + loginStatus);

        if ("online".equalsIgnoreCase(loginStatus)) {
            holder.loginStatus.setTextColor(Color.GREEN);
        } else {
            holder.loginStatus.setTextColor(Color.RED);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(doctor.getName()));
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();  // This will return the list size
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView name, department, loginStatus;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewDoctorName);
            department = itemView.findViewById(R.id.textViewDoctorDepartment);
            loginStatus = itemView.findViewById(R.id.textViewDoctorLoginStatus);
        }
    }
}
