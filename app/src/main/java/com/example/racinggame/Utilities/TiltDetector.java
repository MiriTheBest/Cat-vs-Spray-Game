package com.example.racinggame.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.racinggame.Interfaces.TiltCallback;

public class TiltDetector {
    private Sensor sensor;
    private SensorManager sensorManager;

    private TiltCallback tiltCallback;
    private long timestamp = 0;

    private SensorEventListener sensorEventListener;

    public TiltDetector(Context context, TiltCallback tiltCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.tiltCallback = tiltCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                identifyTilt(x);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };
    }

    private void identifyTilt(float x) {
        if (System.currentTimeMillis() - timestamp > 500) {
            timestamp = System.currentTimeMillis();

            if (x > 4.0) {
                tiltCallback.turnLeftSensor();
            }
            if (x < -4.0) {
                tiltCallback.turnRightSensor();
            }
        }
    }

    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }

}