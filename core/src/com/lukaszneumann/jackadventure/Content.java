package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Lukasz on 2014-11-30.
 */
public class Content {

    AssetManager assetManager;


    public Content() {

        assetManager = new AssetManager();
        assetManager.load("Candy/Candy (1).png", Texture.class);
        assetManager.load("Candy/Candy (2).png", Texture.class);
        assetManager.load("Candy/Candy (3).png", Texture.class);
        assetManager.load("Icon/Shield.png", Texture.class);
        assetManager.load("Icon/Magnet.png", Texture.class);
        assetManager.load("Icon/Copter.png", Texture.class);
        assetManager.load("Indicator/Top Border.png", Texture.class);
        assetManager.load("Indicator/Candy.png", Texture.class);
        assetManager.load("Indicator/Height.png", Texture.class);
        assetManager.load("Background/BG2.png", Texture.class);
        assetManager.load("Background/1.png", Texture.class);
        assetManager.load("Background/2.png", Texture.class);
        assetManager.load("Background/3.png", Texture.class);
        assetManager.load("Background/4.png", Texture.class);
        assetManager.load("Background/5.png", Texture.class);
        assetManager.load("Background/5.png", Texture.class);
        assetManager.load("Background/6.png", Texture.class);
        assetManager.load("Background/7.png", Texture.class);
        assetManager.load("Background/8.png", Texture.class);
        assetManager.load("Background/9.png", Texture.class);
        assetManager.load("Background/10.png", Texture.class);
        assetManager.load("Background/Cloud (1).png", Texture.class);
        assetManager.load("Background/Cloud (2).png", Texture.class);
        assetManager.load("Background/Cloud (3).png", Texture.class);
        assetManager.load("Background/Cloud (4).png", Texture.class);
        assetManager.load("Enemy/Bat (1).png", Texture.class);
        assetManager.load("Enemy/Bat (2).png", Texture.class);
        assetManager.load("Enemy/Bat (3).png", Texture.class);
        assetManager.load("Enemy/Bat (4).png", Texture.class);
        assetManager.load("Enemy/Platform (1).png", Texture.class);
        assetManager.load("Enemy/Platform (2).png", Texture.class);
        assetManager.load("Enemy/Hammer.png", Texture.class);
        assetManager.load("Enemy/witch.png", Texture.class);
        assetManager.load("Enemy/Ghost.png", Texture.class);
        assetManager.load("Enemy/Pumpkin.png", Texture.class);
        assetManager.load("Enemy/Spike.png", Texture.class);
        assetManager.load("Jack/Jump (1).png", Texture.class);
        assetManager.load("Jack/Jump (2).png", Texture.class);
        assetManager.load("Jack/Jump (3).png", Texture.class);
        assetManager.load("Jack/Jump (4).png", Texture.class);
        assetManager.load("Jack/Dead (1).png", Texture.class);
        assetManager.load("Jack/Dead (2).png", Texture.class);
        assetManager.load("Jack/Dead (3).png", Texture.class);
        assetManager.load("Jack/Dead (4).png", Texture.class);
        assetManager.load("Jack/Dead (5).png", Texture.class);
        assetManager.load("Jack/Dead (6).png", Texture.class);
        assetManager.load("Jack/Dead (7).png", Texture.class);
        assetManager.load("Jack/Dead (8).png", Texture.class);
        assetManager.load("Jack/Fall (1).png", Texture.class);
        assetManager.load("Jack/Fall (2).png", Texture.class);
        assetManager.load("Jack/Fall (3).png", Texture.class);
        assetManager.load("Jack/Fall (4).png", Texture.class);
        assetManager.load("Jack/Idle (1).png", Texture.class);
        assetManager.load("Jack/Idle (2).png", Texture.class);
        assetManager.load("Jack/Idle (3).png", Texture.class);
        assetManager.load("Jack/Idle (4).png", Texture.class);
        assetManager.load("Jack/Launch (1).png", Texture.class);
        assetManager.load("Jack/Launch (2).png", Texture.class);
        assetManager.load("Jack/Launch (3).png", Texture.class);
        assetManager.load("Jack/Launch (4).png", Texture.class);
        assetManager.load("Jack/Launch (5).png", Texture.class);
        assetManager.load("Jack/Launch (6).png", Texture.class);
        assetManager.load("Jack/Launch (7).png", Texture.class);
        assetManager.load("Jack/Launch (8).png", Texture.class);
        assetManager.load("Jack/Launch (9).png", Texture.class);
        assetManager.load("Jack/Lean Left (1).png", Texture.class);
        assetManager.load("Jack/Lean Left (2).png", Texture.class);
        assetManager.load("Jack/Lean Left (3).png", Texture.class);
        assetManager.load("Jack/Lean Left (4).png", Texture.class);
        assetManager.load("Jack/Lean Left (5).png", Texture.class);
        assetManager.load("Jack/Lean Right (1).png", Texture.class);
        assetManager.load("Jack/Lean Right (2).png", Texture.class);
        assetManager.load("Jack/Lean Right (3).png", Texture.class);
        assetManager.load("Jack/Lean Right (4).png", Texture.class);
        assetManager.load("Jack/Lean Right (5).png", Texture.class);
        assetManager.load("Jack/Magnet/Jump (1).png", Texture.class);
        assetManager.load("Jack/Magnet/Jump (2).png", Texture.class);
        assetManager.load("Jack/Magnet/Jump (3).png", Texture.class);
        assetManager.load("Jack/Magnet/Jump (4).png", Texture.class);
        assetManager.load("Jack/Armored/Jump (1).png", Texture.class);
        assetManager.load("Jack/Armored/Jump (2).png", Texture.class);
        assetManager.load("Jack/Armored/Jump (3).png", Texture.class);
        assetManager.load("Jack/Armored/Jump (4).png", Texture.class);
        assetManager.load("Jack/Copter/Copter Normal (1).png", Texture.class);
        assetManager.load("Jack/Copter/Copter Normal (2).png", Texture.class);
        assetManager.load("Jack/Copter/Copter Normal (3).png", Texture.class);
        assetManager.load("Jack/Copter/Copter Normal (4).png", Texture.class);
        assetManager.load("Jack/Copter/Copter Normal (5).png", Texture.class);
        assetManager.load("PowerUp/Shield.png", Texture.class);
        assetManager.load("PowerUp/Magnet.png", Texture.class);
        assetManager.load("PowerUp/Copter.png", Texture.class);
        assetManager.load("PowerUp/Boost (1).png", Texture.class);
        assetManager.load("PowerUp/Boost (2).png", Texture.class);
        assetManager.load("PowerUp/Boost (3).png", Texture.class);
        assetManager.load("PowerUp/PanelUpgrade (1).png", Texture.class);
        assetManager.load("PowerUp/PanelUpgrade (2).png", Texture.class);
        assetManager.load("PowerUp/ShieldCircle (1).png", Texture.class);
        assetManager.load("PowerUp/ShieldCircle (2).png", Texture.class);
        assetManager.load("Buttons/Pause (1).png", Texture.class);
        assetManager.load("Buttons/Pause (2).png", Texture.class);
        assetManager.load("Explosion/Collectibles/Collect (1).png", Texture.class);
        assetManager.load("Explosion/Collectibles/Collect (2).png", Texture.class);
        assetManager.load("Explosion/Collectibles/Collect (3).png", Texture.class);
        assetManager.load("Explosion/Collectibles/Collect (4).png", Texture.class);
        assetManager.load("Explosion/Collectibles/Collect (5).png", Texture.class);
        assetManager.load("Explosion/Collectibles/Collect (6).png", Texture.class);
        assetManager.load("Explosion/Collectibles/Collect (7).png", Texture.class);
        assetManager.load("Explosion/Collectibles/Collect (8).png", Texture.class);
        assetManager.load("Explosion/Smoke/Smoke (1).png", Texture.class);
        assetManager.load("Explosion/Smoke/Smoke (2).png", Texture.class);
        assetManager.load("Explosion/Smoke/Smoke (3).png", Texture.class);
        assetManager.load("Explosion/Smoke/Smoke (4).png", Texture.class);
        assetManager.load("Explosion/Smoke/Smoke (5).png", Texture.class);
        assetManager.load("Explosion/Smoke/Smoke (6).png", Texture.class);
        assetManager.load("Explosion/Smoke/Smoke (7).png", Texture.class);
        assetManager.load("Explosion/Smoke/Smoke (8).png", Texture.class);
        assetManager.load("Pause/Exit (1).png", Texture.class);
        assetManager.load("Pause/Exit (2).png", Texture.class);
        assetManager.load("Pause/Music (1).png", Texture.class);
        assetManager.load("Pause/Music (3).png", Texture.class);
        assetManager.load("Pause/Pause Window.png", Texture.class);
        assetManager.load("Pause/Replay (1).png", Texture.class);
        assetManager.load("Pause/Replay (2).png", Texture.class);
        assetManager.load("Pause/Resume (1).png", Texture.class);
        assetManager.load("Pause/Resume (2).png", Texture.class);
        assetManager.load("Pause/Sound (1).png", Texture.class);
        assetManager.load("Pause/Sound (3).png", Texture.class);
        assetManager.load("Jumping Platform/1/Idle.png", Texture.class);
        assetManager.load("Jumping Platform/1/Bounce (1).png", Texture.class);
        assetManager.load("Jumping Platform/1/Bounce (2).png", Texture.class);
        assetManager.load("Jumping Platform/1/Bounce (3).png", Texture.class);
        assetManager.load("Jumping Platform/1/Bounce (4).png", Texture.class);
        assetManager.load("Jumping Platform/1/Bounce (5).png", Texture.class);
        assetManager.load("Jumping Platform/2/Idle.png", Texture.class);
        assetManager.load("Jumping Platform/2/Bounce (1).png", Texture.class);
        assetManager.load("Jumping Platform/2/Bounce (2).png", Texture.class);
        assetManager.load("Jumping Platform/2/Bounce (3).png", Texture.class);
        assetManager.load("Jumping Platform/2/Bounce (4).png", Texture.class);
        assetManager.load("Jumping Platform/2/Bounce (5).png", Texture.class);
        assetManager.load("Jumping Platform/3/Idle.png", Texture.class);
        assetManager.load("Jumping Platform/3/Bounce (1).png", Texture.class);
        assetManager.load("Jumping Platform/3/Bounce (2).png", Texture.class);
        assetManager.load("Jumping Platform/3/Bounce (3).png", Texture.class);
        assetManager.load("Jumping Platform/3/Bounce (4).png", Texture.class);
        assetManager.load("Jumping Platform/3/Bounce (5).png", Texture.class);
        assetManager.load("Health/Hearth.png", Texture.class);
        assetManager.load("Health/LifeBar.png", Texture.class);
        assetManager.load("Health/Life.png", Texture.class);
        assetManager.load("Health/Tombstone.png", Texture.class);


        assetManager.load("Font/text.fnt", BitmapFont.class);


        assetManager.load("Sound/PowerUp.wav", Sound.class);
        assetManager.load("Sound/soundRocket.mp3", Music.class);
        assetManager.load("Sound/Candy (1).wav", Sound.class);
        assetManager.load("Sound/Candy (2).wav", Sound.class);
        assetManager.load("Sound/Candy (3).wav", Sound.class);
        assetManager.load("Sound/Witch_laugh.wav", Sound.class);


    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void dispose() {
        assetManager.dispose();
    }

}
