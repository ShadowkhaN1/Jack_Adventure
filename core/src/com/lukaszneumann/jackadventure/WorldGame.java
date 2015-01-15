package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Lukasz on 2014-12-21.
 * This class supports physics in game.
 */
public class WorldGame implements ContactListener {


    public static final float METERS_TO_PIXELS = 100;
    public static final float PIXELS_TO_METERS = 0.01f;
//    public static final float METERS_TO_PIXELS = 1;
//    public static final float PIXELS_TO_METERS = 1;

    private float gravity = -9.8f;

    private World world;


    public WorldGame(Vector2 gravity, boolean doSleep) {

        world = new World(gravity, doSleep);

    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
      //  this.getWorld().setGravity(new Vector2(0, gravity));
    }

    public World getWorld() {
        return world;
    }


    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


}
