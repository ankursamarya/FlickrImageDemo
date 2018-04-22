package com.example.flickerimages;

import android.app.Application;
import android.content.Context;


public class FlickrApplication extends Application {
    private static FlickrApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Context getInstance() {
        return mApplication;
    }
}
