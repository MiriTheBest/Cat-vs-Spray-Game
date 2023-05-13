package com.example.racinggame;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.racinggame.Fragments.ListFragment;
import com.example.racinggame.Fragments.MapFragment;
import com.example.racinggame.Interfaces.CallBack_SendClick;
import com.example.racinggame.Models.Score;

public class ScoreActivity extends AppCompatActivity {
    private ListFragment listFragment;
    private MapFragment mapFragment;

    CallBack_SendClick callBack_SendClick = new CallBack_SendClick() {
        @Override
        public void scoreChosen(Score score) {
            mapFragment.zoomOnUserLocation(score.getmLatitude(), score.getmLongitude());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        initFragments();

        beginTransactions();
    }


    private void initFragments() {
        mapFragment = new MapFragment();
        listFragment = new ListFragment();
        listFragment.setCallBack(callBack_SendClick);
    }
    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();
    }
}
