package com.lukaszneumann.jackadventure;

/**
 * Created by Lukasz on 2014-11-28.
 */
public class ScoreCandy {

    private MyGame myGame;
    private int score = 0;
    private int goodScore = 30;

    public ScoreCandy(MyGame myGame) {
        this.myGame = myGame;
    }

    public void setScore(int count) {
        score = count;
    }

    public void addScore(int count) {
        score += count;

        if (score >= goodScore) {
            goodScore = score + 30;
            myGame.soundGame.getPlayerYeah();
        }
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return Integer.toString(score);
    }
}
