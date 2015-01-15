package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Lukasz on 2014-12-07.
 */
public class Hammer extends GameObject {

    private float stateTime = 0;
    private float randomAngle = 0;

    public Hammer(Texture texture) {
        super(texture);

        randomAngle = MathUtils.random(-1, 1);

        if (randomAngle == 0) {
            randomAngle = MathUtils.random(-1, 1);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        stateTime += deltaTime;
    }

    public float getRotate() {
        return (MathUtils.sin(randomAngle * stateTime)) * MathUtils.radiansToDegrees / 1.2f;
    }
}
