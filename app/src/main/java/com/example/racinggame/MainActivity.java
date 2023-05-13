package com.example.racinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.racinggame.Interfaces.TiltCallback;
import com.example.racinggame.Logic.GameManager;
import com.example.racinggame.Utilities.TiltDetector;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private final int rows = 6;
    private final int cols = 5;
    private AppCompatImageView main_IMG_background;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[][] main_IMG_sprays;
    private MaterialTextView main_LBL_score;
    private ShapeableImageView[][] main_IMG_rewards;
    private ShapeableImageView[] main_IMG_player;
    private ExtendedFloatingActionButton FAB_left;
    private ExtendedFloatingActionButton FAB_right;
    private static int playerCol = 2;
    public static final String DELAY_KEY = "DELAY_KEY";
    public static final String SENSOR_KEY = "SENSOR_KEY";
    private boolean ifSensor = false;
    private int DELAY = 700;
    public boolean gameEnded = false;
    private Random random;
    private final Handler handler = new Handler();
    private GameManager gameManager;
    private TiltDetector tiltDetector;
    private LocationManager locationManager;
    private Location location;
    private double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        gameManager = new GameManager(main_IMG_hearts.length);

        FAB_left.setOnClickListener(view -> playerTurnLeft());
        FAB_right.setOnClickListener(view -> playerTurnRight());

        initMap();

        Intent intent = getIntent();
        if(intent != null) {
            DELAY = intent.getIntExtra(MainActivity.DELAY_KEY, 700);
        }

        Intent intentSensor = getIntent();
        if(intentSensor != null) {
            ifSensor = intent.getBooleanExtra(MainActivity.SENSOR_KEY, false);
        }

        if (ifSensor == true) {
            initTiltDetector();
        }

        random = new Random();
        handler.post(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            checkCrash();
            moveIcons();
            randomStart();
            handler.postDelayed(this, DELAY);
        }
    };


    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};
        FAB_left = findViewById(R.id.button_FAB_left);
        FAB_right = findViewById(R.id.button_FAB_right);
        main_IMG_sprays = new ShapeableImageView[][]{
                {findViewById(R.id.main_IMG_spray1), findViewById(R.id.main_IMG_spray2), findViewById(R.id.main_IMG_spray3), findViewById(R.id.main_IMG_spray4), findViewById(R.id.main_IMG_spray5)},
                {findViewById(R.id.main_IMG_spray6), findViewById(R.id.main_IMG_spray7), findViewById(R.id.main_IMG_spray8), findViewById(R.id.main_IMG_spray9), findViewById(R.id.main_IMG_spray10)},
                {findViewById(R.id.main_IMG_spray11), findViewById(R.id.main_IMG_spray12), findViewById(R.id.main_IMG_spray13), findViewById(R.id.main_IMG_spray14), findViewById(R.id.main_IMG_spray15)},
                {findViewById(R.id.main_IMG_spray16), findViewById(R.id.main_IMG_spray17), findViewById(R.id.main_IMG_spray18), findViewById(R.id.main_IMG_spray19), findViewById(R.id.main_IMG_spray20)},
                {findViewById(R.id.main_IMG_spray21), findViewById(R.id.main_IMG_spray22), findViewById(R.id.main_IMG_spray23), findViewById(R.id.main_IMG_spray24), findViewById(R.id.main_IMG_spray25)},
                {findViewById(R.id.main_IMG_spray26), findViewById(R.id.main_IMG_spray27), findViewById(R.id.main_IMG_spray28), findViewById(R.id.main_IMG_spray29), findViewById(R.id.main_IMG_spray30)}
        };
        main_IMG_rewards = new ShapeableImageView[][]{
                {findViewById(R.id.main_IMG_food1), findViewById(R.id.main_IMG_food2), findViewById(R.id.main_IMG_food3), findViewById(R.id.main_IMG_food4), findViewById(R.id.main_IMG_food5)},
                {findViewById(R.id.main_IMG_food6), findViewById(R.id.main_IMG_food7), findViewById(R.id.main_IMG_food8), findViewById(R.id.main_IMG_food9), findViewById(R.id.main_IMG_food10)},
                {findViewById(R.id.main_IMG_food11), findViewById(R.id.main_IMG_food12), findViewById(R.id.main_IMG_food13), findViewById(R.id.main_IMG_food14), findViewById(R.id.main_IMG_food15)},
                {findViewById(R.id.main_IMG_food16), findViewById(R.id.main_IMG_food17), findViewById(R.id.main_IMG_food18), findViewById(R.id.main_IMG_food19), findViewById(R.id.main_IMG_food20)},
                {findViewById(R.id.main_IMG_food21), findViewById(R.id.main_IMG_food22), findViewById(R.id.main_IMG_food23), findViewById(R.id.main_IMG_food24), findViewById(R.id.main_IMG_food25)},
                {findViewById(R.id.main_IMG_food26), findViewById(R.id.main_IMG_food27), findViewById(R.id.main_IMG_food28), findViewById(R.id.main_IMG_food29), findViewById(R.id.main_IMG_food30)}
        };

        main_IMG_player = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_cat1),
                findViewById(R.id.main_IMG_cat2),
                findViewById(R.id.main_IMG_cat3),
                findViewById(R.id.main_IMG_cat4),
                findViewById(R.id.main_IMG_cat5)};

        main_LBL_score = findViewById(R.id.main_LBL_score);
    }

    private void playerTurnRight() {
        if (playerCol < cols - 1) {
            main_IMG_player[playerCol].setVisibility(View.INVISIBLE);
            main_IMG_player[++playerCol].setVisibility(View.VISIBLE);
        }
    }

    private void playerTurnLeft() {
        if (playerCol > 0) {
            main_IMG_player[playerCol].setVisibility(View.INVISIBLE);
            main_IMG_player[--playerCol].setVisibility(View.VISIBLE);
        }
    }

    private void checkIfLoseLife(int sprayCol, int playerCol) {
        int check = gameManager.checkCrash(sprayCol, playerCol);
        if (check == 1) {
            int lives = gameManager.getLife();
            if (lives > 0)
                main_IMG_hearts[lives - 1].setVisibility(View.INVISIBLE);
        }
    }

    private void checkCrash() {
        for (int j = 0; j < cols; j++) {
            if (main_IMG_rewards[5][j].getVisibility() == View.VISIBLE) {
                gameManager.checkIfAddScore(j, playerCol);
                main_LBL_score.setText(""+ gameManager.getScore());
                main_IMG_rewards[5][j].setVisibility(View.INVISIBLE);
            }

            if (main_IMG_sprays[5][j].getVisibility() == View.VISIBLE && gameEnded == false) {
                checkIfLoseLife(j, playerCol);
                main_IMG_sprays[5][j].setVisibility(View.INVISIBLE);

                if(gameManager.isGameEnded() && gameEnded == false) {
                    gameEnded = true;
                    gameManager.gameOver(latitude, longitude);
                    openScoreScreen();
                }
            }
        }
        gameManager.addRegularScore();
        main_LBL_score.setText(""+ gameManager.getScore());
    }

    private void moveIcons() {
        for (int i = rows-2; i >= 0; i--) {
            for (int j = 0; j < cols; j++) {
                if (main_IMG_sprays[i][j].getVisibility() == View.VISIBLE) {
                    main_IMG_sprays[i][j].setVisibility(View.INVISIBLE);
                    main_IMG_sprays[i + 1][j].setVisibility(View.VISIBLE);
                }
            }
        }

        for (int i = rows-2; i >= 0; i--) {
            for (int j = 0; j < cols; j++) {

                if (main_IMG_rewards[i][j].getVisibility() == View.VISIBLE) {
                    main_IMG_rewards[i][j].setVisibility(View.INVISIBLE);
                    main_IMG_rewards[i+1][j].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void randomStart() {
        int sprayCol = random.nextInt(cols);
        int rewardCol = random.nextInt(cols);
        if(sprayCol == rewardCol)
            main_IMG_rewards[0][rewardCol].setVisibility(View.VISIBLE);
        else
            main_IMG_sprays[0][sprayCol].setVisibility(View.VISIBLE);
    }


    private void openScoreScreen() {
        Log.d("MainActivity", "Opening ScoreActivity...");
        Intent scoreIntent = new Intent(MainActivity.this, ScoreActivity.class);
        startActivity(scoreIntent);
        finish();
    }


    private void initTiltDetector() {

        tiltDetector = new TiltDetector(this, new TiltCallback() {
            @Override
            public void turnRightSensor() {
                playerTurnRight();
            }

            @Override
            public void turnLeftSensor() {
                playerTurnLeft();
            }
        });
        FAB_left.setVisibility(View.INVISIBLE);
        FAB_right.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(ifSensor == true)
            tiltDetector.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(ifSensor == true)
            tiltDetector.stop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish(); // this will destroy the current activity
    }

    private void initMap() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        });
    }
}


