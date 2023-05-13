package com.example.racinggame;

import android.app.Application;

import com.example.racinggame.Utilities.MySP;
import com.example.racinggame.Utilities.SignalGenerator;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySP.init(this);
        SignalGenerator.init(this);
    }
}