package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

/**
 * Created by Lukasz on 2014-12-12.
 */
public class UpgradeScreen implements Screen {

    private MyGame myGame;

    private Sprite upgradeWindow;
    private Sprite magnet;
    private Sprite copter;
    private Sprite shield;
    private ArrayList<Sprite> buttonBuy1;
    private ArrayList<Sprite> buttonBuy2;
    private BitmapFont textMagnet;
    private BitmapFont textShield;
    private BitmapFont textCopter;
    private ArrayList<BitmapFont> textPriceUpgrade;
    private Integer priceUpgrade = 100;
    private Integer collectedCandy = 1000;
    private BitmapFont textCollectedCandy;
    private Texture fontTexture;


    public UpgradeScreen(MyGame myGame) {
        this.myGame = myGame;
        show();

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            System.out.println("Key pressed");
            myGame.setScreen(new MenuScreen(myGame));
        }

        myGame.batch.begin();

        myGame.batch.draw(upgradeWindow, upgradeWindow.getX(), upgradeWindow.getY(), upgradeWindow.getWidth(), upgradeWindow.getHeight());
        myGame.batch.draw(magnet, magnet.getX(), magnet.getY(), magnet.getWidth(), magnet.getHeight());
        myGame.batch.draw(copter, copter.getX(), copter.getY(), copter.getWidth(), copter.getHeight());
        myGame.batch.draw(shield, shield.getX(), shield.getY(), shield.getWidth(), shield.getHeight());


