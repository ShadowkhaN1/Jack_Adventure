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
    private Sprite panelUpgrade;
    private Animation animationPanelUpgrade;
    private Sprite magnet;
    private Sprite copter;
    private Sprite shield;

    private boolean isTouchedPanelUpgrade = false;
    private float stateTime = 0;
    private float stateTimePanelUpgrade = 0;
    private float timeDisplayUpgrade = 3;

    private BitmapFont textCountCopter;
    private BitmapFont textCountMagnet;
    private BitmapFont textCountShield;

    public PanelUpgrade(MyGame myGame) {

        this.myGame = myGame;

        animationPanelUpgrade = new Animation(0.15f, new TextureRegion[]{
                new TextureRegion(myGame.content.getAssetManager().get("PowerUp/PanelUpgrade (1).png", Texture.class)),
                new TextureRegion(myGame.content.getAssetManager().get("PowerUp/PanelUpgrade (2).png", Texture.class)),

        });


        panelUpgrade = new Sprite(myGame.content.getAssetManager().get("PowerUp/PanelUpgrade (1).png", Texture.class));
        panelUpgrade.setPosition(myGame.WIDTH_SCREEN / 2 - panelUpgrade.getOriginX(), 0);
        panelUpgrade.setAlpha(0.4f);

        shield = new Icon(myGame.content.getAssetManager().get("Icon/Shield.png", Texture.class));
        shield.setPosition(myGame.WIDTH_SCREEN / 5.7f, (myGame.HEIGHT_SCREEN / 2) - (2.3f * shield.getHeight()));

        magnet = new Icon(myGame.content.getAssetManager().get("Icon/Magnet.png", Texture.class));
        magnet.setPosition(myGame.WIDTH_SCREEN - (myGame.WIDTH_SCREEN / 5.7f) - magnet.getWidth(), (myGame.HEIGHT_SCREEN / 2) - (2.3f * magnet.getHeight()));

        copter = new Icon(myGame.content.getAssetManager().get("Icon/Copter.png", Texture.class));
        copter.setPosition(myGame.WIDTH_SCREEN / 2 - copter.getOriginX(), myGame.HEIGHT_SCREEN / 2 - (1.2f * copter.getHeight()));

        textCountCopter = myGame.initializeGame.assetManager.get("Font/text.fnt", BitmapFont.class);
        textCountMagnet = myGame.initializeGame.assetManager.get("Font/text.fnt", BitmapFont.class);
        textCountShield = myGame.initializeGame.assetManager.get("Font/text.fnt", BitmapFont.class);


    }

    public void update(float deltaTime) {

        stateTime += deltaTime;

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
            myGame.worldGame.getWorld().setGravity(new Vector2(0, -0.1f));

        } else {
            myGame.worldGame.getWorld().setGravity(new Vector2(0, myGame.worldGame.getGravity()));
        }

    }

    public void drawPanelUpgrade() {

        panelUpgrade.setTexture(animationPanelUpgrade.getKeyFrame(stateTimePanelUpgrade).getTexture());
        panelUpgrade.draw(myGame.batch);


        if (isTouchedPanelUpgrade == true) {

            magnet.draw(myGame.batch);
            copter.draw(myGame.batch);
            shield.draw(myGame.batch);


            textCountCopter.draw(myGame.batch, "0", copter.getX() + copter.getOriginX() -
                    textCountCopter.getBounds("0").width / 2, copter.getY());

            textCountMagnet.draw(myGame.batch, "0", magnet.getX() + magnet.getOriginX() -
                    textCountMagnet.getBounds("0").width / 2, magnet.getY());

            textCountShield.draw(myGame.batch, "0", shield.getX() + shield.getOriginX() -
                    textCountShield.getBounds("0").width / 2, shield.getY());


        }
    }

}
