package com.lukaszneumann.jackadventure;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Lukasz on 2015-01-14.
 */
public class ExplosionSpike {

    private MyGame myGame;
    private float radiusDistance = 0;
    private PointLight explosionLights;
    private int rays = 128;
    private float positionX = 0;
    private float positionY = 0;
    private boolean isDestroyed = false;


    public ExplosionSpike(MyGame myGame, float radiusDistance, float positionX, float positionY) {

        this.myGame = myGame;

        this.radiusDistance = radiusDistance;
        this.positionX = positionX;
        this.positionY = positionY;


        createExplosion();
    }


    public void update(float deltaTime) {

        if (explosionLights.getDistance() < radiusDistance) {
            explosionLights.setDistance(explosionLights.getDistance() + (-myGame.worldGame.getWorld().getGravity().y * deltaTime * radiusDistance / 4));

        } else {
            isDestroyed = true;
            destroyExplosion();
        }
    }

    private void createExplosion() {

        explosionLights = new PointLight(myGame.rayHandler, rays, Color.LIGHT_GRAY, 0, positionX, positionY);
    }

    private void destroyExplosion() {
        explosionLights.dispose();
        explosionLights.remove();

    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
