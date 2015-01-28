package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukasz on 2014-11-10.
 */
public class LaunchingPlayer extends Sprite {

    static float WIDTH = 0;
    static float HEIGHT = 0;


    static float MAX_VELOCITY = 700;

    enum State {
        Idle, Launch
    }

    State state = State.Idle;

    private MyGame myGame;
    float stateTimeIdle = 0;
    float stateTimeLaunch = 0;
    private Animation launch;
    private Animation idle;
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private float frameDuration = 0.15f;
    private int start = 5;
    private boolean isLaunched = false;
    private boolean soundStartRocker = false;

    private boolean endAnimation = false;
    private boolean isFlying = false;

    public LaunchingPlayer(MyGame myGame) {

        this.myGame = myGame;


        TextureRegion[] regions = {new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Launch (1).png", Texture.class)), new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Launch (2).png", Texture.class)),
                new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Launch (3).png", Texture.class)), new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Launch (4).png", Texture.class)), new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Launch (5).png", Texture.class)),
                new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Launch (6).png", Texture.class)), new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Launch (7).png", Texture.class)), new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Launch (8).png", Texture.class)),
                new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Launch (9).png", Texture.class))};
        launch = new Animation(frameDuration, regions[0], regions[1], regions[2], regions[3], regions[4], regions[5], regions[6], regions[7], regions[8]);
        launch.setPlayMode(Animation.PlayMode.NORMAL);


        regions = new TextureRegion[4];
        regions[0] = new TextureRegion(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Idle (1).png", Texture.class));
        regions[1] = new TextureRegion(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Idle (2).png", Texture.class));
        regions[2] = new TextureRegion(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Idle (3).png", Texture.class));
        regions[3] = new TextureRegion(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Idle (4).png", Texture.class));

        idle = new Animation(frameDuration, regions[0], regions[1], regions[2], regions[3]);
        idle.setPlayMode(Animation.PlayMode.LOOP);


        WIDTH = regions[0].getRegionWidth();
        HEIGHT = regions[0].getRegionHeight();

        position.add((myGame.WIDTH_SCREEN / 2 - WIDTH / 2), 0);
        setPosition(position.x, position.y);
        setOrigin(WIDTH / 2, HEIGHT / 2);

    }

    public void update(float deltaTime) {

        stateTimeIdle += deltaTime;

        if (isLaunched) {
            state = State.Launch;

        } else if (!isLaunched) {
            state = State.Idle;
        }
        if (isLaunched) {
            stateTimeLaunch += deltaTime;
        }

        if (isLaunched) {
            if (launch.getKeyFrameIndex(stateTimeLaunch) >= start) {
                isFlying = true;

                if (soundStartRocker == false) {
                    myGame.soundGame.getStartRocket();
                    soundStartRocker = true;
                }
            }

            if (launch.isAnimationFinished(stateTimeLaunch)) {
                endAnimation = true;

            }
        }

        if (isFlying) {

            velocity.y = -MAX_VELOCITY;
            velocity.scl(deltaTime);
            position.add(velocity);
            setPosition(position.x, position.y);
        }
    }


    public TextureRegion render() {

        switch (state) {
            case Idle:
                return idle.getKeyFrame(stateTimeIdle);
            case Launch:
                return launch.getKeyFrame(stateTimeLaunch);
            default:
                return idle.getKeyFrame(stateTimeIdle);
        }
    }

    public void setLaunched(boolean isLaunched) {
        this.isLaunched = isLaunched;
    }

    public boolean getEndAnimation() {
        return endAnimation;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean isFlying) {
        this.isFlying = isFlying;
    }
}
