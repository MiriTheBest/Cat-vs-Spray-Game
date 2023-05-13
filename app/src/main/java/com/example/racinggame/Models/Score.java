package com.example.racinggame.Models;

public class Score {
    private int score = 0;

    public double getmLatitude() {
        return mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    private double mLatitude = 48;
    private double mLongitude = 12;

    public Score() {}


    public int getScore() {
        return score;
    }

    public Score setScore(int score) {
        this.score = score;
        return this;
    }

    public Score setLatitude(double mLattitude) {
        this.mLatitude = mLattitude;
        return this;
    }

    public Score setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
        return this;
    }


    @Override
    public String toString() {
        return "Score{" + " score=" + score;
    }
}