package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Lukasz on 2014-11-26.
 */
public class Boost {

    static int respawnTime = 10;
    static int maxCollectibles = 4;


    private ArrayList<Sprite> boostArray;
    private Sprite boost;
    private Sprite copter;
    private Sprite magnet;
    private Sprite rocket;
    private Sprite shield;
    private Random random;
    private float time = 0;




    public Boost() {

        copter = new Sprite(new Texture(Gdx.files.internal("PowerUp/Copter.png")));
        magnet = new Sprite(new Texture(Gdx.files.internal("PowerUp/Magnet.png")));
        rocket = new Sprite(new Texture(Gdx.files.internal("PowerUp/Rocket.png")));
        shield = new Sprite(new Texture(Gdx.files.internal("PowerUp/Shield.png")));

        random = new Random();
        boostArray = new ArrayList<Sprite>(10);

    }

    public void update(float deltaTime, Batch batch, float y) {

        time += deltaTime;


        if ((int) time % respawnTime == 0) {
            time += 1;
            create(y);
        }

        Iterator<Sprite> iterator = boostArray.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getY() < y - 2 * Gdx.graphics.getHeight()) {
                iterator.remove();
            }
        }

      //  draw(batch);
    }

    public void create(float y) {
        boost = new Sprite(getRandomBoost());
        boost.setPosition(getRandomPositionX((int) (Gdx.graphics.getWidth() - copter.getWidth())),
                y + 2 * Gdx.graphics.getHeight());
        boostArray.add(boost);
    }

    private Sprite getRandomBoost() {

        switch (random.nextInt(maxCollectibles + 1)) {
            case 0:
                return copter;
            case 1:
                return magnet;
            case 2:
                return rocket;
            case 3:
                return shield;
            default:
                return copter;
        }
    }

    public ArrayList<Sprite> getBoostArray() {
        return boostArray;
    }

    private float getRandomPositionX(int x) {
        return random.nextInt(x);
    }

    private float getRandomPositionY(int y) {
        return random.nextInt(y);
    }

    public void remove(int i) {
        boostArray.remove(i);
    }

    public void draw(Batch batch) {
        for (Sprite sprite : boostArray) {
            sprite.draw(batch);
        }
    }

}
