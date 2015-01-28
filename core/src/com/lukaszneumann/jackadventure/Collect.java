package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukasz on 2014-12-09.
 */
public class Collect {


    private Animation animation;
    private float stateTime = 0;
    private float frameDuration = 0.15f;
    private TextureRegion[] regions;
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private float width = 0;
    private float height = 0;
    private float maxVelocity = 0;


    public Collect(MyGame myGame, float x, float y) {

        regions = new TextureRegion[8];
        regions[0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Collectibles/Collect (1).png", Texture.class));
        regions[1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Collectibles/Collect (2).png", Texture.class));
        regions[2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Collectibles/Collect (3).png", Texture.class));
        regions[3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Collectibles/Collect (4).png", Texture.class));
        regions[4] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Collectibles/Collect (5).png", Texture.class));
        regions[5] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Collectibles/Collect (6).png", Texture.class));
        regions[6] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Collectibles/Collect (7).png", Texture.class));
        regions[7] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Collectibles/Collect (8).png", Texture.class));

        animation = new Animation(frameDuration, regions[0], regions[1], regions[2], regions[3], regions[4], regions[5], regions[6], regions[7]);
        animation.setPlayMode(Animation.PlayMode.NORMAL);

        width = regions[0].getRegionWidth();
        height = regions[0].getRegionHeight();

        position.add(x, y);

    }


    public void update(float deltaTime) {
        stateTime += deltaTime;

        velocity.y = -maxVelocity;
        velocity.scl(deltaTime);
        position.add(velocity);

    }

    public TextureRegion render() {
        return animation.getKeyFrame(stateTime);
    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public boolean getEndAnimation() {
        if (animation.isAnimationFinished(stateTime)) {
            return true;
        }
        return false;

    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
