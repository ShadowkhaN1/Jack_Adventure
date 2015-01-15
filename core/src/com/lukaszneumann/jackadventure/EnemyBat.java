package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Lukasz on 2015-01-14.
 */
public class EnemyBat extends Sprite {

    private MyGame myGame;
    private Animation animation;
    private Body bodyBat;
    private float frameDuration = 0.15f;
    private float stateTime = 0;
    private TextureRegion[] textureRegions;

    public EnemyBat(MyGame myGame) {
        this.myGame = myGame;

        textureRegions = new TextureRegion[4];
        textureRegions[0] = new TextureRegion(myGame.content.getAssetManager().get("Enemy/Bat (1).png", Texture.class));
        textureRegions[1] = new TextureRegion(myGame.content.getAssetManager().get("Enemy/Bat (2).png", Texture.class));
        textureRegions[2] = new TextureRegion(myGame.content.getAssetManager().get("Enemy/Bat (3).png", Texture.class));
        textureRegions[3] = new TextureRegion(myGame.content.getAssetManager().get("Enemy/Bat (4).png", Texture.class));

        animation = new Animation(frameDuration, textureRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        setSize(textureRegions[0].getRegionWidth(), textureRegions[0].getRegionHeight());
        setOriginCenter();
        setPosition(MathUtils.random(0, myGame.WIDTH_SCREEN - this.getWidth()), myGame.HEIGHT_SCREEN + this.getHeight());

        createPhysics();

    }

    public void update(float deltaTime) {

        stateTime += deltaTime;

        setX((bodyBat.getPosition().x * WorldGame.METERS_TO_PIXELS) - this.getOriginX());
        setY((bodyBat.getPosition().y * WorldGame.METERS_TO_PIXELS) - this.getOriginY());
    }

    public TextureRegion render() {
        return animation.getKeyFrame(stateTime);
    }

    private void createPhysics() {

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.getWidth() / 2 * WorldGame.PIXELS_TO_METERS, this.getHeight() / 2 * WorldGame.PIXELS_TO_METERS);

        FixtureDef fixtureDefBat = new FixtureDef();
        fixtureDefBat.shape = polygonShape;
        fixtureDefBat.density = 1f;
        fixtureDefBat.restitution = 0.8f;
        fixtureDefBat.friction = 0.3f;
        fixtureDefBat.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);
        fixtureDefBat.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);

        BodyDef bodyDefBat = new BodyDef();
        bodyDefBat.type = BodyDef.BodyType.DynamicBody;
        bodyDefBat.position.x = (this.getX() - this.getOriginX()) * WorldGame.PIXELS_TO_METERS;
        bodyDefBat.position.y = (this.getY() - this.getOriginY()) * WorldGame.PIXELS_TO_METERS;


        bodyBat = myGame.worldGame.getWorld().createBody(bodyDefBat);
        bodyBat.createFixture(fixtureDefBat);

        polygonShape.dispose();

    }

    public void destroyBody() {
        myGame.worldGame.getWorld().destroyBody(bodyBat);
    }

}
