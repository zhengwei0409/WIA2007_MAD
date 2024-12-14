package com.example.madpart1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View mWindow;
    private final Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null); // Use context here
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        TextView title = mWindow.findViewById(R.id.title);
        TextView snippet = mWindow.findViewById(R.id.rating);

        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());

        return  mWindow;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        // Customize this method if you need a custom frame for the info window.
        return null;  // Default frame
    }
}
