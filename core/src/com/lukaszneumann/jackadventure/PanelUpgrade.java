package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukasz on 2015-01-14.
 */
public class PanelUpgrade {


    private MyGame myGame;
    private WorldGame worldGame;
    private Sprite panelUpgrade;
    private Animation animationPanelUpgrade;
    private Sprite magnet;
    private Sprite copter;
    private Sprite shield;

    private boolean isTouchedPanelUpgrade = false;
    private float stateTimePanelUpgrade = 0;
    private float timeDisplayUpgrade = 3;

    private BitmapFont textCountCopter;
    private BitmapFont textCountMagnet;
    private BitmapFont textCountShield;

    private float alphaMagnet = 0.5f;
    private float alphaCopter = 0.5f;
    private float alphaShield = 0.5f;

    private boolean isTouchedCopter = false;
    private boolean isTouchedMagnet = false;
    private boolean isTouchedShield = false;

    public PanelUpgrade(MyGame myGame, WorldGame worldGame) {

        this.myGame = myGame;
        this.worldGame = worldGame;

        animationPanelUpgrade = new Animation(0.15f, new TextureRegion[]{
                new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "PowerUp/PanelUpgrade (1).png", Texture.class)),
                new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "PowerUp/PanelUpgrade (2).png", Texture.class)),

        });


        panelUpgrade = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "PowerUp/PanelUpgrade (1).png", Texture.class));
        panelUpgrade.setPosition(myGame.WIDTH_SCREEN / 2 - panelUpgrade.getOriginX(), 0);
        panelUpgrade.setAlpha(0.4f);

        shield = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Icon/Shield.png", Texture.class));
        shield.setPosition(myGame.WIDTH_SCREEN / 5.7f, (myGame.HEIGHT_SCREEN / 2) - (2.3f * shield.getHeight()));

        magnet = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Icon/Magnet.png", Texture.class));
        magnet.setPosition(myGame.WIDTH_SCREEN - (myGame.WIDTH_SCREEN / 5.7f) - magnet.getWidth(), (myGame.HEIGHT_SCREEN / 2) - (2.3f * magnet.getHeight()));

        copter = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Icon/Copter.png", Texture.class));
        copter.setPosition(myGame.WIDTH_SCREEN / 2 - copter.getOriginX(), myGame.HEIGHT_SCREEN / 2 - (1.2f * copter.getHeight()));

        textCountCopter = myGame.initializeGame.getFont();
        textCountMagnet = myGame.initializeGame.getFont();
        textCountShield = myGame.initializeGame.getFont();


    }

    public void update(float deltaTime) {

        setTouchedUpgradePanel();
        slowTime(isTouchedPanelUpgrade);

        if (isTouchedPanelUpgrade == true) {
            stateTimePanelUpgrade += deltaTime;

            if (stateTimePanelUpgrade >= timeDisplayUpgrade) {
                stateTimePanelUpgrade = 0;
                isTouchedPanelUpgrade = false;
            }

        } else {
            stateTimePanelUpgrade = 0;
        }


        touchedUpgradeIcon();

        setAlphaUpgrade();

    }

    private void setTouchedUpgradePanel() {

        if (Gdx.input.justTouched() && panelUpgrade.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {

            if (isTouchedPanelUpgrade == false) {
                isTouchedPanelUpgrade = true;

            } else {
                isTouchedPanelUpgrade = false;
            }
        }
    }

    private void slowTime(boolean isTouchedPanelUpgrade) {

        if (isTouchedPanelUpgrade == true) {
            worldGame.getWorld().setGravity(new Vector2(0, -2f));

        } else {
            worldGame.getWorld().setGravity(new Vector2(0, worldGame.getGravity()));
        }

    }

    public void drawPanelUpgrade() {

        panelUpgrade.setTexture(animationPanelUpgrade.getKeyFrame(stateTimePanelUpgrade).getTexture());
        panelUpgrade.draw(myGame.batch);


        if (isTouchedPanelUpgrade == true) {


            magnet.draw(myGame.batch, alphaMagnet);
            copter.draw(myGame.batch, alphaCopter);
            shield.draw(myGame.batch, alphaShield);


            textCountCopter.draw(myGame.batch, Integer.toString(myGame.dataStorage.getCopterPowerUp()), copter.getX() + copter.getOriginX() -
                    textCountCopter.getBounds(Integer.toString(myGame.dataStorage.getCopterPowerUp())).width / 2, copter.getY());

            textCountMagnet.draw(myGame.batch, Integer.toString(myGame.dataStorage.getMagnetPowerUp()), magnet.getX() + magnet.getOriginX() -
                    textCountMagnet.getBounds(Integer.toString(myGame.dataStorage.getMagnetPowerUp())).width / 2, magnet.getY());

            textCountShield.draw(myGame.batch, Integer.toString(myGame.dataStorage.getShieldPowerUp()), shield.getX() + shield.getOriginX() -
                    textCountShield.getBounds(Integer.toString(myGame.dataStorage.getShieldPowerUp())).width / 2, shield.getY());

        }
    }


    private void touchedUpgradeIcon() {

        if (isTouchedPanelUpgrade == true && Gdx.input.justTouched()) {

            if (myGame.dataStorage.getCopterPowerUp() != 0 && copter.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
                isTouchedCopter = true;
                soundClickedOn();
                myGame.soundGame.getUsePowerUp();
                isTouchedPanelUpgrade = false;
                myGame.dataStorage.setCopterPowerUp(myGame.dataStorage.getCopterPowerUp() - 1);

            } else if (myGame.dataStorage.getMagnetPowerUp() != 0 && magnet.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
                isTouchedMagnet = true;
                soundClickedOn();
                myGame.soundGame.getUsePowerUp();
                isTouchedPanelUpgrade = false;
                myGame.dataStorage.setMagnetPowerUp(myGame.dataStorage.getMagnetPowerUp() - 1);

            } else if (myGame.dataStorage.getShieldPowerUp() != 0 && shield.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
                isTouchedShield = true;
                soundClickedOn();
                myGame.soundGame.getUsePowerUp();
                isTouchedPanelUpgrade = false;
                myGame.dataStorage.setShieldPowerUp(myGame.dataStorage.getShieldPowerUp() - 1);

            }
        }
    }

    private void soundClickedOn() {

        if (myGame.isSound) {
            myGame.musicGame.getButtonClicked();
        }
    }

    private void setAlphaUpgrade() {

        if (myGame.dataStorage.getMagnetPowerUp() > 0) {
            alphaMagnet = 1f;

        } else {
            alphaMagnet = 0.5f;
        }

        if (myGame.dataStorage.getCopterPowerUp() > 0) {
            alphaCopter = 1f;

        } else {
            alphaCopter = 0.5f;
        }


        if (myGame.dataStorage.getShieldPowerUp() > 0) {
            alphaShield = 1f;

        } else {
            alphaShield = 0.5f;
        }

    }

    public boolean isTouchedPanelUpgrade() {
        return isTouchedPanelUpgrade;
    }

    public void setTouchedCopter(boolean isTouchedCopter) {
        this.isTouchedCopter = isTouchedCopter;
    }

    public void setTouchedMagnet(boolean isTouchedMagnet) {
        this.isTouchedMagnet = isTouchedMagnet;
    }

    public void setTouchedShield(boolean isTouchedShield) {
        this.isTouchedShield = isTouchedShield;
    }

    public boolean isTouchedCopter() {
        return isTouchedCopter;
    }

    public boolean isTouchedMagnet() {
        return isTouchedMagnet;
    }

    public boolean isTouchedShield() {
        return isTouchedShield;
    }
}
