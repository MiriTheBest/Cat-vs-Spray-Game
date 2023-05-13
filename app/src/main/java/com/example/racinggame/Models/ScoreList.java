package com.example.racinggame.Models;

import java.util.ArrayList;

public class ScoreList {
    private String name = "";
    private ArrayList<Score> scores = new ArrayList<>();

    public ScoreList() {
    }

    public String getName() {
        return name;
    }

    public ScoreList setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public ScoreList setScores(ArrayList<Score> scores) {
        this.scores = scores;
        return this;
    }

    public int findMinScoreIndex() {
        int minValue = scores.get(0).getScore();
        int minScoreIndex = 0;
        for (int i = 1; i < this.scores.size(); i++) {
            if (scores.get(i).getScore() < minValue) {
                minValue = scores.get(i).getScore();
                minScoreIndex = i;
            }
        }
        return minScoreIndex;
    }

    @Override
    public String toString() {
        return "ScoreList{" +
                "name='" + name + '\'' +
                ", scores=" + scores +
                '}';
    }
}
