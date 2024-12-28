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

public class UserAdapter extends ListAdapter<Users, UserAdapter.UserViewHolder> {

    private final OnItemClickListener listener;

    // Interface for handling clicks
    public interface OnItemClickListener {
        void onItemClick(Users user); // Updated to pass the full user object
    }

    public UserAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Users> DIFF_CALLBACK = new DiffUtil.ItemCallback<Users>() {
        @Override
        public boolean areItemsTheSame(@NonNull Users oldItem, @NonNull Users newItem) {
            // Use unique user property (e.g., name) for comparison
            return oldItem.getName() != null && oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Users oldItem, @NonNull Users newItem) {
            return oldItem.equals(newItem); // Compare the entire object
        }
    };

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the user item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_online_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Users user = getItem(position);
        if (user != null) {
            holder.name.setText(user.getName() != null ? user.getName() : "Unknown User");
            holder.phoneNo.setText(user.getPhoneNo() != null ? user.getPhoneNo() : "Unknown Phone Number");

            // Set login status and color dynamically
            Boolean loginStatus = user.getLoginStatus();
            if (loginStatus != null) {


                if (loginStatus) {
                    holder.loginStatus.setText("Status: Online");
                    holder.loginStatus.setTextColor(Color.GREEN);
                } else {
                    holder.loginStatus.setText("Status: Offline");
                    holder.loginStatus.setTextColor(Color.RED);
                }
            } else {
                holder.loginStatus.setText("Status: Unknown");
                holder.loginStatus.setTextColor(Color.GRAY);
            }

            // Set click listener for item
            holder.itemView.setOnClickListener(v -> listener.onItemClick(user));
        }
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name, phoneNo, loginStatus;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewUserName);
            phoneNo = itemView.findViewById(R.id.textViewPhoneNumber);
            loginStatus = itemView.findViewById(R.id.textViewUserLoginStatus);
        }
    }
}
