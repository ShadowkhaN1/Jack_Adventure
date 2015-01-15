package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Lukasz on 2015-01-15.
 */
public class Tombstone extends Sprite {

    private MyGame myGame;
    private boolean isVisible = false;
    private Body body;


    public Tombstone(MyGame myGame) {
        this.myGame = myGame;

        set(new Sprite(myGame.content.getAssetManager().get("Health/Tombstone.png", Texture.class)));
        setOriginCenter();
    }


    private void createPhysics() {

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.getWidth() / 2 * WorldGame.PIXELS_TO_METERS, this.getHeight() / 2 * WorldGame.PIXELS_TO_METERS);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.4f;
        fixtureDef.friction = 0.4f;
        fixtureDef.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Tombstone);
        fixtureDef.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Tombstone);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x =



    }

}
