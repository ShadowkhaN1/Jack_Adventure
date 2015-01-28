package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;

/**
 * Created by Lukasz on 2014-11-27.
 */
public class Cloud {

    private MyGame myGame;
    private WorldGame worldGame;
    private ArrayList<Body> cloudsBody = new ArrayList<Body>(10);
    private ArrayList<Sprite> cloudsSprite = new ArrayList<Sprite>(10);
    private final int amountOfClouds = 4;

    private float stateTime = 0;
    private float respawnCloud = 1;


    public Cloud(MyGame myGame, WorldGame worldGame) {
        this.myGame = myGame;
        this.worldGame = worldGame;
    }


    public void update(float deltaTime, float startTime) {
        stateTime += deltaTime;


        for (int i = 0; i < cloudsSprite.size(); i++) {

            cloudsSprite.get(i).setX((cloudsBody.get(i).getPosition().x * WorldGame.METERS_TO_PIXELS) - cloudsSprite.get(i).getOriginX());
            cloudsSprite.get(i).setY((cloudsBody.get(i).getPosition().y * WorldGame.METERS_TO_PIXELS) - cloudsSprite.get(i).getOriginY());
        }


        if (stateTime >= respawnCloud) {
            if (worldGame.getGravity() < 0) {
                respawnCloud = 0.5f * (Math.abs(startTime) / Math.abs(worldGame.getWorld().getGravity().y));

            } else {
                respawnCloud = (Math.abs(startTime) / Math.abs(worldGame.getGravity()));
            }

            stateTime = 0;

            createCloud();
            createPhysics();
        }

        for (int i = 0; i < cloudsSprite.size(); i++) {

            if (worldGame.getGravity() < 0) {
                if (cloudsSprite.get(i).getY() + cloudsSprite.get(i).getHeight() < 0) {
                    destroyCloudSprite(i);
                    destroyCloudBody(i);
                }
            } else {
                if (cloudsSprite.get(i).getY() > myGame.HEIGHT_SCREEN) {
                    destroyCloudSprite(i);
                    destroyCloudBody(i);
                }
            }
        }

    }

    public void draw() {

        for (Sprite cloud : cloudsSprite) {
            myGame.batch.draw(cloud, cloud.getX(), cloud.getY(), cloud.getWidth(), cloud.getHeight());
        }
    }

    private void createCloud() {

        Sprite cloud = new Sprite(getRandomTexture(MathUtils.random(amountOfClouds)));
        cloud.setOriginCenter();

        if (worldGame.getGravity() < 0) {
            cloud.setPosition(MathUtils.random(0, myGame.WIDTH_SCREEN - cloud.getWidth()), myGame.HEIGHT_SCREEN + cloud.getHeight());
        } else {
            cloud.setPosition(MathUtils.random(0, myGame.WIDTH_SCREEN), 0 - cloud.getHeight());
        }

        cloudsSprite.add(cloud);

        // cloud.getTexture().dispose();
    }

    private void destroyCloudSprite(int which) {
        cloudsSprite.remove(which);
    }


    private void createPhysics() {

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((cloudsSprite.get(cloudsSprite.size() - 1).getWidth() / 2) * WorldGame.PIXELS_TO_METERS,
                (cloudsSprite.get(cloudsSprite.size() - 1).getHeight() / 2) * WorldGame.PIXELS_TO_METERS);


        FixtureDef fixtureDefCloud = new FixtureDef();
        fixtureDefCloud.shape = shape;
        fixtureDefCloud.density = 0.5f;
        fixtureDefCloud.friction = 0.4f;
        fixtureDefCloud.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Cloud);
        fixtureDefCloud.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Cloud);


        BodyDef bodyDefCloud = new BodyDef();
        bodyDefCloud.type = BodyDef.BodyType.DynamicBody;
        bodyDefCloud.position.x = (cloudsSprite.get(cloudsSprite.size() - 1).getX() + cloudsSprite.get(cloudsSprite.size() - 1).getOriginX()) * WorldGame.PIXELS_TO_METERS;
        bodyDefCloud.position.y = (cloudsSprite.get(cloudsSprite.size() - 1).getY() + cloudsSprite.get(cloudsSprite.size() - 1).getOriginY()) * WorldGame.PIXELS_TO_METERS;

        Body bodyCloud = worldGame.getWorld().createBody(bodyDefCloud);
        bodyCloud.createFixture(fixtureDefCloud);
        bodyCloud.setGravityScale(0.8f);

        cloudsBody.add(bodyCloud);

        shape.dispose();

    }

    private void destroyCloudBody(int which) {
        worldGame.getWorld().destroyBody(cloudsBody.get(which));
        cloudsBody.remove(which);
    }


    private Texture getRandomTexture(int which) {

        switch (which) {
            case 0:
                return myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/Cloud (1).png", Texture.class);
            case 1:
                return myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/Cloud (2).png", Texture.class);
            case 2:
                return myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/Cloud (3).png", Texture.class);
            case 3:
                return myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/Cloud (4).png", Texture.class);
            default:
                return myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/Cloud (1).png", Texture.class);

        }
    }


}
