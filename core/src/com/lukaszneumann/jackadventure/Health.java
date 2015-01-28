package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Lukasz on 2015-01-19.
 */
public class Health {

    private MyGame myGame;
    private Sprite hearth;
    private int valueOfHealth = 0;
    private int valueOfOffsetLifeBar = 0;
    private int maxValueOfHealth = 0;
    private ShapeRenderer lifeBar;
    private ShapeRenderer offsetLifeBar;
    private int vibrationTime = 800;


    public Health(MyGame myGame) {

        this.myGame = myGame;

        hearth = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Health/Hearth.png", Texture.class));
        hearth.setPosition(0.3f * hearth.getWidth(), myGame.HEIGHT_SCREEN - (4f * hearth.getHeight()));

        valueOfOffsetLifeBar = hearth.getRegionWidth();
        valueOfHealth = hearth.getRegionWidth();

        maxValueOfHealth = 2 * hearth.getRegionWidth();

        offsetLifeBar = new ShapeRenderer();
        offsetLifeBar.setColor(Color.LIGHT_GRAY);
        offsetLifeBar.setProjectionMatrix(myGame.camera.combined);

        lifeBar = new ShapeRenderer();
        lifeBar.setProjectionMatrix(myGame.camera.combined);
        lifeBar.setColor(1.f, 0.1f, 0.2f, 0.6f);
    }


    public void drawHearth() {

        myGame.batch.draw(hearth, hearth.getX(), hearth.getY(), hearth.getWidth(), hearth.getHeight());
    }

    public void drawLife() {

        offsetLifeBar.setProjectionMatrix(myGame.camera.combined);
        offsetLifeBar.begin(ShapeRenderer.ShapeType.Filled);
        offsetLifeBar.rect(hearth.getX() + 1.2f * hearth.getWidth(), hearth.getY() + hearth.getOriginX() - 20, valueOfOffsetLifeBar, hearth.getHeight() / 3.5f);
        offsetLifeBar.end();


        lifeBar.setProjectionMatrix(myGame.camera.combined);
        lifeBar.begin(ShapeRenderer.ShapeType.Filled);
        //lifeBar.setColor(Color.LIGHT_GRAY);
        // lifeBar.rect(hearth.getX() + 1.2f * hearth.getWidth(), hearth.getY() + hearth.getOriginX() - 20, 150, hearth.getHeight() / 3);
        // lifeBar.setColor(Color.RED);
        lifeBar.rect(hearth.getX() + 1.2f * hearth.getWidth(), hearth.getY() + hearth.getOriginX() - 20, valueOfHealth, hearth.getHeight() / 3.5f);
        lifeBar.end();
    }

    public boolean isEmptyHealth() {

        if (valueOfHealth <= 0) {
            return true;
        }
        return false;
    }

    public int getValueOfHealth() {
        return valueOfHealth;
    }

    public void setValueOfHealth(int valueOfHealth) {

        if (valueOfHealth < getValueOfHealth()) {
            Gdx.input.vibrate(vibrationTime);
            myGame.soundGame.getDamage();
        }

        if (valueOfHealth >= 0 && valueOfHealth <= maxValueOfHealth) {

            this.valueOfHealth = valueOfHealth;

        } else if (valueOfHealth < 0) {
            this.valueOfHealth = 0;

        } else if (valueOfHealth > maxValueOfHealth) {
            this.valueOfHealth = maxValueOfHealth;
        }

    }


    public int getMaxValueOfHealth() {
        return maxValueOfHealth;
    }
}
