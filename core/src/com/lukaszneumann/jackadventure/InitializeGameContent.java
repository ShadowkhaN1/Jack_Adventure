package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Lukasz on 2014-12-14.
 */
public class InitializeGameContent {

    AssetManager assetManager;
    private BitmapFont font;
    private String usesDpi;


    public InitializeGameContent(String usesDpi) {
        this.usesDpi = usesDpi;
        assetManager = new AssetManager();

        assetManager.load(usesDpi + "/" + "Background/BG.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Buttons/Play (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Buttons/Play (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Buttons/Shop (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Buttons/Shop (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Buttons/Score (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Buttons/Score (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Text/Title 1.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Upgrade/Upgrade Window.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Upgrade/Magnet.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Upgrade/Copter.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Upgrade/Shield.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Upgrade/Buy (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Upgrade/Buy (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Font/text.fnt", BitmapFont.class);
        assetManager.load(usesDpi + "/" + "Font/text.png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Rocket (1).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Rocket (2).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Rocket (3).png", Texture.class);
        assetManager.load(usesDpi + "/" + "Jack/Rocket (4).png", Texture.class);

        assetManager.load("Data/GameMusic.mp3", Music.class);
        assetManager.load("Data/ButtonClick.wav", Sound.class);
        assetManager.load("Data/ButtonBackClicked.wav", Sound.class);


        createFont();

    }

    private void createFont() {
        font = new BitmapFont(Gdx.files.internal(usesDpi + "/" + "Font/text.fnt"));
    }

    public BitmapFont getFont() {
        return font;
    }


    public AssetManager getAssetManager() {
        return assetManager;
    }


}
