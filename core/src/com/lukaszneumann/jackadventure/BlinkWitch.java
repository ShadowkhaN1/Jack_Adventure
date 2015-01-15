package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Lukasz on 2015-01-12.
 */
public class BlinkWitch extends Sprite {


    private Animation animation;
    private TextureRegion[] textureRegions;


    private float width = 0;
    private float height = 0;

    private float frameDuration = 0.15f;
    private float stateTime = 0;
    private boolean isVisible = false;
    private boolean finishedAnimationBlink = false;

    public BlinkWitch(MyGame myGame) {

        textureRegions = new TextureRegion[8];
        textureRegions[0] = new TextureRegion(myGame.getContent().getAssetManager().get("Explosion/Collectibles/Collect (1).png", Texture.class));
        textureRegions[1] = new TextureRegion(myGame.getContent().getAssetManager().get("Explosion/Collectibles/Collect (2).png", Texture.class));
        textureRegions[2] = new TextureRegion(myGame.getContent().getAssetManager().get("Explosion/Collectibles/Collect (3).png", Texture.class));
        textureRegions[3] = new TextureRegion(myGame.getContent().getAssetManager().get("Explosion/Collectibles/Collect (4).png", Texture.class));
        textureRegions[4] = new TextureRegion(myGame.getContent().getAssetManager().get("Explosion/Collectibles/Collect (5).png", Texture.class));
        textureRegions[5] = new TextureRegion(myGame.getContent().getAssetManager().get("Explosion/Collectibles/Collect (6).png", Texture.class));
        textureRegions[6] = new TextureRegion(myGame.getContent().getAssetManager().get("Explosion/Collectibles/Collect (7).png", Texture.class));
        textureRegions[7] = new TextureRegion(myGame.getContent().getAssetManager().get("Explosion/Collectibles/Collect (8).png", Texture.class));


        animation = new Animation(frameDuration, textureRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        width = textureRegions[0].getRegionWidth();
        height = textureRegions[0].getRegionHeight();

        setSize(width, height);
        setOriginCenter();

    }


    public void update(float deltaTime) {

        if (isVisible == true) {
            stateTime += deltaTime;
        }

        if (isVisible() == true && animation.isAnimationFinished(stateTime) == true) {
            stateTime = 0;
            isVisible = false;
            finishedAnimationBlink = true;
        }

    }

    public TextureRegion render() {
        return animation.getKeyFrame(stateTime);
    }

    public Animation getAnimation() {
        return animation;
    }


    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }


    public boolean isFinishedAnimationBlink() {
        return finishedAnimationBlink;
    }

    public void setFinishedAnimationBlink(boolean finishedAnimationBlink) {
        this.finishedAnimationBlink = finishedAnimationBlink;
    }
}
