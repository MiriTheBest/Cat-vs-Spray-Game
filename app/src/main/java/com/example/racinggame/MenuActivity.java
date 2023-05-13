package com.example.racinggame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MenuActivity extends AppCompatActivity {

    private Intent intent;
    private MaterialButton slowButtonMode;
    private MaterialButton fastButtonMode;
    private MaterialButton sensorMode;
    private MaterialButton scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startmenu);

        findViews();

        intent = new Intent(this, MainActivity.class);
        slowButtonMode.setOnClickListener(v -> {
            intent.putExtra(MainActivity.DELAY_KEY, 1200);
            startActivity(intent);
        });

        fastButtonMode.setOnClickListener(v -> {
            intent.putExtra(MainActivity.DELAY_KEY, 700);
            startActivity(intent);
        });

        sensorMode.setOnClickListener(v -> {
            intent.putExtra(MainActivity.SENSOR_KEY, true);
            startActivity((intent));
        });

        scores.setOnClickListener(v -> {
            Intent intent2 = new Intent(MenuActivity.this, ScoreActivity.class);
            startActivity((intent2));
        });
    }

    private void findViews() {
        slowButtonMode = findViewById(R.id.main_BTN_button1);
        fastButtonMode = findViewById(R.id.main_BTN_button2);
        sensorMode = findViewById(R.id.main_BTN_button3);
        scores = findViewById(R.id.main_scores);
    }


}
