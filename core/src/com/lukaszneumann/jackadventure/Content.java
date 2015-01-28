package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Lukasz on 2014-11-30.
 */
public class Content {

    AssetManager assetManager;


    public Content(String usesDpi) {

        assetManager = new AssetManager();
        assetManager.load(usesDpi + "/" + "Candy/Candy (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Candy/Candy (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Candy/Candy (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Icon/Shield.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Icon/Magnet.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Icon/Copter.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Indicator/Top Border.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Indicator/Candy.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Indicator/Height.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/BG2.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/1.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/2.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/3.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/4.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/5.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/5.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/6.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/7.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/8.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/9.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/10.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/Cloud (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/Cloud (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/Cloud (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Background/Cloud (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Enemy/Bat (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Enemy/Bat (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Enemy/Bat (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Enemy/Bat (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Enemy/Platform (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Enemy/Platform (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Enemy/Hammer.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Enemy/witch.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Enemy/Ghost.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Enemy/Pumpkin.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Enemy/Spike.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Jump (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Jump (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Jump (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Jump (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Dead (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Dead (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Dead (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Dead (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Dead (5).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Dead (6).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Dead (7).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Dead (8).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Fall (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Fall (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Fall (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Fall (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Idle (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Idle (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Idle (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Idle (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Launch (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Launch (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Launch (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Launch (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Launch (5).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Launch (6).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Launch (7).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Launch (8).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Launch (9).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Lean Left (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Lean Left (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Lean Left (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Lean Left (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Lean Left (5).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Lean Right (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Lean Right (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Lean Right (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Lean Right (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Lean Right (5).png", Texture.class);


        assetManager.load(usesDpi + "/" + "Jack/Magnet/Jump (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Jump (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Jump (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Jump (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Lean Left (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Lean Left (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Lean Left (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Lean Left (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Lean Left (5).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Lean Right (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Lean Right (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Lean Right (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Lean Right (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Magnet/Lean Right (5).png", Texture.class);


        assetManager.load(usesDpi + "/" + "Jack/Armored/Jump (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Jump (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Jump (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Jump (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Lean Left (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Lean Left (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Lean Left (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Lean Left (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Lean Left (5).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Lean Right (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Lean Right (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Lean Right (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Lean Right (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Armored/Lean Right (5).png", Texture.class);


        assetManager.load(usesDpi + "/" + "Jack/Copter/Copter Normal (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Copter/Copter Normal (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Copter/Copter Normal (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Copter/Copter Normal (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Copter/Copter Normal (5).png", Texture.class);


        assetManager.load(usesDpi + "/" + "PowerUp/Shield.png", Texture.class);
        assetManager.load(usesDpi + "/" + "PowerUp/Magnet.png", Texture.class);
        assetManager.load(usesDpi + "/" + "PowerUp/Copter.png", Texture.class);
        assetManager.load(usesDpi + "/" + "PowerUp/Boost (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "PowerUp/Boost (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "PowerUp/Boost (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "PowerUp/PanelUpgrade (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "PowerUp/PanelUpgrade (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "PowerUp/Shield (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "PowerUp/Shield (2).png", Texture.class);

        assetManager.load(usesDpi + "/" + "Buttons/Pause (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Buttons/Pause (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Collectibles/Collect (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Collectibles/Collect (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Collectibles/Collect (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Collectibles/Collect (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Collectibles/Collect (5).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Collectibles/Collect (6).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Collectibles/Collect (7).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Collectibles/Collect (8).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Smoke/Smoke (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Smoke/Smoke (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Smoke/Smoke (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Smoke/Smoke (4).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Smoke/Smoke (5).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Smoke/Smoke (6).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Smoke/Smoke (7).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Explosion/Smoke/Smoke (8).png", Texture.class);
        assetManager.load(usesDpi + "/" + "pause/Exit (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "pause/Exit (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "pause/Music (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "pause/Music (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "pause/Pause Window.png", Texture.class);
        assetManager.load(usesDpi + "/" + "pause/Replay (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "pause/Replay (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "pause/Resume (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "pause/Resume (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "pause/Sound (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "pause/Sound (3).png", Texture.class);
//        assetManager.load("Jumping Platform/1/Idle.png", Texture.class);
//        assetManager.load("Jumping Platform/1/Bounce (1).png", Texture.class);
//        assetManager.load("Jumping Platform/1/Bounce (2).png", Texture.class);
//        assetManager.load("Jumping Platform/1/Bounce (3).png", Texture.class);
//        assetManager.load("Jumping Platform/1/Bounce (4).png", Texture.class);
//        assetManager.load("Jumping Platform/1/Bounce (5).png", Texture.class);
//        assetManager.load("Jumping Platform/2/Idle.png", Texture.class);
//        assetManager.load("Jumping Platform/2/Bounce (1).png", Texture.class);
//        assetManager.load("Jumping Platform/2/Bounce (2).png", Texture.class);
//        assetManager.load("Jumping Platform/2/Bounce (3).png", Texture.class);
//        assetManager.load("Jumping Platform/2/Bounce (4).png", Texture.class);
//        assetManager.load("Jumping Platform/2/Bounce (5).png", Texture.class);
//        assetManager.load("Jumping Platform/3/Idle.png", Texture.class);
//        assetManager.load("Jumping Platform/3/Bounce (1).png", Texture.class);
//        assetManager.load("Jumping Platform/3/Bounce (2).png", Texture.class);
//        assetManager.load("Jumping Platform/3/Bounce (3).png", Texture.class);
//        assetManager.load("Jumping Platform/3/Bounce (4).png", Texture.class);
//        assetManager.load("Jumping Platform/3/Bounce (5).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Health/Hearth.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Health/Tombstone.png", Texture.class);
        assetManager.load(usesDpi + "/" + "GameOver/GameOverWindow (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Text/Launch.png", Texture.class);


        assetManager.load("Data/PowerUp.wav", Sound.class);
        assetManager.load("Data/Candy (1).wav", Sound.class);
        assetManager.load("Data/Candy (2).wav", Sound.class);
        assetManager.load("Data/Candy (3).wav", Sound.class);
        assetManager.load("Data/Witch_laugh.wav", Sound.class);
        assetManager.load("Data/health.wav", Sound.class);
        assetManager.load("Data/playerYeah.mp3", Sound.class);
        assetManager.load("Data/startRocket.wav", Sound.class);
        assetManager.load("Data/newStage.mp3", Sound.class);
        assetManager.load("Data/usePowerUp.mp3", Sound.class);
        assetManager.load("Data/damage.mp3", Sound.class);


    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void dispose() {
        assetManager.dispose();
    }

}
