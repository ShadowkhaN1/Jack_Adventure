package com.lukaszneumann.jackadventure;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created by Lukasz on 2014-11-15.
 */
public class Candy extends Sprite {


    public static int RED_SCORE = 1;
    public static int YELLOW_SCORE = 3;
    public static int BLUE_SCORE = 5;

    private MyGame myGame;
    private WorldGame worldGame;
    private RayHandler rayHandler;
    private Body candyBody;
    private int point = 0;
    PointLight light;
    private Color colorCandy;

    private boolean isFollowTheObject = false;
    private float followPositionX = 0;
    private float followPositionY = 0;


    public Candy(MyGame myGame, WorldGame worldGame, RayHandler rayHandler) {
        this.myGame = myGame;
        this.worldGame = worldGame;
        this.rayHandler = rayHandler;
    }


    public void update() {

        if (isFollowTheObject == false) {
            candyBody.setLinearVelocity(0, worldGame.getWorld().getGravity().y);

        } else {
            followTheObject(followPositionX, followPositionY);
        }


        setX((candyBody.getPosition().x * WorldGame.METERS_TO_PIXELS) - this.getWidth() / 2);
        setY((candyBody.getPosition().y * WorldGame.METERS_TO_PIXELS) - this.getHeight() / 2);


        light.setPosition(this.getX() + this.getOriginX(), this.getY() + this.getOriginY());
    }

    private void followTheObject(float x, float y) {


        float a = candyBody.getPosition().x - x * WorldGame.PIXELS_TO_METERS;
        float b = candyBody.getPosition().y - y * WorldGame.PIXELS_TO_METERS;


        float angle = MathUtils.atan2(b, a);

        float velocityX = (float) ((double) worldGame.getWorld().getGravity().y / Math.tan(angle));

        candyBody.setLinearVelocity(1.5f * velocityX, 1.5f * worldGame.getWorld().getGravity().y);
    }


    public void createPhysics() {


        CircleShape circleShape = new CircleShape();
        circleShape.setRadius((this.getWidth() / 2) * WorldGame.PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;


        BodyDef bodyDefCandy = new BodyDef();
        bodyDefCandy.type = BodyDef.BodyType.DynamicBody;
        bodyDefCandy.position.x = (this.getX() + this.getWidth() / 2) * WorldGame.PIXELS_TO_METERS;
        bodyDefCandy.position.y = (this.getY() + this.getHeight() / 2) * WorldGame.PIXELS_TO_METERS;


        candyBody = worldGame.getWorld().createBody(bodyDefCandy);
        candyBody.createFixture(fixtureDef);
        candyBody.setGravityScale(0.7f);

        circleShape.dispose();


        light = new PointLight(rayHandler, 128, colorCandy, 2 * this.getWidth(), 0, 0);
    }

    public void setColorCandy(Color colorCandy) {
        this.colorCandy = colorCandy;
    }

    public void destroyBody() {
        light.remove();
        light.dispose();
        worldGame.getWorld().destroyBody(candyBody);
    }


    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }


    public void setFollowTheObject(boolean followTheObject, float followPositionX, float followPositionY) {
        this.isFollowTheObject = followTheObject;
        this.followPositionX = followPositionX;
        this.followPositionY = followPositionY;
    }

    public void setFollowObject(boolean followObject) {
        this.isFollowTheObject = followObject;
    }
}
