package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.g2d.Sprite;
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
    private Body candyBody;


    private int point = 0;
    private float angleBetween = 0;


    public Candy(MyGame myGame) {
        this.myGame = myGame;
    }


    public void update() {

        setX((candyBody.getPosition().x * WorldGame.METERS_TO_PIXELS) - this.getWidth() / 2);
        setY((candyBody.getPosition().y * WorldGame.METERS_TO_PIXELS) - this.getHeight() / 2);

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


        candyBody = myGame.worldGame.getWorld().createBody(bodyDefCandy);
        candyBody.createFixture(fixtureDef);
        candyBody.setGravityScale(0.7f);

        circleShape.dispose();

    }

    public void destroyBody() {
        myGame.worldGame.getWorld().destroyBody(candyBody);
    }


    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }


}
