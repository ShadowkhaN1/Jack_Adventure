package com.lukaszneumann.jackadventure;

/**
 * Created by Lukasz on 2015-01-13.
 */
public class ScoreHeight {

    private MyGame myGame;
    private int score = 0;
    private float countResult = 0;


    public ScoreHeight(MyGame myGame) {
        this.myGame = myGame;
    }

    public void update(float deltaTime) {

        countResult += (-myGame.worldGame.getWorld().getGravity().y * deltaTime);

        if (countResult >= 1) {
            countResult = 0;
            score++;
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String toString() {
        return Integer.toString(score);
    }
}