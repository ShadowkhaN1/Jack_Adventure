package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Lukasz on 2014-12-14.
 */
public class InitializeScreen implements Screen {

    private MyGame myGame;
    private TextureRegion[] regions;
    private Animation animation;
    private float stateTime = 0;
    private float frameDuration = 0.15f;

    public InitializeScreen(MyGame myGame) {
        this.myGame = myGame;
        show();
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        myGame.batch.begin();

        myGame.batch.draw(animation.getKeyFrame(stateTime), (Gdx.graphics.getWidth() / 2 - regions[0].getTexture().getWidth() / 2),
                (Gdx.graphics.getHeight() / 2 - regions[0].getTexture().getHeight() / 2), regions[0].getRegionWidth(), regions[0].getRegionHeight());

        myGame.batch.end();

        if (myGame.getInitializeGame().getAssetManager().getProgress() >= 1) {
            myGame.isMusic = true;
            myGame.setScreen(new MenuScreen(myGame));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

        regions = new TextureRegion[6];
        regions[0] = new TextureRegion(new Texture(Gdx.files.internal("Jack/Copter/Idle (1).png")));
        regions[1] = new TextureRegion(new Texture(Gdx.files.internal("Jack/Copter/Idle (2).png")));
        regions[2] = new TextureRegion(new Texture(Gdx.files.internal("Jack/Copter/Idle (3).png")));
        regions[3] = new TextureRegion(new Texture(Gdx.files.internal("Jack/Copter/Idle (4).png")));
        regions[4] = new TextureRegion(new Texture(Gdx.files.internal("Jack/Copter/Idle (5).png")));
        regions[5] = new TextureRegion(new Texture(Gdx.files.internal("Jack/Copter/Idle (6).png")));


        animation = new Animation(frameDuration, regions[0], regions[1], regions[2], regions[3], regions[4], regions[5]);
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }


    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
