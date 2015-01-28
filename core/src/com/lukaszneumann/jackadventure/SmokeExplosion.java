package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created by Lukasz on 2015-01-12.
 */
public class SmokeExplosion extends Sprite {


    private Animation animation;
    private TextureRegion[] textureRegions;


    private float width = 0;
    private float height = 0;

    private float frameDuration = 0.15f;
    private float stateTime = 0;

    public SmokeExplosion(MyGame myGame) {

        textureRegions = new TextureRegion[8];
        textureRegions[0] = new TextureRegion(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Smoke/Smoke (1).png", Texture.class));
        textureRegions[1] = new TextureRegion(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Smoke/Smoke (2).png", Texture.class));
        textureRegions[2] = new TextureRegion(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Smoke/Smoke (3).png", Texture.class));
        textureRegions[3] = new TextureRegion(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Smoke/Smoke (4).png", Texture.class));
        textureRegions[4] = new TextureRegion(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Smoke/Smoke (5).png", Texture.class));
        textureRegions[5] = new TextureRegion(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Smoke/Smoke (6).png", Texture.class));
        textureRegions[6] = new TextureRegion(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Smoke/Smoke (7).png", Texture.class));
        textureRegions[7] = new TextureRegion(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Explosion/Smoke/Smoke (8).png", Texture.class));

        animation = new Animation(frameDuration, textureRegions);
        animation.setPlayMode(Animation.PlayMode.NORMAL);

        width = textureRegions[0].getRegionWidth();
        height = textureRegions[0].getRegionHeight();

        setSize(width, height);
        setOriginCenter();
    }


    public void update(float deltaTime) {
        stateTime += deltaTime;
    }

    public TextureRegion render() {
        return animation.getKeyFrame(stateTime);
    }

    public boolean isFinishedAnimation() {
        return animation.isAnimationFinished(stateTime);
    }

}
