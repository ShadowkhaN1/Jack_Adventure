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
    private Sound health;
    private Sound damage;
    private Sound startRocket;
    private Sound newStage;
    private Sound usePowerUp;
    private Sound playerYeah;


    public SoundGame(MyGame myGame) {
        this.myGame = myGame;
        candy = new Sound[3];
    }

    public void createSoundGame() {
        candy[0] = myGame.content.getAssetManager().get("Data/Candy (1).wav", Sound.class);
        candy[1] = myGame.content.getAssetManager().get("Data/Candy (2).wav", Sound.class);
        candy[2] = myGame.content.getAssetManager().get("Data/Candy (3).wav", Sound.class);

        laughWitch = myGame.getContent().getAssetManager().get("Data/Witch_laugh.wav", Sound.class);

        powerUp = myGame.getContent().getAssetManager().get("Data/PowerUp.wav", Sound.class);

        health = myGame.getContent().getAssetManager().get("Data/health.wav", Sound.class);
        playerYeah = myGame.getContent().getAssetManager().get("Data/playerYeah.mp3", Sound.class);
        startRocket = myGame.content.getAssetManager().get("Data/startRocket.wav", Sound.class);
        newStage = myGame.content.getAssetManager().get("Data/newStage.mp3", Sound.class);
        usePowerUp = myGame.content.getAssetManager().get("Data/usePowerUp.mp3", Sound.class);
        damage = myGame.content.getAssetManager().get("Data/damage.mp3", Sound.class);

    }

    public void getLaughWitch() {

        if (myGame.isSound) {
            laughWitch.play();
        }
    }

    public void getRandomCandy() {

        if (myGame.isSound) {
            candy[MathUtils.random(0, 2)].play();
        }
    }

    public void getPowerUp() {

        if (myGame.isSound) {
            powerUp.play();
        }

    }

    public void getHealth() {

        if (myGame.isSound) {
            health.play();
        }
    }

    public void getPlayerYeah() {

        if (myGame.isSound) {
            playerYeah.play();
        }
    }

    public void getStartRocket() {

        if (myGame.isSound) {
            startRocket.play();
        }
    }

    public void getNewStage() {

        if (myGame.isSound) {
            newStage.play();
        }
    }

    public void getUsePowerUp() {

        if (myGame.isSound) {
            usePowerUp.play();
        }
    }

    public void getDamage() {

        if (myGame.isSound) {
            damage.play();
        }
    }
}
