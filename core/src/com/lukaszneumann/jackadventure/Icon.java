package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Lukasz on 2014-12-01.
 */
public class Icon extends Sprite {

    private float alpha = 0.5f;

    private Integer countPowerUp = 0;
    private int maxCountPowerUp = 10;


    public Icon(Texture texture) {
        super(texture);
    }


    public void addCountPowerUp() {
        if (countPowerUp < maxCountPowerUp) {
            countPowerUp += 1;
        }
    }

    public void removeCountPowerUp() {
        if (countPowerUp > 0) {
            countPowerUp -= 1;
        }
    }

    public void setAlpha() {
        if (countPowerUp > 0) {
            alpha = 1;
            setAlpha(alpha);
        } else {
            alpha = 0.5f;
            setAlpha(alpha);
        }
    }

    public float getAlpha() {
        return alpha;
    }

    public Integer getCountPowerUp() {
        return countPowerUp;
    }


}
