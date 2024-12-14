package com.example.madpart1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> itemList;

    // Constructor for the adapter, receiving a list of items
    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate item_layout.xml for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        // Get the data item at the given position
        Item item = itemList.get(position);
        // Set the text for the TextView in the ViewHolder
        holder.name.setText(item.getName());
        holder.date.setText(item.getDate());
        holder.pic.setImageResource(item.getPhoto()); // Use drawable resource ID

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class to hold the view references so that you can set text to textView in onBindViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView date;
        ImageView pic;


        // Constructor to get references to the views
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find the TextView in the item layout (item_layout.xml)
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            pic = itemView.findViewById(R.id.image);
        }
    }
}
