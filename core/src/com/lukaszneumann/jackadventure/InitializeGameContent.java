package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Lukasz on 2014-12-14.
 */
public class InitializeGameContent {

    AssetManager assetManager;

    public InitializeGameContent() {
        assetManager = new AssetManager();

        assetManager.load("Background/BG.png", Texture.class);
        assetManager.load("Buttons/Play (1).png", Texture.class);
        assetManager.load("Buttons/Play (2).png", Texture.class);
        assetManager.load("Buttons/Shop (1).png", Texture.class);
        assetManager.load("Buttons/Shop (2).png", Texture.class);
        assetManager.load("Buttons/Score (1).png", Texture.class);
        assetManager.load("Text/Title 1.png", Texture.class);
        assetManager.load("Upgrade/Upgrade Window.png", Texture.class);
        assetManager.load("Upgrade/Magnet.png", Texture.class);
        assetManager.load("Upgrade/Copter.png", Texture.class);
        assetManager.load("Upgrade/Shield.png", Texture.class);
        assetManager.load("Upgrade/Buy (1).png", Texture.class);
        assetManager.load("Upgrade/Buy (2).png", Texture.class);
        assetManager.load("Font/text.fnt", BitmapFont.class);
        assetManager.load("Font/text.png", Texture.class);
        assetManager.load("Jack/Rocket (1).png", Texture.class);
        assetManager.load("Jack/Rocket (2).png", Texture.class);
        assetManager.load("Jack/Rocket (3).png", Texture.class);
        assetManager.load("Jack/Rocket (4).png", Texture.class);

        assetManager.load("Sound/GameMusic.mp3", Music.class);
        assetManager.load("Font/text.fnt", BitmapFont.class);



    }


    public AssetManager getAssetManager() {
        return assetManager;
    }


}
