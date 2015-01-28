package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Lukasz on 2015-01-15.
 */
public class Tombstone extends Sprite {

    private MyGame myGame;
    private WorldGame worldGame;
    private boolean isVisible = false;
    private boolean isStationary = false;
    private Body body;
    private Body bodyGround;


    public Tombstone(MyGame myGame, WorldGame worldGame) {
        this.myGame = myGame;
        this.worldGame = worldGame;

        this.set(new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Health/Tombstone.png", Texture.class)));
        this.setPosition(myGame.WIDTH_SCREEN / 2 - this.getWidth() / 2, myGame.HEIGHT_SCREEN);
        this.setOriginCenter();

        createGround();

    }


    public void update() {

        if (isVisible == true) {

            setX(body.getPosition().x * WorldGame.METERS_TO_PIXELS - this.getWidth() / 2);
            setY(body.getPosition().y * WorldGame.METERS_TO_PIXELS - this.getHeight() / 2);


            for (Contact contact : worldGame.getWorld().getContactList()) {

                if (contact.getFixtureA().getBody() == body && contact.getFixtureB().getBody() == bodyGround ||
                        contact.getFixtureA().getBody() == bodyGround && contact.getFixtureB().getBody() == body) {
                    isStationary = true;
                    body.setType(BodyDef.BodyType.StaticBody);

                }
            }

        }


    }


    public boolean isStationary() {
        return isStationary;
    }

    public void createPhysics(float x, float y) {

        if (body == null) {

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(this.getWidth() / 2 * WorldGame.PIXELS_TO_METERS, this.getHeight() / 2 * WorldGame.PIXELS_TO_METERS);


            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1f;
            fixtureDef.restitution = 0f;
            fixtureDef.friction = 0.01f;
            fixtureDef.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Tombstone);
            fixtureDef.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Tombstone);

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.x = x * WorldGame.PIXELS_TO_METERS;
            bodyDef.position.y = y * WorldGame.PIXELS_TO_METERS;

            body = worldGame.getWorld().createBody(bodyDef);
            body.createFixture(fixtureDef).setUserData("Tombstone");

            body.setGravityScale(-100);

            shape.dispose();

        }
    }

    private void createGround() {

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4 * this.getWidth() * WorldGame.PIXELS_TO_METERS, this.getHeight() / 35 * WorldGame.PIXELS_TO_METERS);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.5f;
        fixtureDef.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Tombstone);
        fixtureDef.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Tombstone);

        BodyDef bodyDefGround = new BodyDef();
        bodyDefGround.type = BodyDef.BodyType.StaticBody;
        bodyDefGround.position.x = (myGame.WIDTH_SCREEN / 2 * WorldGame.PIXELS_TO_METERS);
        bodyDefGround.position.y = 0;

        bodyGround = worldGame.getWorld().createBody(bodyDefGround);
        bodyGround.createFixture(fixtureDef).setUserData("groundBody");

        shape.dispose();

    }


    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