        for (int i = 0; i < buttonBuy1.size(); i++) {

            if (Gdx.input.isTouched() && buttonBuy1.get(i).getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {

                buttonBuy2.get(i).draw(myGame.batch);

                if (Integer.parseInt(myGame.fileScoreCandy.readString()) >= priceUpgrade && Gdx.input.justTouched()) {
                    myGame.fileScoreCandy.writeString(Integer.toString(Integer.parseInt(myGame.fileScoreCandy.readString()) - priceUpgrade), false);
                }

            } else {
                buttonBuy1.get(i).draw(myGame.batch);
            }
        }


        textMagnet.draw(myGame.batch, "Magnet", (buttonBuy1.get(0).getX() + buttonBuy1.get(0).getOriginX() - textMagnet.getBounds("Magnet").width / 2),
                (buttonBuy1.get(0).getY() + 1.85f * buttonBuy1.get(0).getHeight() - textMagnet.getBounds("Magnet").height / 2));

        textCopter.draw(myGame.batch, "Copter", buttonBuy1.get(1).getX() + buttonBuy1.get(1).getOriginX() - textMagnet.getBounds("Copter").width / 2,
                buttonBuy1.get(1).getY() + 1.85f * buttonBuy1.get(1).getHeight() - textMagnet.getBounds("Copter").height / 2);

        textShield.draw(myGame.batch, "Shield", buttonBuy1.get(2).getX() + buttonBuy1.get(2).getOriginX() - textMagnet.getBounds("Shield").width / 2,
                buttonBuy1.get(2).getY() + 1.85f * buttonBuy1.get(2).getHeight() - textMagnet.getBounds("Shield").height / 2);


        for (int i = 0; i < textPriceUpgrade.size(); i++) {
            textPriceUpgrade.get(i).draw(myGame.batch, priceUpgrade.toString(), buttonBuy1.get(i).getX() + (1.2f * buttonBuy1.get(i).getOriginX()) - textPriceUpgrade.get(i).getBounds(priceUpgrade.toString()).width / 2,
                    buttonBuy1.get(i).getY() + buttonBuy1.get(i).getOriginY() + textPriceUpgrade.get(i).getBounds(priceUpgrade.toString()).height / 2);
        }

        textCollectedCandy.draw(myGame.batch, myGame.fileScoreCandy.readString(), (Gdx.graphics.getWidth() / 2) - (textCollectedCandy.getBounds(collectedCandy.toString()).width / 2),
                upgradeWindow.getY() + (upgradeWindow.getHeight() / 12.5f));

        myGame.batch.end();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

        upgradeWindow = new Sprite(myGame.initializeGame.getAssetManager().get("Upgrade/Upgrade Window.png", Texture.class));
        upgradeWindow.setPosition((myGame.WIDTH_SCREEN / 2) - (upgradeWindow.getWidth() / 2), (myGame.HEIGHT_SCREEN / 2) - (upgradeWindow.getHeight() / 2));

        magnet = new Sprite(myGame.initializeGame.getAssetManager().get("Upgrade/Magnet.png", Texture.class));
        magnet.setPosition((myGame.WIDTH_SCREEN / 2) - (magnet.getWidth() / 2), upgradeWindow.getY() + (upgradeWindow.getHeight() / 6));

        copter = new Sprite(myGame.initializeGame.getAssetManager().get("Upgrade/Copter.png", Texture.class));
        copter.setPosition(myGame.WIDTH_SCREEN / 2 - copter.getWidth() / 2, upgradeWindow.getY() + 2.2f * (upgradeWindow.getHeight() / 6));

        shield = new Sprite(myGame.initializeGame.getAssetManager().get("Upgrade/Shield.png", Texture.class));
        shield.setPosition((myGame.WIDTH_SCREEN / 2) - (shield.getWidth() / 2), upgradeWindow.getY() + 3.4f * (upgradeWindow.getHeight() / 6));

        buttonBuy1 = new ArrayList<Sprite>(3);
        buttonBuy1.add(new Sprite(myGame.initializeGame.getAssetManager().get("Upgrade/Buy (1).png", Texture.class)));
        buttonBuy1.get(0).setPosition(magnet.getX() + (magnet.getWidth() / 2.6f), magnet.getY());
        buttonBuy1.get(0).setOrigin(buttonBuy1.get(0).getWidth() / 2, buttonBuy1.get(0).getHeight() / 2);
        buttonBuy1.add(new Sprite(myGame.initializeGame.getAssetManager().get("Upgrade/Buy (1).png", Texture.class)));
        buttonBuy1.get(1).setPosition(copter.getX() + (copter.getWidth() / 2.6f), copter.getY());
        buttonBuy1.get(1).setOrigin(buttonBuy1.get(1).getWidth() / 2, buttonBuy1.get(1).getHeight() / 2);
        buttonBuy1.add(new Sprite(myGame.initializeGame.getAssetManager().get("Upgrade/Buy (1).png", Texture.class)));
        buttonBuy1.get(2).setPosition(shield.getX() + (shield.getWidth() / 2.6f), shield.getY());
        buttonBuy1.get(2).setOrigin(buttonBuy1.get(2).getWidth() / 2, buttonBuy1.get(2).getHeight() / 2);


        buttonBuy2 = new ArrayList<Sprite>(3);
        buttonBuy2.add(new Sprite(myGame.initializeGame.getAssetManager().get("Upgrade/Buy (2).png", Texture.class)));
        buttonBuy2.get(0).setPosition(magnet.getX() + (magnet.getWidth() / 2.6f), magnet.getY());
        buttonBuy2.add(new Sprite(myGame.initializeGame.getAssetManager().get("Upgrade/Buy (2).png", Texture.class)));
        buttonBuy2.get(1).setPosition(copter.getX() + (copter.getWidth() / 2.6f), copter.getY());
        buttonBuy2.add(new Sprite(myGame.initializeGame.getAssetManager().get("Upgrade/Buy (2).png", Texture.class)));
        buttonBuy2.get(2).setPosition(shield.getX() + (shield.getWidth() / 2.6f), shield.getY());

        fontTexture = new Texture(Gdx.files.internal("Font/text.png"), true);
        fontTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        textMagnet = new BitmapFont(Gdx.files.internal("Font/text.fnt"), false);


        textCopter = myGame.getInitializeGame().getAssetManager().get("Font/text.fnt", BitmapFont.class);

        textShield = myGame.getInitializeGame().getAssetManager().get("Font/text.fnt", BitmapFont.class);

        textCollectedCandy = myGame.getInitializeGame().getAssetManager().get("Font/text.fnt", BitmapFont.class);

        textPriceUpgrade = new ArrayList<BitmapFont>(3);
        textPriceUpgrade.add(myGame.getInitializeGame().getAssetManager().get("Font/text.fnt", BitmapFont.class));
        textPriceUpgrade.add(myGame.getInitializeGame().getAssetManager().get("Font/text.fnt", BitmapFont.class));
        textPriceUpgrade.add(myGame.getInitializeGame().getAssetManager().get("Font/text.fnt", BitmapFont.class));


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
}
