package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created by Lukasz on 2015-01-14.
 */
public class EnemySpike extends Sprite {

    private MyGame myGame;
    private Body bodySpike;


    public EnemySpike(MyGame myGame) {

        this.myGame = myGame;


        set(new Sprite(myGame.content.getAssetManager().get("Enemy/Spike.png", Texture.class)));
        //setScale(MathUtils.random(0.5f, 1f));
        setOriginCenter();

    }


    public void update() {

        setX((bodySpike.getPosition().x * WorldGame.METERS_TO_PIXELS) - this.getOriginX());
        setY((bodySpike.getPosition().y * WorldGame.METERS_TO_PIXELS) - this.getOriginY());

    }


    public void createPhysics() {

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(this.getWidth() / 2 * WorldGame.PIXELS_TO_METERS);

        FixtureDef fixtureDefSpike = new FixtureDef();
        fixtureDefSpike.shape = circleShape;
        fixtureDefSpike.density = 1f;
        fixtureDefSpike.friction = 0.7f;
        fixtureDefSpike.restitution = 0.9f;
        fixtureDefSpike.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);
        fixtureDefSpike.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);


        BodyDef bodyDefSpike = new BodyDef();
        bodyDefSpike.type = BodyDef.BodyType.DynamicBody;
        bodyDefSpike.position.x = ((this.getX() + this.getOriginX()) * WorldGame.PIXELS_TO_METERS);
        bodyDefSpike.position.y = ((this.getY() + this.getOriginY()) * WorldGame.PIXELS_TO_METERS);


        bodySpike = myGame.worldGame.getWorld().createBody(bodyDefSpike);
        bodySpike.createFixture(fixtureDefSpike);

        circleShape.dispose();
    }

    public void destroyBody() {
        myGame.worldGame.getWorld().destroyBody(bodySpike);
    }


}
