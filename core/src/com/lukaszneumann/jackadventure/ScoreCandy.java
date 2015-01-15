package com.lukaszneumann.jackadventure;

/**
 * Created by Lukasz on 2014-11-28.
 */
public class ScoreCandy {

    private int score = 0;

    public ScoreCandy() {
    }

    public void setScore(int count) {
        score = count;
    }

    public void addScore(int count) {
        score += count;
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return Integer.toString(score);
    }
}
