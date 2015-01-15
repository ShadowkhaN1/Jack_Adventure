package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukasz on 2014-11-27.
 */
public class GameObjectAnimation extends Sprite {


    private MyGame myGame;
    private float maxVelocity = 0;
    private Animation animation;
    private float stateTime = 0;
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private float frameDuration = 0.15f;

    private float width = 0;
    private float height = 0;

    public GameObjectAnimation(MyGame myGame, TextureRegion[] textures) {

        this.myGame = myGame;

        animation = new Animation(frameDuration, textures);
        animation.setPlayMode(Animation.PlayMode.LOOP);


        width = textures[0].getTexture().getWidth() * WorldGame.PIXELS_TO_METERS;
        height = textures[0].getTexture().getHeight() * WorldGame.PIXELS_TO_METERS;
        setSize(width, height);
        // setSize(regions[0].getRegionWidth() * 0.75f, regions[0].getRegionHeight() * 0.75f);

    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        // width = animation.getKeyFrame(stateTime).getTexture().getWidth() * myGame.worldGame.PIXELS_TO_METERS;
        // height = animation.getKeyFrame(stateTime).getTexture().getHeight() * myGame.worldGame.PIXELS_TO_METERS;
        // setSize(width, height);
        //setOriginCenter();


//        if (isPhysics) {
//            velocity.y = -maxVelocity;
//            velocity.scl(deltaTime);
//            position.add(velocity);
//            setPosition(position.x, position.y);
//        }

    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public void setPositionX(float x) {
        position.x = x;
    }

    public void setPositionY(float y) {
        position.y = y;
    }

    public TextureRegion render() {
        return animation.getKeyFrame(stateTime);
    }

    public boolean endAnimation() {
        if (animation.isAnimationFinished(stateTime)) {
            return true;
        }
        return false;
    }

    public void stopAnimation() {
        animation.setPlayMode(Animation.PlayMode.NORMAL);
    }


}
