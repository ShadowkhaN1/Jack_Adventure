package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Lukasz on 2015-01-12.
 */
public class MusicGame {

    private MyGame myGame;
    private Music musicGame;
    private Sound buttonClicked;
    private Sound buttonBackClicked;


    public MusicGame(MyGame myGame) {

        this.myGame = myGame;
        musicGame = Gdx.audio.newMusic(Gdx.files.internal("Data/GameMusic.mp3"));

        buttonClicked = Gdx.audio.newSound(Gdx.files.internal("Data/ButtonClick.wav"));

        buttonBackClicked = Gdx.audio.newSound(Gdx.files.internal("Data/ButtonBackClicked.wav"));

    }


    public Music getMusicGame() {
        return musicGame;
    }

    public void getButtonClicked() {

        if (myGame.isSound == true) {
            buttonClicked.play();
        }
    }

    public void getButtonBackClicked() {

        if (myGame.isSound) {
            buttonBackClicked.play();

        }
    }

}
