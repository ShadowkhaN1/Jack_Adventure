package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Lukasz on 2014-11-11.
 */
public class Background extends Sprite {


    private MyGame myGame;
    private WorldGame worldGame;
    private Body body;


    public Background(MyGame myGame, WorldGame worldGame, Sprite sprite) {

        this.myGame = myGame;
        this.worldGame = worldGame;

        set(sprite);
        setSize(myGame.WIDTH_SCREEN, this.getHeight());
        setOriginCenter();
        setPosition(0, 0);

        createPhysics();

    }


    public void update(float deltaTime, boolean isDead) {


        setX((body.getPosition().x * WorldGame.METERS_TO_PIXELS) - this.getWidth() / 2);
        setY((body.getPosition().y * WorldGame.METERS_TO_PIXELS) - this.getHeight() / 2);


        if (isDead == true) {
            body.setType(BodyDef.BodyType.DynamicBody);

        } else if (getY() < 5 * -myGame.HEIGHT_SCREEN) {
            body.setType(BodyDef.BodyType.StaticBody);
        }


        if (this.getY() > 0) {
            setY(0);
        }
    }


    private void createPhysics() {

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.getWidth() * WorldGame.PIXELS_TO_METERS, this.getHeight() * WorldGame.PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = (this.getX() + this.getWidth() / 2) * WorldGame.PIXELS_TO_METERS;
        bodyDef.position.y = (this.getY() + this.getHeight() / 2) * WorldGame.PIXELS_TO_METERS;

        body = worldGame.getWorld().createBody(bodyDef);
        body.createFixture(fixtureDef);

        shape.dispose();

    }

    private void destroyBody() {
        worldGame.getWorld().destroyBody(body);
    }


}
