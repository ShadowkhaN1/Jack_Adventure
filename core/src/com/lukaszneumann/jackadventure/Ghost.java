package com.lukaszneumann.jackadventure;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
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
public class Ghost extends Sprite {


    private MyGame myGame;
    private WorldGame worldGame;
    private RayHandler rayHandler;
    private Body ghostBody;
    private boolean destroy = false;
    private float stateTime = 0;
    private PointLight pointLight;
    private int rayPoint = 128;
    private float distanceRadiusPoint = 0;


    public Ghost(MyGame myGame, WorldGame worldGame, RayHandler rayHandler) {

        this.myGame = myGame;
        this.worldGame = worldGame;
        this.rayHandler = rayHandler;

        float scale = MathUtils.random(0.7f, 1f);

        this.set(new Sprite(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Enemy/Ghost.png", Texture.class)));
        setSize(this.getWidth() * scale, this.getHeight() * scale);
        this.setOriginCenter();

        distanceRadiusPoint = this.getWidth();
        pointLight = new PointLight(rayHandler, rayPoint, Color.WHITE, distanceRadiusPoint, 0, 0);
    }


    public void update(float deltaTime) {

        stateTime += deltaTime;

        setX((ghostBody.getPosition().x * WorldGame.METERS_TO_PIXELS) - this.getOriginX());
        setY((ghostBody.getPosition().y * WorldGame.METERS_TO_PIXELS) - this.getOriginY());


        pointLight.setPosition(ghostBody.getPosition().x * WorldGame.METERS_TO_PIXELS, ghostBody.getPosition().y * WorldGame.METERS_TO_PIXELS);
        pointLight.setDistance(getDistanceRadiusPoint());


    }


    public float getDistanceRadiusPoint() {
        return MathUtils.clamp(distanceRadiusPoint * Math.abs(MathUtils.sin(4 * stateTime)), this.getWidth() / 2, 2 * this.getWidth());
    }

    public void createPhysics(float x, float y) {


        CircleShape shape = new CircleShape();
        shape.setRadius(this.getWidth() / 2 * WorldGame.PIXELS_TO_METERS);


        FixtureDef fixtureDefGhost = new FixtureDef();
        fixtureDefGhost.shape = shape;
        fixtureDefGhost.restitution = 0f;
        fixtureDefGhost.density = 1f;
        fixtureDefGhost.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);
        fixtureDefGhost.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = x * WorldGame.PIXELS_TO_METERS;
        bodyDef.position.y = y * WorldGame.PIXELS_TO_METERS;


        ghostBody = worldGame.getWorld().createBody(bodyDef);
        ghostBody.createFixture(fixtureDefGhost).setUserData("Ghost");


        shape.dispose();

    }


    private void destroyBody() {
        worldGame.getWorld().destroyBody(ghostBody);
    }

    private void destroyPointLight() {
        pointLight.dispose();
        pointLight.remove();
    }

    public void destroy() {

        destroyBody();
        destroyPointLight();
    }

    public boolean isDestroy() {
        return destroy;
    }


}
