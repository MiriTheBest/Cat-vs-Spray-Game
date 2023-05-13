package com.example.racinggame.Logic;


import android.util.Log;
import android.widget.Toast;

import com.example.racinggame.Models.Score;
import com.example.racinggame.Models.ScoreList;
import com.example.racinggame.Utilities.MySP;
import com.example.racinggame.Utilities.SignalGenerator;
import com.google.gson.Gson;

public class GameManager {

    public static final String SCORE_KEY = "scores";
    private int life;
    private int score;

    private ScoreList scoreList;

    public GameManager(int life) {
        this.life = life;
        this.score = 0;

        String fromSP =  MySP.getInstance().getString(GameManager.SCORE_KEY,"");

        ScoreList scoreListFromJson;
        if(fromSP.isEmpty() == false)
            scoreListFromJson = new Gson().fromJson(fromSP,ScoreList.class );
        else
            scoreListFromJson = new ScoreList();
        this.scoreList = scoreListFromJson;
    }

    public int getLife() {
        return this.life;
    }

    public int getScore() {
        return this.score;
    }

    public int checkCrash (int sprayCol, int playerCol) {

        if(sprayCol == playerCol) {
            SignalGenerator.getInstance().toast("🤬You sprayed the cat!",Toast.LENGTH_LONG);
            SignalGenerator.getInstance().vibrate();
            SignalGenerator.getInstance().playAngrySound();
            if(this.life > 0){
                this.life--;
            }
            return 1;
        }
        else {
            return 0;
        }
    }

    public void checkIfAddScore(int rewardCol, int playerCol) {

        if (rewardCol == playerCol) {
            this.score += 100;
            SignalGenerator.getInstance().playPurrSound();
        }
    }

    public void addRegularScore() {
        this.score +=10;
    }

    public boolean isGameEnded() {
        return this.life == 0;
    }

    public void gameOver (double mLatitude, double mLongitude) {
        scoreList.setName("Top 10 Scores");

        scoreList.getScores().add(new Score().setScore(this.score).setLatitude(mLatitude).setLongitude(mLongitude));

        if (scoreList.getScores().size() > 10) {
            int toDelete = scoreList.findMinScoreIndex();
            scoreList.getScores().remove(toDelete);
        }

        String scoreListJson = new Gson().toJson(scoreList);
        Log.d("JSON", scoreListJson);
        MySP.getInstance().putString(SCORE_KEY, scoreListJson);
    }

}
