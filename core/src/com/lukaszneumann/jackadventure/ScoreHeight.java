package com.lukaszneumann.jackadventure;

/**
 * Created by Lukasz on 2015-01-13.
 */
public class ScoreHeight {

    private MyGame myGame;
    private WorldGame worldGame;
    private int score = 0;
    private float countResult = 0;


    public ScoreHeight(MyGame myGame, WorldGame worldGame) {
        this.myGame = myGame;
        this.worldGame = worldGame;
    }

    public void update(float deltaTime, float startTime) {

        countResult += (-worldGame.getGravity() * deltaTime);

        if (countResult >= (startTime / worldGame.getWorld().getGravity().y) / 2) {
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