package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Lukasz on 2015-01-25.
 */
public class Stage {

    private MyGame myGame;
    private WorldGame worldGame;
    private int stage = 0;
    private int valueOfNewStage = 0;
    private int addCandy = 0;
    private float timeDrawStage = 10;
    private float stateTime = 0;
    private boolean drawStage = false;
    private BitmapFont fontStage;
    private BitmapFont fontCandyBonus;
    private Sprite candy;


    public Stage(MyGame myGame, WorldGame worldGame) {

        this.myGame = myGame;
        this.worldGame = worldGame;
        valueOfNewStage = 500;
        fontStage = myGame.initializeGame.getFont();
        fontCandyBonus = myGame.initializeGame.getFont();
        candy = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Candy/Candy (2).png", Texture.class));
    }


    public void update(float deltaTime, int height, ScoreCandy scoreCandy) {

        if (drawStage == true) {
            stateTime += deltaTime;
        }


        if (!drawStage && height != 0) {
            if (MathUtils.isZero(height % valueOfNewStage, 0.2f)) {

                drawStage = true;
                valueOfNewStage = +500;
                ++stage;

                myGame.soundGame.getNewStage();
                setGravityWorld();
                addCandy(scoreCandy);
            }
        }
    }

    private void setGravityWorld() {

        worldGame.setGravity(worldGame.getGravity() - 1.5f);
    }

    private void addCandy(ScoreCandy scoreCandy) {

        addCandy += 50;
        scoreCandy.setScore(scoreCandy.getScore() + addCandy);

    }

    public void drawStage() {

        if (drawStage && stateTime < timeDrawStage) {

            fontStage.draw(myGame.batch, "Stage " + stage + "!", myGame.WIDTH_SCREEN - fontStage.getBounds("Stage " + stage + "!").width,
                    myGame.HEIGHT_SCREEN / 2 + 2 * fontStage.getBounds("Stage " + stage + "!").height);


            candy.setPosition(myGame.WIDTH_SCREEN - candy.getWidth(), myGame.HEIGHT_SCREEN / 2);
            candy.draw(myGame.batch);

            fontCandyBonus.draw(myGame.batch, "+" + addCandy, candy.getX() - 1.8f * fontCandyBonus.getBounds("+" + addCandy).width,
                    myGame.HEIGHT_SCREEN / 2 + fontCandyBonus.getBounds("+" + addCandy).height);

        } else if (stateTime > timeDrawStage) {
            drawStage = false;
            stateTime = 0;
        }
    }

    public int getStage() {
        return stage;
    }
}
