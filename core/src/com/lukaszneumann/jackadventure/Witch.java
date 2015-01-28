package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

/**
 * Created by Lukasz on 2015-01-10.
 */
public class Witch extends Sprite {

    private MyGame myGame;
    private WorldGame worldGame;
    private Body bodyWitch;
    private Body boundsScreen;
    private ArrayList<Body> starsBody = new ArrayList<Body>(10);
    private boolean rightFace = true;
    private boolean isVisible = false;


    public Witch(MyGame myGame, WorldGame worldGame) {

        this.myGame = myGame;
        this.worldGame = worldGame;


        this.set(new Sprite(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Enemy/witch.png", Texture.class)));
        this.setOriginCenter();


    }


    public void update(float deltaTime) {

        if (isVisible == true) {

            if (bodyWitch == null) {
                createPhysicsWitch();
                createPhysicsBoundScreen();
                laughSound(true);
            }


            this.setX((bodyWitch.getPosition().x * WorldGame.METERS_TO_PIXELS) - this.getWidth() / 2);
            this.setY((bodyWitch.getPosition().y * WorldGame.METERS_TO_PIXELS) - this.getHeight() / 2);

            if (bodyWitch.getLinearVelocity().x > 0) {
                rightFace = true;

            } else {
                rightFace = false;
            }

        } else {

            if (bodyWitch != null) {

//            destroy body witch of World
                worldGame.getWorld().destroyBody(bodyWitch);
//            destroy bounds screen of World
                worldGame.getWorld().destroyBody(boundsScreen);
//            destroy body witch
                bodyWitch = null;
            }
        }
    }

    public void draw() {

        //        draw witch when it appears
        if (isVisible() == true) {
            if (isRightFace()) {
                myGame.batch.draw(this, this.getX(), this.getY(), this.getWidth(), this.getHeight());
            } else {
                myGame.batch.draw(this, this.getX() + this.getWidth(), this.getY(), -this.getWidth(), this.getHeight());
            }
        }
    }

    public boolean isRightFace() {
        return rightFace;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    private void createPhysicsWitch() {

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.getWidth() / 2 * WorldGame.PIXELS_TO_METERS, this.getHeight() / 2 * WorldGame.PIXELS_TO_METERS);


        FixtureDef fixtureDefWitch = new FixtureDef();
        fixtureDefWitch.shape = shape;
        fixtureDefWitch.restitution = 1f;
        fixtureDefWitch.density = 1f;
        fixtureDefWitch.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Witch);
        fixtureDefWitch.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Witch);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((this.getX() + this.getOriginX()) * WorldGame.PIXELS_TO_METERS,
                (this.getY() + this.getOriginY()) * WorldGame.PIXELS_TO_METERS);


        bodyWitch = worldGame.getWorld().createBody(bodyDef);
        bodyWitch.setLinearVelocity(4, 0);
        bodyWitch.setGravityScale(0);
        bodyWitch.createFixture(fixtureDefWitch);

        shape.dispose();

    }


    private void destroyStar(int which) {

        worldGame.getWorld().destroyBody(starsBody.get(which));
        starsBody.remove(which);

    }


    private void createPhysicsBoundScreen() {

        ChainShape shapeBound = new ChainShape();
        shapeBound.createLoop(new Vector2[]{
                new Vector2(myGame.WIDTH_SCREEN * WorldGame.PIXELS_TO_METERS, 0),
                new Vector2(myGame.WIDTH_SCREEN * WorldGame.PIXELS_TO_METERS, myGame.HEIGHT_SCREEN * WorldGame.PIXELS_TO_METERS),
                new Vector2(0, myGame.HEIGHT_SCREEN * WorldGame.PIXELS_TO_METERS),
                new Vector2(0, 0),
        });

        FixtureDef fixtureDefBound = new FixtureDef();
        fixtureDefBound.shape = shapeBound;
        fixtureDefBound.restitution = 1f;
        fixtureDefBound.density = 1f;
        fixtureDefBound.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Witch);
        fixtureDefBound.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Witch);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        boundsScreen = worldGame.getWorld().createBody(bodyDef);
        boundsScreen.createFixture(fixtureDefBound);

        shapeBound.dispose();

    }

    private void laughSound(boolean isLaughing) {
        if (isLaughing == true) {
            myGame.soundGame.getLaughWitch();
        }
    }


}
