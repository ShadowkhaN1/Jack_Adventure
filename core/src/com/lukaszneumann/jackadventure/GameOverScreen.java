package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Lukasz on 2015-01-03.
 */
public class GameOverScreen implements Screen, InputProcessor {

    private MyGame myGame;
    private Sprite gameOverWindow;
    private Sprite replay_1;
    private Sprite replay_2;
    private BitmapFont textCandiesCollected;
    private BitmapFont textBestRecord;

    private boolean isReplayButtonPressedDown = false;
    private boolean isReplayButtonPressedUp = false;

    public GameOverScreen(MyGame myGame) {

        this.myGame = myGame;
        show();
    }

    @Override
    public void render(float delta) {


        Gdx.input.setInputProcessor(this);


        myGame.batch.begin();

        myGame.batch.draw(gameOverWindow, gameOverWindow.getX(), gameOverWindow.getY(), gameOverWindow.getWidth(), gameOverWindow.getHeight());

        if (Gdx.input.isTouched()) {
            touchDown((int) myGame.touch.calcX(Gdx.input.getX()), (int) myGame.touch.calcY(Gdx.input.getY()), 0, 0);
        }


        if (!isReplayButtonPressedDown) {
            myGame.batch.draw(replay_1, replay_1.getX(), replay_1.getY(), replay_1.getWidth(), replay_1.getHeight());

        } else {
            myGame.batch.draw(replay_2, replay_2.getX(), replay_2.getY(), replay_2.getWidth(), replay_2.getHeight());
        }

        if (isReplayButtonPressedUp) {

            isReplayButtonPressedUp = false;
            myGame.isGameOver = false;
            myGame.musicGame.getButtonClicked();
            myGame.setScreen(new GameScreen(myGame));
        }

        textCandiesCollected.draw(myGame.batch, Integer.toString(myGame.dataStorage.getCandyScore()), myGame.WIDTH_SCREEN / 2 - textBestRecord.getBounds(Integer.toString(myGame.dataStorage.getCandyScore())).width / 2,
                gameOverWindow.getY() + gameOverWindow.getHeight() / 2 - 1.2f * textCandiesCollected.getBounds(Integer.toString(myGame.dataStorage.getCandyScore())).height);

        textBestRecord.draw(myGame.batch, Integer.toString(myGame.dataStorage.getScoreHeight()) + " m", myGame.WIDTH_SCREEN / 2 - textBestRecord.getBounds(Integer.toString(myGame.dataStorage.getScoreHeight())).width / 2,
                gameOverWindow.getY() + gameOverWindow.getHeight() / 5);

        myGame.batch.end();

    }

    @Override
    public void resize(int width, int height) {


    }

    @Override
    public void show() {
        gameOverWindow = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "GameOver/GameOverWindow (2).png", Texture.class));
        gameOverWindow.setPosition(myGame.WIDTH_SCREEN / 2 - gameOverWindow.getWidth() / 2, myGame.HEIGHT_SCREEN / 2 - gameOverWindow.getHeight() / 3);

        replay_1 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Replay (1).png", Texture.class));
        replay_1.setPosition(myGame.WIDTH_SCREEN / 2 - replay_1.getWidth() / 2, gameOverWindow.getY() - 1.1f * replay_1.getHeight());

        replay_2 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "pause/Replay (2).png", Texture.class));
        replay_2.setPosition(myGame.WIDTH_SCREEN / 2 - replay_2.getWidth() / 2, gameOverWindow.getY() - 1.1f * replay_1.getHeight());


        textCandiesCollected = myGame.getInitializeGame().getFont();

        textBestRecord = myGame.getInitializeGame().getFont();
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

    //implement methods InputProcessor

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
            myGame.musicGame.getButtonBackClicked();
            myGame.isGameOver = false;
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

        if (replay_1.getBoundingRectangle().contains(screenX, screenY)) {
            isReplayButtonPressedDown = true;

        } else {
            isReplayButtonPressedDown = false;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        Vector3 touchPosition = new Vector3(screenX, screenY, 0);
        myGame.camera.unproject(touchPosition);

        if (replay_1.getBoundingRectangle().contains(touchPosition.x, touchPosition.y)) {
            isReplayButtonPressedDown = false;
            isReplayButtonPressedUp = true;

        } else {
            isReplayButtonPressedDown = false;
            isReplayButtonPressedUp = false;
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
