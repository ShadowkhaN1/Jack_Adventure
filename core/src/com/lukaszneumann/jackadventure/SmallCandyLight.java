package com.lukaszneumann.jackadventure;

import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import java.util.ArrayList;

/**
 * Created by Lukasz on 2015-01-13.
 */
public class SmallCandyLight {

    private MyGame myGame;
    private int rayLight = 128;
    private float maxDistancePoint = 0;
    private ArrayList<Light> smallCandyLight = new ArrayList<Light>(10);
    private ArrayList<Body> smallCandyBody = new ArrayList<Body>(10);
    private float positionX = 0;
    private float positionY = 0;
    private int newCountBody = 0;


    public SmallCandyLight(MyGame myGame) {
        this.myGame = myGame;
    }

    public void setRadiusLight(float maxDistancePoint) {
        this.maxDistancePoint = maxDistancePoint;

    }

    public void update() {

        for (int i = 0; i < smallCandyLight.size(); i++) {
            smallCandyLight.get(i).setPosition(smallCandyBody.get(i).getPosition().x * WorldGame.METERS_TO_PIXELS,
                    smallCandyBody.get(i).getPosition().y * WorldGame.METERS_TO_PIXELS);
        }


        for (int i = 0; i < smallCandyLight.size(); i++) {
            if (smallCandyLight.get(i).getY() < 0) {

                destroyLight(i);
                smallCandyLight.remove(i);

                destroyBody(i);
                smallCandyBody.remove(i);

            }
        }
    }

    public void setPosition(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;

        createPhysics();
    }

    private void createPhysics() {

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(maxDistancePoint / 2 * WorldGame.PIXELS_TO_METERS);

        FixtureDef fixtureDefSmallCandy = new FixtureDef();
        fixtureDefSmallCandy.shape = circleShape;
        fixtureDefSmallCandy.friction = 0.5f;
        fixtureDefSmallCandy.density = 1f;
        fixtureDefSmallCandy.restitution = 0.7f;
        fixtureDefSmallCandy.isSensor = true;


        BodyDef bodyDefSmallCandy = new BodyDef();
        bodyDefSmallCandy.type = BodyDef.BodyType.DynamicBody;
        bodyDefSmallCandy.position.x = this.positionX * WorldGame.PIXELS_TO_METERS;
        bodyDefSmallCandy.position.y = this.positionY * WorldGame.PIXELS_TO_METERS;

        newCountBody = MathUtils.random(3, 6);

        for (int i = 0; i < newCountBody; i++) {

            Body body = myGame.worldGame.getWorld().createBody(bodyDefSmallCandy);
            body.createFixture(fixtureDefSmallCandy);
            body.applyForceToCenter(MathUtils.random(-maxDistancePoint / 6, maxDistancePoint / 6) * body.getMass() * -myGame.worldGame.getWorld().getGravity().y,
                    MathUtils.random(maxDistancePoint / 5, maxDistancePoint / 3) * body.getMass() * -myGame.worldGame.getWorld().getGravity().y, true);
            body.setGravityScale(MathUtils.random(0.4f, 0.9f));
            smallCandyBody.add(body);
        }

        circleShape.dispose();

        createSmallCandyLight();
    }

    private void destroyBody(int which) {
        myGame.worldGame.getWorld().destroyBody(smallCandyBody.get(which));
    }


    private void createSmallCandyLight() {

        for (int i = 0; i < newCountBody; i++) {

            PointLight pointLight = new PointLight(myGame.rayHandler, rayLight, null, 0, 0, 0);
            pointLight.setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1f);
            pointLight.setDistance(MathUtils.random(maxDistancePoint, 2 * maxDistancePoint));
            pointLight.setPosition(smallCandyBody.get(i).getPosition().x * WorldGame.METERS_TO_PIXELS,
                    smallCandyBody.get(i).getPosition().y * WorldGame.METERS_TO_PIXELS);
            pointLight.setSoftnessLength(100);
            pointLight.setSoft(true);

            smallCandyLight.add(pointLight);
        }

    }

    private void destroyLight(int which) {
        smallCandyLight.get(which).dispose();
        smallCandyLight.get(which).remove();
    }


    private void destroyAllBodyAndLight() {

        for (int i = 0; i < smallCandyLight.size(); i++) {
            myGame.worldGame.getWorld().destroyBody(smallCandyBody.get(i));
            smallCandyBody.remove(i);

            smallCandyLight.get(i).dispose();
            smallCandyLight.get(i).remove();
            smallCandyLight.remove(i);
        }
    }


}
