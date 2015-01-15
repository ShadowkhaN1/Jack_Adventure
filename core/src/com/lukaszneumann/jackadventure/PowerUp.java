package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created by Lukasz on 2015-01-12.
 */
public class PowerUp extends Sprite {

    private MyGame myGame;
    private Body body;
    private TypeOfPowerUp typeOfPowerUp;

    public static enum TypeOfPowerUp {
        Copter, Shield, Magnet
    }


    public PowerUp(MyGame myGame, Texture texture) {

        this.myGame = myGame;

        this.set(new Sprite(texture));
        this.setOriginCenter();

        createPhysics();

    }

    public void update() {

        setX((body.getPosition().x * WorldGame.METERS_TO_PIXELS) - getOriginX());
        setY((body.getPosition().y * WorldGame.METERS_TO_PIXELS) - getOriginY());

    }

    public void destroyBody() {
        myGame.worldGame.getWorld().destroyBody(body);
    }

    public void setPower(TypeOfPowerUp typeOfPowerUp) {
        this.typeOfPowerUp = typeOfPowerUp;
    }

    public TypeOfPowerUp getTypeOfPowerUp() {
        return typeOfPowerUp;
    }

    private void createPhysics() {

        CircleShape shape = new CircleShape();
        shape.setRadius(getWidth() / 2 * WorldGame.PIXELS_TO_METERS);


        FixtureDef fixtureDefPowerUp = new FixtureDef();
        fixtureDefPowerUp.shape = shape;
        fixtureDefPowerUp.density = 0.0001f;
        fixtureDefPowerUp.isSensor = true;

        BodyDef bodyDefPowerUp = new BodyDef();
        bodyDefPowerUp.type = BodyDef.BodyType.DynamicBody;
        bodyDefPowerUp.position.x = MathUtils.random(0, (MyGame.WIDTH_SCREEN - this.getWidth()) * WorldGame.PIXELS_TO_METERS);
        bodyDefPowerUp.position.y = (myGame.HEIGHT_SCREEN + this.getHeight()) * WorldGame.PIXELS_TO_METERS;

        body = myGame.worldGame.getWorld().createBody(bodyDefPowerUp);
        body.createFixture(fixtureDefPowerUp);

    }


}
