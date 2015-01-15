package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Lukasz on 2014-11-26.
 */
public class Enemy {


    private Animation bat;
    private ArrayList<Vector2> position;
    private ArrayList<Animation> animationArray;
    private TextureRegion[] regionsBat;
    private Random random;
    private float time = 0;
    private float respawnTime = 10;


    public Enemy() {

        regionsBat = new TextureRegion[4];
        regionsBat[0] = new TextureRegion(new Texture(Gdx.files.internal("Enemy/Bat (1).png")));
        regionsBat[1] = new TextureRegion(new Texture(Gdx.files.internal("Enemy/Bat (2).png")));
        regionsBat[2] = new TextureRegion(new Texture(Gdx.files.internal("Enemy/Bat (3).png")));
        regionsBat[3] = new TextureRegion(new Texture(Gdx.files.internal("Enemy/Bat (4).png")));


        bat = new Animation(0.15f, regionsBat[0], regionsBat[1], regionsBat[2], regionsBat[3]);
        bat.setPlayMode(Animation.PlayMode.LOOP);

        animationArray = new ArrayList<Animation>(10);
        position = new ArrayList<Vector2>(10);
        random = new Random();


    }


    public void update(float deltaTime, Batch batch, float y) {

        time += deltaTime;

        if ((int) time % respawnTime == 0) {
            time += 1;
            create(y);
        }

        draw(batch, time);
    }

    public float getWidth() {
        return regionsBat[0].getRegionWidth();
    }

    public float getHeight() {
        return regionsBat[0].getRegionHeight();
    }


    private void create(float y) {

        position.add(new Vector2());
        position.get(position.size() - 1).x = getRandomPositionX(Gdx.graphics.getWidth() - regionsBat[0].getRegionWidth());
        position.get(position.size() - 1).y = (y + 2 * Gdx.graphics.getHeight());
        animationArray.add(bat);

    }

    private float getRandomPositionX(int x) {
        return random.nextInt(x);
    }

    private float getRandomPositionY(int y) {
        return random.nextInt(y);
    }

    public ArrayList<Vector2> getPosition() {
        return position;
    }

    public void remove(int i) {
        position.remove(i);
        animationArray.remove(i);
    }

    private void draw(Batch batch, float time) {
        for (int i = 0; i < animationArray.size(); i++) {
            batch.draw(animationArray.get(i).getKeyFrame(time), position.get(i).x, position.get(i).y);
        }
    }

}
