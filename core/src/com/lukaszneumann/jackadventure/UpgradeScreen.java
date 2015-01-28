package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by Lukasz on 2014-12-12.
 */
public class UpgradeScreen implements Screen, InputProcessor {

    private MyGame myGame;
    private MenuScreen menuScreen;
    private Sprite upgradeWindow;
    private Sprite magnet;
    private Sprite copter;
    private Sprite shield;
    private ArrayList<Sprite> buttonBuy1 = new ArrayList<Sprite>(3);
    private ArrayList<Sprite> buttonBuy2 = new ArrayList<Sprite>(3);
    private BitmapFont textMagnet;
    private BitmapFont textShield;
    private BitmapFont textCopter;
    private ArrayList<BitmapFont> textPriceUpgrade = new ArrayList<BitmapFont>(3);
    private Integer priceUpgrade = 50;
    private BitmapFont textCollectedCandy;
    private Texture fontTexture;
    private boolean[] isButtonBuyPressedDown = {false, false, false};
    private boolean[] isButtonBuyPressedUp = {false, false, false};


    public UpgradeScreen(MyGame myGame, MenuScreen menuScreen) {

        this.myGame = myGame;
        this.menuScreen = menuScreen;
        show();

    }

    @Override
    public void render(float delta) {

        Gdx.input.setInputProcessor(this);


        myGame.batch.draw(upgradeWindow, upgradeWindow.getX(), upgradeWindow.getY(), upgradeWindow.getWidth(), upgradeWindow.getHeight());
        myGame.batch.draw(magnet, magnet.getX(), magnet.getY(), magnet.getWidth(), magnet.getHeight());
        myGame.batch.draw(copter, copter.getX(), copter.getY(), copter.getWidth(), copter.getHeight());
        myGame.batch.draw(shield, shield.getX(), shield.getY(), shield.getWidth(), shield.getHeight());


        if (Gdx.input.isTouched()) {
            touchDown((int) myGame.touch.calcX(Gdx.input.getX()), (int) myGame.touch.calcY(Gdx.input.getY()), 0, 0);
        }


        drawButtonBuy();


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

        textCollectedCandy.draw(myGame.batch, Integer.toString(myGame.dataStorage.getCandyScore()), (Gdx.graphics.getWidth() / 2) - (textCollectedCandy.getBounds(Integer.toString(myGame.dataStorage.getCandyScore())).width / 2),
                upgradeWindow.getY() + (upgradeWindow.getHeight() / 12.5f));


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

        upgradeWindow = new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Upgrade/Upgrade Window.png", Texture.class));
        upgradeWindow.setPosition((myGame.WIDTH_SCREEN / 2) - (upgradeWindow.getWidth() / 2), (myGame.HEIGHT_SCREEN / 2) - (upgradeWindow.getHeight() / 2));

        magnet = new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Upgrade/Magnet.png", Texture.class));
        magnet.setPosition((myGame.WIDTH_SCREEN / 2) - (magnet.getWidth() / 2), upgradeWindow.getY() + (upgradeWindow.getHeight() / 6));

        copter = new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Upgrade/Copter.png", Texture.class));
        copter.setPosition(myGame.WIDTH_SCREEN / 2 - copter.getWidth() / 2, upgradeWindow.getY() + 2.2f * (upgradeWindow.getHeight() / 6));

        shield = new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Upgrade/Shield.png", Texture.class));
        shield.setPosition((myGame.WIDTH_SCREEN / 2) - (shield.getWidth() / 2), upgradeWindow.getY() + 3.4f * (upgradeWindow.getHeight() / 6));


        buttonBuy1.add(new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Upgrade/Buy (1).png", Texture.class)));
        buttonBuy1.get(0).setPosition(magnet.getX() + (magnet.getWidth() / 2.6f), magnet.getY());
        buttonBuy1.get(0).setOrigin(buttonBuy1.get(0).getWidth() / 2, buttonBuy1.get(0).getHeight() / 2);
        buttonBuy1.add(new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Upgrade/Buy (1).png", Texture.class)));
        buttonBuy1.get(1).setPosition(copter.getX() + (copter.getWidth() / 2.6f), copter.getY());
        buttonBuy1.get(1).setOrigin(buttonBuy1.get(1).getWidth() / 2, buttonBuy1.get(1).getHeight() / 2);
        buttonBuy1.add(new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Upgrade/Buy (1).png", Texture.class)));
        buttonBuy1.get(2).setPosition(shield.getX() + (shield.getWidth() / 2.6f), shield.getY());
        buttonBuy1.get(2).setOrigin(buttonBuy1.get(2).getWidth() / 2, buttonBuy1.get(2).getHeight() / 2);


        buttonBuy2.add(new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Upgrade/Buy (2).png", Texture.class)));
        buttonBuy2.get(0).setPosition(magnet.getX() + (magnet.getWidth() / 2.6f), magnet.getY());
        buttonBuy2.add(new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Upgrade/Buy (2).png", Texture.class)));
        buttonBuy2.get(1).setPosition(copter.getX() + (copter.getWidth() / 2.6f), copter.getY());
        buttonBuy2.add(new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Upgrade/Buy (2).png", Texture.class)));
        buttonBuy2.get(2).setPosition(shield.getX() + (shield.getWidth() / 2.6f), shield.getY());


        textMagnet = myGame.getInitializeGame().getFont();


        textCopter = myGame.getInitializeGame().getFont();

        textShield = myGame.getInitializeGame().getFont();

        textCollectedCandy = myGame.getInitializeGame().getFont();


        textPriceUpgrade.add(myGame.getInitializeGame().getFont());
        textPriceUpgrade.add(myGame.getInitializeGame().getFont());
        textPriceUpgrade.add(myGame.getInitializeGame().getFont());


    }


    private void drawButtonBuy() {

        for (int i = 0; i < buttonBuy1.size(); i++) {

            if (!isButtonBuyPressedDown[i]) {
                buttonBuy1.get(i).draw(myGame.batch);

            } else {
                buttonBuy2.get(i).draw(myGame.batch);
            }


            if (isButtonBuyPressedUp[i] == true) {

                isButtonBuyPressedUp[i] = false;
                myGame.musicGame.getButtonClicked();

                if (priceUpgrade <= myGame.dataStorage.getCandyScore()) {

                    setValueOfUpgrade(i);

//            subtract the value of candy from a file if button buy is pressed
                    myGame.dataStorage.setCandySCore(myGame.dataStorage.getCandyScore() - priceUpgrade);
                    isButtonBuyPressedUp[i] = false;
                }
            }
        }
    }


    private void setValueOfUpgrade(int i) {

        switch (i) {
            case 0:
                myGame.dataStorage.setMagnetPowerUp(myGame.dataStorage.getMagnetPowerUp() + 1);
                break;
            case 1:
                myGame.dataStorage.setCopterPowerUp(myGame.dataStorage.getCopterPowerUp() + 1);
                break;
            case 2:
                myGame.dataStorage.setShieldPowerUp(myGame.dataStorage.getShieldPowerUp() + 1);
                break;
        }

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

//        textCollectedCandy.dispose();
//        textCopter.dispose();
//        textMagnet.dispose();
//        textCopter.dispose();
//        textShield.dispose();
//        fontTexture.dispose();
//
//        for (BitmapFont bitmapFont : textPriceUpgrade) {
//            bitmapFont.dispose();
//        }
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {

            myGame.musicGame.getButtonBackClicked();
            menuScreen.setUpgradeScreen(false);
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        for (int i = 0; i < buttonBuy1.size(); i++) {

            if (buttonBuy1.get(i).getBoundingRectangle().contains(screenX, screenY)) {

                isButtonBuyPressedDown[i] = true;

            } else {
                isButtonBuyPressedDown[i] = false;
            }
        }


        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        Vector3 touchPosition = new Vector3(screenX, screenY, 0);
        myGame.camera.unproject(touchPosition);

        for (int i = 0; i < buttonBuy1.size(); i++) {

            if (buttonBuy1.get(i).getBoundingRectangle().contains(touchPosition.x, touchPosition.y)) {
                isButtonBuyPressedDown[i] = false;
                isButtonBuyPressedUp[i] = true;

            } else {
                isButtonBuyPressedDown[i] = false;
                isButtonBuyPressedUp[i] = false;
            }
        }

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
