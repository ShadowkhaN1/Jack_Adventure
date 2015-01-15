package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Lukasz on 2015-01-05.
 */
public class SoundGame {

    private MyGame myGame;
    private Sound[] candy;
    private Sound powerUp;
    private Sound laughWitch;


    public SoundGame(MyGame myGame) {
        this.myGame = myGame;
        candy = new Sound[3];
    }

    public void createSoundGame() {
        candy[0] = myGame.content.getAssetManager().get("Sound/Candy (1).wav", Sound.class);
        candy[1] = myGame.content.getAssetManager().get("Sound/Candy (2).wav", Sound.class);
        candy[2] = myGame.content.getAssetManager().get("Sound/Candy (3).wav", Sound.class);

        laughWitch = myGame.getContent().getAssetManager().get("Sound/Witch_laugh.wav", Sound.class);

        powerUp = myGame.getContent().getAssetManager().get("Sound/PowerUp.wav", Sound.class);

    }

    public Sound getLaughWitch() {
        return laughWitch;
    }

    public Sound getRandomCandy() {
        return candy[MathUtils.random(0, 2)];
    }

    public Sound getPowerUp() {
        return powerUp;
    }
}
