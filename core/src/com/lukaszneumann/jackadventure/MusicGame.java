package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Lukasz on 2015-01-12.
 */
public class MusicGame {

    private Music musicGame;


    public MusicGame(MyGame myGame) {
        musicGame = Gdx.audio.newMusic(Gdx.files.internal("Sound/GameMusic.mp3"));
    }


    public Music getMusicGame() {
        return musicGame;
    }
}
