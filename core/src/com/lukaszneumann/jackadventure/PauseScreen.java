package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Lukasz on 2014-12-17.
 */
public class PauseScreen implements Screen {


    private MyGame myGame;
    private Sprite pauseWindow;
    private Sprite resume_1;
    private Sprite resume_2;
    private Sprite replay_1;
    private Sprite replay_2;
    private Sprite exit_1;
    private Sprite exit_2;
    private Sprite music_1;
    private Sprite music_2;
    private Sprite sound_1;
    private Sprite sound_2;


    public PauseScreen(MyGame myGame) {
        this.myGame = myGame;
        show();
    }

    @Override
    public void render(float delta) {

        if (myGame.isSound && Gdx.input.justTouched() && sound_1.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
            myGame.isSound = false;
        } else if (!myGame.isSound && Gdx.input.justTouched() && sound_1.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
            myGame.isSound = true;
        }

        if (myGame.isMusic && Gdx.input.justTouched() && music_1.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
            myGame.isMusic = false;
        } else if (!myGame.isMusic && Gdx.input.justTouched() && music_1.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
            myGame.isMusic = true;
        }

        if (Gdx.input.justTouched() && resume_1.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
            myGame.isPause = false;
        }

        if (Gdx.input.justTouched() && replay_1.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
            myGame.isPause = false;
            myGame.getScreen().dispose();
            myGame.setScreen(new GameScreen(myGame));
        }

        if (exit_1.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
            myGame.setScreen(new MenuScreen(myGame));
        }

        myGame.batch.begin();

        myGame.batch.draw(pauseWindow, pauseWindow.getX(), pauseWindow.getY(), pauseWindow.getWidth(), pauseWindow.getHeight());
        myGame.batch.draw(exit_1, exit_1.getX(), exit_1.getY(), exit_1.getWidth(), exit_1.getHeight());
        myGame.batch.draw(replay_1, replay_1.getX(), replay_1.getY(), replay_1.getWidth(), replay_1.getHeight());
        myGame.batch.draw(resume_1, resume_1.getX(), resume_1.getY(), resume_1.getWidth(), resume_1.getHeight());


        if (myGame.isSound) {
            sound_1.draw(myGame.batch);
        } else {
            sound_2.draw(myGame.batch);
        }

        if (myGame.isMusic) {
            music_1.draw(myGame.batch);
        } else {
            music_2.draw(myGame.batch);
        }

        myGame.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

        pauseWindow = new Sprite(myGame.content.getAssetManager().get("Pause/Pause Window.png", Texture.class));
        pauseWindow.setPosition(myGame.WIDTH_SCREEN / 2 - pauseWindow.getWidth() / 2, myGame.HEIGHT_SCREEN / 2 - pauseWindow.getHeight() / 2);

        resume_1 = new Sprite(myGame.content.getAssetManager().get("Pause/Resume (1).png", Texture.class));
        resume_1.setPosition(myGame.WIDTH_SCREEN / 2 - resume_1.getWidth() / 2, pauseWindow.getY() + pauseWindow.getHeight() / 2.15f);

        resume_2 = new Sprite(myGame.content.getAssetManager().get("Pause/Resume (2).png", Texture.class));

        replay_1 = new Sprite(myGame.content.getAssetManager().get("Pause/Replay (1).png", Texture.class));
        replay_1.setPosition(myGame.WIDTH_SCREEN / 2 - replay_1.getWidth() / 2, pauseWindow.getY() + pauseWindow.getHeight() / 3.6f);

        replay_2 = new Sprite(myGame.content.getAssetManager().get("Pause/Replay (2).png", Texture.class));

        exit_1 = new Sprite(myGame.content.getAssetManager().get("Pause/Exit (1).png", Texture.class));
        exit_1.setPosition(myGame.WIDTH_SCREEN / 2 - exit_1.getWidth() / 2, pauseWindow.getY() + pauseWindow.getHeight() / 11);

        exit_2 = new Sprite(myGame.content.getAssetManager().get("Pause/Exit (2).png", Texture.class));

        music_1 = new Sprite(myGame.content.getAssetManager().get("Pause/Music (1).png", Texture.class));
        music_1.setPosition(pauseWindow.getX() + pauseWindow.getWidth() / 4, pauseWindow.getY() + pauseWindow.getHeight() / 1.52f);

        music_2 = new Sprite(myGame.content.getAssetManager().get("Pause/Music (3).png", Texture.class));
        music_2.setPosition(pauseWindow.getX() + pauseWindow.getWidth() / 4, pauseWindow.getY() + pauseWindow.getHeight() / 1.52f);

        sound_1 = new Sprite(myGame.content.getAssetManager().get("Pause/Sound (1).png", Texture.class));
        sound_1.setPosition(pauseWindow.getX() + pauseWindow.getWidth() * 0.75f - sound_1.getWidth(), pauseWindow.getY() + pauseWindow.getHeight() / 1.52f);

        sound_2 = new Sprite(myGame.content.getAssetManager().get("Pause/Sound (3).png", Texture.class));
        sound_2.setPosition(pauseWindow.getX() + pauseWindow.getWidth() * 0.75f - sound_2.getWidth(), pauseWindow.getY() + pauseWindow.getHeight() / 1.52f);


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
