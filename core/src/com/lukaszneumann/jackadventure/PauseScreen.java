package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Lukasz on 2014-12-25.
 */
public class PauseScreen implements Screen, InputProcessor {


    private MyGame myGame;
    private GameScreen gameScreen;
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

    private boolean isResumePressedDown = false;
    private boolean isResumePressedUp = false;
    private boolean isReplayPressedDown = false;
    private boolean isReplayPressedUp = false;
    private boolean isExitPressedDown = false;
    private boolean isExitPressedUp = false;
    private boolean isMusicPressedUp = false;
    private boolean isSoundPressedUp = false;


    public PauseScreen(MyGame myGame, GameScreen gameScreen) {
        this.myGame = myGame;
        this.gameScreen = gameScreen;
        show();


    }

    @Override
    public void render(float delta) {


        Gdx.input.setInputProcessor(this);

        if (Gdx.input.isTouched()) {
            touchDown((int) myGame.touch.calcX(Gdx.input.getX()), (int) myGame.touch.calcY(Gdx.input.getY()), 0, 0);
        }

        myGame.batch.begin();


        myGame.batch.draw(pauseWindow, pauseWindow.getX(), pauseWindow.getY(), pauseWindow.getWidth(), pauseWindow.getHeight());

        drawResumeButton();
        drawReplayButton();
        drawExitButton();
        drawMusicButton();
        drawSoundButton();


        myGame.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

        pauseWindow = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Pause Window.png", Texture.class));
        pauseWindow.setPosition(myGame.WIDTH_SCREEN / 2 - pauseWindow.getWidth() / 2, myGame.HEIGHT_SCREEN / 2 - pauseWindow.getHeight() / 2);

        resume_1 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Resume (1).png", Texture.class));
        resume_1.setPosition(myGame.WIDTH_SCREEN / 2 - resume_1.getWidth() / 2, pauseWindow.getY() + pauseWindow.getHeight() / 2.15f);

        resume_2 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Resume (2).png", Texture.class));
        resume_2.setPosition(myGame.WIDTH_SCREEN / 2 - resume_2.getWidth() / 2, pauseWindow.getY() + pauseWindow.getHeight() / 2.15f);

        replay_1 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Replay (1).png", Texture.class));
        replay_1.setPosition(myGame.WIDTH_SCREEN / 2 - replay_1.getWidth() / 2, pauseWindow.getY() + pauseWindow.getHeight() / 3.6f);

        replay_2 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Replay (2).png", Texture.class));
        replay_2.setPosition(myGame.WIDTH_SCREEN / 2 - replay_2.getWidth() / 2, pauseWindow.getY() + pauseWindow.getHeight() / 3.6f);

        exit_1 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Exit (1).png", Texture.class));
        exit_1.setPosition(myGame.WIDTH_SCREEN / 2 - exit_1.getWidth() / 2, pauseWindow.getY() + pauseWindow.getHeight() / 11);

        exit_2 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Exit (2).png", Texture.class));
        exit_2.setPosition(myGame.WIDTH_SCREEN / 2 - exit_2.getWidth() / 2, pauseWindow.getY() + pauseWindow.getHeight() / 11);

        music_1 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Music (1).png", Texture.class));
        music_1.setPosition(pauseWindow.getX() + pauseWindow.getWidth() / 4, pauseWindow.getY() + pauseWindow.getHeight() / 1.52f);

        music_2 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Music (3).png", Texture.class));
        music_2.setPosition(pauseWindow.getX() + pauseWindow.getWidth() / 4, pauseWindow.getY() + pauseWindow.getHeight() / 1.52f);

        sound_1 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Sound (1).png", Texture.class));
        sound_1.setPosition(pauseWindow.getX() + pauseWindow.getWidth() * 0.75f - sound_1.getWidth(), pauseWindow.getY() + pauseWindow.getHeight() / 1.52f);

        sound_2 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Sound (3).png", Texture.class));
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

    private void saveScoresToFile() {

        gameScreen.writeScoreCandyToFile();
        gameScreen.writeScoreHeightToFile();
    }


    private void drawResumeButton() {

        if (!isResumePressedDown) {
            myGame.batch.draw(resume_1, resume_1.getX(), resume_1.getY(), resume_1.getWidth(), resume_1.getHeight());

        } else {
            myGame.batch.draw(resume_2, resume_2.getX(), resume_2.getY(), resume_2.getWidth(), resume_2.getHeight());
        }

        if (isResumePressedUp) {
            myGame.musicGame.getButtonClicked();
            isResumePressedUp = false;
            myGame.isPause = false;
        }

    }


    private void drawReplayButton() {

        if (!isReplayPressedDown) {
            myGame.batch.draw(replay_1, replay_1.getX(), replay_1.getY(), replay_1.getWidth(), replay_1.getHeight());

        } else {
            myGame.batch.draw(replay_2, replay_2.getX(), replay_2.getY(), replay_2.getWidth(), replay_2.getHeight());
        }

        if (isReplayPressedUp) {

            isReplayPressedUp = false;
            myGame.isPause = false;
            myGame.isGameOver = false;
            myGame.musicGame.getButtonClicked();
            saveScoresToFile();
            myGame.setScreen(new GameScreen(myGame));
        }

    }


    private void drawExitButton() {

        if (!isExitPressedDown) {
            myGame.batch.draw(exit_1, exit_1.getX(), exit_1.getY(), exit_1.getWidth(), exit_1.getHeight());

        } else {
            myGame.batch.draw(exit_2, exit_2.getX(), exit_2.getY(), exit_2.getWidth(), exit_2.getHeight());
        }

        if (isExitPressedUp) {

            isExitPressedUp = false;
            myGame.isPause = false;
            myGame.musicGame.getButtonClicked();
            saveScoresToFile();
            myGame.setScreen(new MenuScreen(myGame));
        }

    }


    private void drawMusicButton() {


        if (myGame.isMusic == true) {
            myGame.batch.draw(music_1, music_1.getX(), music_1.getY(), music_1.getWidth(), music_1.getHeight());

        } else {
            myGame.batch.draw(music_2, music_2.getX(), music_2.getY(), music_2.getWidth(), music_2.getHeight());
        }


        if (isMusicPressedUp) {
            isMusicPressedUp = false;
            myGame.musicGame.getButtonClicked();

            if (myGame.isMusic == true) {
                myGame.isMusic = false;
            } else {
                myGame.isMusic = true;
            }
        }
    }


    private void drawSoundButton() {

        if (myGame.isSound == true) {
            myGame.batch.draw(sound_1, sound_1.getX(), sound_1.getY(), sound_1.getWidth(), sound_1.getHeight());

        } else {
            myGame.batch.draw(sound_2, sound_2.getX(), sound_2.getY(), sound_2.getWidth(), sound_2.getHeight());
        }

        if (isSoundPressedUp) {
            isSoundPressedUp = false;
            myGame.musicGame.getButtonClicked();

            if (myGame.isSound == true) {
                myGame.isSound = false;
            } else {
                myGame.isSound = true;
            }
        }
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.BACK) {

            myGame.isPause = false;
            myGame.musicGame.getButtonBackClicked();
            saveScoresToFile();
            myGame.setScreen(new MenuScreen(myGame));
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {


        if (resume_1.getBoundingRectangle().contains(screenX, screenY)) {
            isResumePressedDown = true;

        } else {
            isResumePressedDown = false;
        }


        if (replay_1.getBoundingRectangle().contains(screenX, screenY)) {
            isReplayPressedDown = true;

        } else {
            isReplayPressedDown = false;
        }


        if (exit_1.getBoundingRectangle().contains(screenX, screenY)) {
            isExitPressedDown = true;

        } else {
            isExitPressedDown = false;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        Vector3 touchPosition = new Vector3(screenX, screenY, 0);
        myGame.camera.unproject(touchPosition);


        if (resume_1.getBoundingRectangle().contains(touchPosition.x, touchPosition.y)) {
            isResumePressedDown = false;
            isResumePressedUp = true;

        } else {
            isResumePressedDown = false;
        }


        if (replay_1.getBoundingRectangle().contains(touchPosition.x, touchPosition.y)) {
            isReplayPressedDown = false;
            isReplayPressedUp = true;

        } else {
            isReplayPressedDown = false;
        }


        if (exit_1.getBoundingRectangle().contains(touchPosition.x, touchPosition.y)) {
            isExitPressedDown = false;
            isExitPressedUp = true;

        } else {
            isExitPressedDown = false;
        }


        if (music_1.getBoundingRectangle().contains(touchPosition.x, touchPosition.y)) {
            isMusicPressedUp = true;

        }


        if (sound_1.getBoundingRectangle().contains(touchPosition.x, touchPosition.y)) {
            isSoundPressedUp = true;

        }


        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
