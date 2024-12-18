package com.example.madpart1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {

    private Context context;
    private List<Hospital> hospitalList;

    public HospitalAdapter(Context context, List<Hospital> hospitalList) {
        this.context = context;
        this.hospitalList = hospitalList;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hospital_layout, parent, false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        Hospital hospital = hospitalList.get(position);

        holder.nameTextView.setText(hospital.name);
        holder.ratingTextView.setText(hospital.rating);
        holder.typeTextView.setText(hospital.type);
        holder.addressTextView.setText(hospital.address);
        holder.hospitalImageView.setImageResource(hospital.hospitalImage);
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public static class HospitalViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, ratingTextView, typeTextView, addressTextView;
        ImageView hospitalImageView;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.hospital_name);
            ratingTextView = itemView.findViewById(R.id.hospital_rating);
            typeTextView = itemView.findViewById(R.id.hospital_type);
            addressTextView = itemView.findViewById(R.id.hospital_address);
            hospitalImageView = itemView.findViewById(R.id.hospital_img);
        }
    }
}
