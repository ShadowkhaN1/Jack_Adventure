package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Lukasz on 2014-12-01.
 */
public class LoadingScreen implements Screen, InputProcessor {

    private MyGame myGame;
    private BitmapFont text;
    private Integer valueLoadContent = 0;


    public LoadingScreen(MyGame myGame) {
        this.myGame = myGame;
        show();
    }


    @Override
    public void render(float delta) {

        Gdx.input.setInputProcessor(this);

        myGame.batch.begin();


        text.draw(myGame.batch, "Loading...", myGame.WIDTH_SCREEN / 2 - text.getBounds("Loading...").width / 2, 2 * text.getCapHeight());
        text.draw(myGame.batch, valueLoadContent.toString((int) (myGame.getContent().getAssetManager().getProgress() * 100)) + "",
                myGame.WIDTH_SCREEN / 2 - text.getBounds(valueLoadContent.toString((int) (myGame.getContent().getAssetManager().getProgress() * 100)) + "%").width / 2, myGame.HEIGHT_SCREEN / 2);


        myGame.batch.end();

        if (myGame.content.getAssetManager().getProgress() >= 1) {
            dispose();
            myGame.setScreen(new GameScreen(myGame));
        }
    }

    @Override
    public void show() {

        text = myGame.initializeGame.getFont();
    }

    @Override
    public void hide() {

    }


    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

       // text.dispose();
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
