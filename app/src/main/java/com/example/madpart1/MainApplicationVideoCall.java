package com.example.madpart1;

import android.app.Application;

import live.videosdk.rtc.android.VideoSDK;

public class MainApplicationVideoCall extends Application {
    public void onCreate() {
        super.onCreate();
        // Initialize VideoSDK once for the entire app
        VideoSDK.initialize(getApplicationContext());
    }
}
