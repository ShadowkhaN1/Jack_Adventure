package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created by Lukasz on 2015-01-10.
 */
public class BulletWitch extends Sprite {

    private MyGame myGame;
    private WorldGame worldGame;
    private Witch witch;
    private Body projectileBody;
    private boolean destroy = false;


    public BulletWitch(MyGame myGame, WorldGame worldGame, Witch witch) {

        this.myGame = myGame;
        this.worldGame = worldGame;
        this.witch = witch;

        this.set(new Sprite(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Enemy/Pumpkin.png", Texture.class)));
        this.setOriginCenter();

    }


    public void update() {

        setX((projectileBody.getPosition().x * WorldGame.METERS_TO_PIXELS) - this.getOriginX());
        setY((projectileBody.getPosition().y * WorldGame.METERS_TO_PIXELS) - this.getOriginY());

    }

    public void draw() {
        myGame.batch.draw(this, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    public void createPhysics(float x, float y) {

        CircleShape shape = new CircleShape();
        shape.setRadius((this.getWidth() / 2) * WorldGame.PIXELS_TO_METERS);


        FixtureDef fixtureDefProjectile = new FixtureDef();
        fixtureDefProjectile.shape = shape;
        fixtureDefProjectile.restitution = 0f;
        fixtureDefProjectile.density = 1f;
        fixtureDefProjectile.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);
        fixtureDefProjectile.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = (witch.getX() + witch.getOriginX()) * WorldGame.PIXELS_TO_METERS;
        bodyDef.position.y = witch.getY() * WorldGame.PIXELS_TO_METERS;


        projectileBody = worldGame.getWorld().createBody(bodyDef);
        projectileBody.setLinearVelocity(4, -2);
        projectileBody.createFixture(fixtureDefProjectile);

        shape.dispose();

        setLinearVelocity(x, y);

    }

    private void setLinearVelocity(float x, float y) {

        float a = projectileBody.getPosition().x - x * WorldGame.PIXELS_TO_METERS;
        float b = projectileBody.getPosition().y - y * WorldGame.PIXELS_TO_METERS;


        float angle = MathUtils.atan2(b, a);

        float velocityX = (float) ((double) worldGame.getWorld().getGravity().y / Math.tan(angle));

        projectileBody.setLinearVelocity(1.2f * velocityX, 1.2f * worldGame.getWorld().getGravity().y);

    }


    public void destroyBody() {
        worldGame.getWorld().destroyBody(projectileBody);
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public boolean isDestroy() {
        return destroy;
    }
}
