package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created by Lukasz on 2015-01-25.
 */
public class HealthPotion extends Sprite {

    private MyGame myGame;
    private WorldGame worldGame;
    private Body body;

    public HealthPotion(MyGame myGame, WorldGame worldGame) {
        this.myGame = myGame;
        this.worldGame = worldGame;

        set(new Sprite(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Health/Hearth.png", Texture.class)));
        setPosition(MathUtils.random(myGame.WIDTH_SCREEN - this.getWidth()), myGame.HEIGHT_SCREEN + this.getHeight());

        createPhysics();
    }


    public void update() {

        this.setX(body.getPosition().x * WorldGame.METERS_TO_PIXELS - this.getWidth() / 2);
        this.setY(body.getPosition().y * WorldGame.METERS_TO_PIXELS - this.getHeight() / 2);

    }


    private void createPhysics() {

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(this.getWidth() / 2 * WorldGame.PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = (this.getX() + this.getWidth() / 2) * WorldGame.PIXELS_TO_METERS;
        bodyDef.position.y = (this.getY() + this.getHeight() / 2) * WorldGame.PIXELS_TO_METERS;


        body = worldGame.getWorld().createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setGravityScale(0.6f);

        circleShape.dispose();


    }


    public void destroyBody() {
        worldGame.getWorld().destroyBody(body);

    }


}
