package com.lukaszneumann.jackadventure;


import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

/**
 * Created by Lukasz on 2015-01-10.
 */
public class Witch extends Sprite {

    private MyGame myGame;
    private Body bodyWitch;
    private Body boundsScreen;
    private ArrayList<Body> starsBody = new ArrayList<Body>(10);
    private ArrayList<Light> starsLight = new ArrayList<Light>(10);
    private final int raysLight = 128;
    private float stateTimeStar = 0;
    private boolean rightFace = true;
    private boolean isVisible = false;


    public Witch(MyGame myGame) {

        this.myGame = myGame;


        this.set(new Sprite(myGame.getContent().getAssetManager().get("Enemy/witch.png", Texture.class)));
        this.setOriginCenter();


    }


    public void update(float deltaTime) {

        if (isVisible == true) {

            if (bodyWitch == null) {
                createPhysicsWitch();
                createPhysicsBoundScreen();
//                laughSound(true);
            }

            stateTimeStar += deltaTime;

            this.setX((bodyWitch.getPosition().x * WorldGame.METERS_TO_PIXELS) - this.getWidth() / 2);
            this.setY((bodyWitch.getPosition().y * WorldGame.METERS_TO_PIXELS) - this.getHeight() / 2);

            if (bodyWitch.getLinearVelocity().x > 0) {
                rightFace = true;

            } else {
                rightFace = false;
            }

//            if (stateTimeStar >= 1f) {
//
//                stateTimeStar = 0;
//                createStar(MathUtils.random(3, 5));
//                createStarLight();
//            }

        } else {

            if (bodyWitch != null) {

//            destroy body witch of World
                myGame.worldGame.getWorld().destroyBody(bodyWitch);
//            destroy bounds screen of World
                myGame.worldGame.getWorld().destroyBody(boundsScreen);
//            destroy body witch
                bodyWitch = null;
            }
        }

        for (int i = 0; i < starsBody.size(); i++) {

            if (starsBody.get(i).getPosition().y < myGame.HEIGHT_SCREEN / 2) {
                destroyStar(i);
                destroyStarLight(i);
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


        bodyWitch = myGame.worldGame.getWorld().createBody(bodyDef);
        bodyWitch.setLinearVelocity(4, 0);
        bodyWitch.setGravityScale(0);
        bodyWitch.createFixture(fixtureDefWitch);

        shape.dispose();

    }


    private void createStar(int count) {

        CircleShape shape = new CircleShape();
        shape.setRadius(MathUtils.random(this.getWidth() / 20, this.getWidth() / 10));

        FixtureDef fixtureDefStar = new FixtureDef();
        fixtureDefStar.shape = shape;
        fixtureDefStar.density = 1f;
        fixtureDefStar.friction = 0.03f;
        fixtureDefStar.restitution = 0.5f;
        fixtureDefStar.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.StarWitch);
        fixtureDefStar.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.StarWitch);


        BodyDef bodyDefStar = new BodyDef();
        bodyDefStar.type = BodyDef.BodyType.DynamicBody;


        for (int i = 0; i < count; i++) {

            Body bodyStar;
            bodyDefStar.position.y = this.getY();

            if (rightFace) {

                bodyDefStar.position.x = this.getX();
                bodyStar = myGame.worldGame.getWorld().createBody(bodyDefStar);
                bodyStar.createFixture(fixtureDefStar);
                starsBody.add(bodyStar);
                starsBody.get(starsBody.size() - 1).applyForceToCenter(-MathUtils.random(5, 10) *
                        starsBody.get(starsBody.size() - 1).getMass() * -myGame.worldGame.getWorld().getGravity().y, 0, true);

            } else {

                bodyDefStar.position.x = this.getX() + this.getWidth();
                bodyStar = myGame.worldGame.getWorld().createBody(bodyDefStar);
                bodyStar.createFixture(fixtureDefStar);
                starsBody.add(bodyStar);
                starsBody.get(starsBody.size() - 1).applyForceToCenter(MathUtils.random(5, 10) *
                        starsBody.get(starsBody.size() - 1).getMass() * -myGame.worldGame.getWorld().getGravity().y, 0, true);
            }
        }

        shape.dispose();

    }

    private void destroyStar(int which) {

        myGame.worldGame.getWorld().destroyBody(starsBody.get(which));
        starsBody.remove(which);

    }


    private void createStarLight() {

        for (int i = 0; i < starsBody.size(); i++) {
            PointLight pointLight = new PointLight(myGame.rayHandler, raysLight, Color.WHITE,
                    MathUtils.random(this.getWidth() / 20, this.getWidth() / 10), 0, 0);
            pointLight.attachToBody(starsBody.get(i));

            starsLight.add(pointLight);

        }

    }

    private void destroyStarLight(int which) {

        starsLight.get(which).dispose();
        starsLight.get(which).remove();
        starsLight.remove(which);

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

        boundsScreen = myGame.worldGame.getWorld().createBody(bodyDef);
        boundsScreen.createFixture(fixtureDefBound);

        shapeBound.dispose();

    }

    private void laughSound(boolean isLaughing) {
        if (isLaughing == true) {
            myGame.soundGame.getLaughWitch().play();
        }
    }


}
