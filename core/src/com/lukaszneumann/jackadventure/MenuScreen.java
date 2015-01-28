package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Lukasz on 2014-12-11.
 */
public class MenuScreen implements Screen, InputProcessor {

    private MyGame myGame;
    private UpgradeScreen upgradeScreen;
    private Sprite background;
    private Sprite buttonPlayNotPressed;
    private Sprite buttonPlayPressed;
    private Sprite buttonShopNotPressed;
    private Sprite buttonShopPressed;
    private Sprite buttonHighScoreNotPressed;
    private Sprite buttonHighScorePressed;
    private Sprite title;
    private float stateTime = 0;
    private Animation animationJack;
    private TextureRegion[] texturesRegionJack;
    private float realWidthJack = 0;
    private float realHeightJack = 0;
    private float frameDuration = 0.15f;
    private WorldGame worldGame;
    private Body jackBody;
    private Body boundsScreenBody;
    private boolean isButtonPlayPressedDown = false;
    private boolean isButtonPlayPressedUp = false;
    private boolean isButtonShopDown = false;
    private boolean isButtonShopUp = false;
    private boolean isButtonHighScorePressedDown = false;
    private boolean isButtonHighScorePressedUp = false;

    private boolean isUpgradeScreen = false;

    private Sprite jack;
    private float alpha = 1;


    public MenuScreen(MyGame myGame) {
        this.myGame = myGame;
        show();


    }

    @Override
    public void render(float delta) {


        stateTime += delta;


        myGame.batch.begin();


        // myGame.batch.draw(background, 0, 0, myGame.WIDTH_SCREEN, myGame.HEIGHT_SCREEN);

        // myGame.batch.draw(renderJack(stateTimeAnimation), jackBody.getPosition().x * WorldGame.METERS_TO_PIXELS - realWidthJack / 2,
        // jackBody.getPosition().y * WorldGame.METERS_TO_PIXELS - realHeightJack / 2, realWidthJack, realHeightJack);

        background.draw(myGame.batch, alpha);

        jack.setTexture(renderJack(stateTime).getTexture());
        jack.setPosition(jackBody.getPosition().x * WorldGame.METERS_TO_PIXELS - realWidthJack / 2, jackBody.getPosition().y * WorldGame.METERS_TO_PIXELS - realHeightJack / 2);
        jack.draw(myGame.batch, alpha);


        if (!isUpgradeScreen) {

            Gdx.input.setInputProcessor(this);
            alpha = 1f;

            if (Gdx.input.isTouched()) {
                touchDown((int) myGame.touch.calcX(Gdx.input.getX()), (int) myGame.touch.calcY(Gdx.input.getY()), 0, 0);
            }

            drawButtonPlay();
            drawButtonShop();
            drawButtonHighScore();
            myGame.batch.draw(title, title.getX(), title.getY(), title.getWidth(), title.getHeight());


        } else {
            alpha = 0.5f;
            upgradeScreen.render(delta);
        }

        worldGame.getWorld().step(1 / 60f, 8, 3);

        myGame.batch.end();

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {


        worldGame = new WorldGame(new Vector2(0, -9.8f), true);


        upgradeScreen = new UpgradeScreen(myGame, this);

        background = new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/BG.png", Texture.class));
        background.setSize(myGame.WIDTH_SCREEN, myGame.HEIGHT_SCREEN);
        background.setPosition(0, 0);

        buttonPlayNotPressed = new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Buttons/Play (1).png", Texture.class));
        buttonPlayNotPressed.setPosition((myGame.WIDTH_SCREEN / 2 - buttonPlayNotPressed.getWidth() / 2), (myGame.HEIGHT_SCREEN / 2 - (3 * buttonPlayNotPressed.getHeight() / 2)));
        buttonPlayNotPressed.setBounds(buttonPlayNotPressed.getX(), buttonPlayNotPressed.getY(), buttonPlayNotPressed.getWidth(), buttonPlayNotPressed.getHeight());

        buttonPlayPressed = new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Buttons/Play (2).png", Texture.class));
        buttonPlayPressed.setPosition((myGame.WIDTH_SCREEN / 2) - buttonPlayNotPressed.getWidth() / 2, (myGame.HEIGHT_SCREEN / 2) - (3 * buttonPlayNotPressed.getHeight() / 2));

        buttonShopNotPressed = new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Buttons/Shop (1).png", Texture.class));
        buttonShopNotPressed.setPosition(buttonPlayNotPressed.getX() - (buttonShopNotPressed.getWidth()),
                buttonPlayNotPressed.getY() - (buttonShopNotPressed.getHeight()));
        buttonShopNotPressed.setBounds(buttonShopNotPressed.getX(), buttonShopNotPressed.getY(), buttonShopNotPressed.getWidth(), buttonShopNotPressed.getHeight());

        buttonShopPressed = new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Buttons/Shop (2).png", Texture.class));
        buttonShopPressed.setPosition(buttonPlayNotPressed.getX() - buttonShopNotPressed.getWidth(), buttonPlayNotPressed.getY() - buttonShopNotPressed.getHeight());

        buttonHighScoreNotPressed = new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Buttons/Score (1).png", Texture.class));
        buttonHighScoreNotPressed.setPosition(buttonPlayNotPressed.getX() + buttonHighScoreNotPressed.getWidth(),
                buttonPlayNotPressed.getY() - buttonHighScoreNotPressed.getHeight());
        buttonHighScoreNotPressed.setBounds(buttonHighScoreNotPressed.getX(), buttonHighScoreNotPressed.getY(), buttonHighScoreNotPressed.getWidth(), buttonHighScoreNotPressed.getHeight());

        buttonHighScorePressed = new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Buttons/Score (2).png", Texture.class));
        buttonHighScorePressed.setPosition(buttonPlayPressed.getX() + buttonHighScorePressed.getWidth(),
                buttonPlayPressed.getY() - buttonHighScoreNotPressed.getHeight());
        buttonHighScorePressed.setBounds(buttonHighScorePressed.getX(), buttonHighScorePressed.getY(), buttonHighScorePressed.getWidth(), buttonHighScorePressed.getHeight());

        title = new Sprite(myGame.initializeGame.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Text/Title 1.png", Texture.class));
        title.setPosition((myGame.WIDTH_SCREEN / 2) - title.getWidth() / 2, (myGame.HEIGHT_SCREEN / 2) + buttonPlayNotPressed.getHeight() / 2);


        texturesRegionJack = new TextureRegion[4];
        texturesRegionJack[0] = new TextureRegion(myGame.getInitializeGame().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Rocket (1).png", Texture.class));
        texturesRegionJack[1] = new TextureRegion(myGame.getInitializeGame().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Rocket (2).png", Texture.class));
        texturesRegionJack[2] = new TextureRegion(myGame.getInitializeGame().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Rocket (3).png", Texture.class));
        texturesRegionJack[3] = new TextureRegion(myGame.getInitializeGame().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Rocket (4).png", Texture.class));

        realWidthJack = texturesRegionJack[0].getRegionWidth();
        realHeightJack = texturesRegionJack[0].getRegionHeight();

        animationJack = new Animation(frameDuration, texturesRegionJack);
        animationJack.setPlayMode(Animation.PlayMode.LOOP);

        jack = new Sprite(texturesRegionJack[0].getTexture());

        createPhysicsJackBody();
        createPhysicsBoundsScreen();

    }

    private TextureRegion renderJack(float stateTime) {
        return animationJack.getKeyFrame(stateTime);
    }

    private void createPhysicsJackBody() {


        PolygonShape jackShape = new PolygonShape();
        jackShape.set(new Vector2[]{
                new Vector2(0, -realHeightJack / 2 * WorldGame.PIXELS_TO_METERS),
                new Vector2(realWidthJack / 2 * WorldGame.PIXELS_TO_METERS, realHeightJack / 2 * WorldGame.PIXELS_TO_METERS),
                new Vector2(-realWidthJack / 2 * WorldGame.PIXELS_TO_METERS, realHeightJack / 2 * WorldGame.PIXELS_TO_METERS),
        });


        FixtureDef fixtureDefRocket = new FixtureDef();
        fixtureDefRocket.shape = jackShape;
        fixtureDefRocket.density = 1f;
        fixtureDefRocket.friction = 0;
        fixtureDefRocket.restitution = 1f;
        fixtureDefRocket.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);
        fixtureDefRocket.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);


        BodyDef bodyDefRocket = new BodyDef();
        bodyDefRocket.type = BodyDef.BodyType.DynamicBody;
        bodyDefRocket.position.x = MathUtils.random(myGame.WIDTH_SCREEN * WorldGame.PIXELS_TO_METERS);
        bodyDefRocket.position.y = MathUtils.random(myGame.HEIGHT_SCREEN * WorldGame.PIXELS_TO_METERS);


        jackBody = worldGame.getWorld().createBody(bodyDefRocket);
        jackBody.setLinearVelocity((realWidthJack / 3 * WorldGame.PIXELS_TO_METERS) * 7, (realHeightJack / 3 * WorldGame.PIXELS_TO_METERS) * 7);
        jackBody.setGravityScale(0);
        jackBody.createFixture(fixtureDefRocket);

        jackShape.dispose();

    }


    private void createPhysicsBoundsScreen() {

        ChainShape chainShape = new ChainShape();
        chainShape.createLoop(new Vector2[]{
                new Vector2((-myGame.WIDTH_SCREEN * WorldGame.PIXELS_TO_METERS) / 6, 0),
                new Vector2(1.33f * myGame.WIDTH_SCREEN * WorldGame.PIXELS_TO_METERS, 0),
                new Vector2(1.33f * myGame.WIDTH_SCREEN * WorldGame.PIXELS_TO_METERS, 1.16f * myGame.HEIGHT_SCREEN * WorldGame.PIXELS_TO_METERS),
                new Vector2(1.33f * -myGame.WIDTH_SCREEN * WorldGame.PIXELS_TO_METERS, 1.16f * myGame.HEIGHT_SCREEN * WorldGame.PIXELS_TO_METERS),
        });

        FixtureDef fixtureDefBounds = new FixtureDef();
        fixtureDefBounds.shape = chainShape;
        fixtureDefBounds.restitution = 1f;
        fixtureDefBounds.friction = 0f;
        fixtureDefBounds.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);
        fixtureDefBounds.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);


        BodyDef bodyDefBounds = new BodyDef();
        bodyDefBounds.type = BodyDef.BodyType.StaticBody;


        boundsScreenBody = worldGame.getWorld().createBody(bodyDefBounds);
        boundsScreenBody.createFixture(fixtureDefBounds);


        chainShape.dispose();
    }

    private void drawButtonPlay() {

        if (!isButtonPlayPressedDown) {
            myGame.batch.draw(buttonPlayNotPressed, buttonPlayNotPressed.getX(), buttonPlayNotPressed.getY(),
                    buttonPlayNotPressed.getWidth(), buttonPlayNotPressed.getHeight());
        } else {
            myGame.batch.draw(buttonPlayPressed, buttonPlayPressed.getX(), buttonPlayPressed.getY(),
                    buttonPlayPressed.getWidth(), buttonPlayPressed.getHeight());
        }

        if (isButtonPlayPressedUp) {
            isButtonPlayPressedUp = false;
            playSoundButtonClicked();
            removeBodyFromWorld();
            myGame.setScreen(new LoadingScreen(myGame));
        }
    }

    private void drawButtonShop() {

        if (!isButtonShopDown) {
            myGame.batch.draw(buttonShopNotPressed, buttonShopNotPressed.getX(), buttonShopNotPressed.getY(), buttonShopNotPressed.getWidth(), buttonShopNotPressed.getHeight());

        } else {
            myGame.batch.draw(buttonShopPressed, buttonShopPressed.getX(), buttonShopPressed.getY(), buttonShopPressed.getWidth(), buttonShopPressed.getHeight());
        }

        if (isButtonShopUp == true) {

            isUpgradeScreen = true;
            isButtonShopUp = false;
            playSoundButtonClicked();
        }
    }

    private void drawButtonHighScore() {

        if (!isButtonHighScorePressedDown) {
            myGame.batch.draw(buttonHighScoreNotPressed, buttonHighScoreNotPressed.getX(), buttonHighScoreNotPressed.getY(), buttonHighScoreNotPressed.getWidth(),
                    buttonHighScoreNotPressed.getHeight());

        } else {
            myGame.batch.draw(buttonHighScorePressed, buttonHighScorePressed.getX(), buttonHighScorePressed.getY(), buttonHighScorePressed.getWidth(), buttonHighScorePressed.getHeight());
        }

        if (isButtonHighScorePressedUp == true) {
            isButtonHighScorePressedUp = false;
            playSoundButtonClicked();
        }
    }

    private void removeBodyFromWorld() {

        worldGame.getWorld().destroyBody(jackBody);
        worldGame.getWorld().destroyBody(boundsScreenBody);

    }

    public void setUpgradeScreen(boolean isUpgradeScreen) {
        this.isUpgradeScreen = isUpgradeScreen;
    }

    private void playSoundButtonClicked() {
        myGame.musicGame.getButtonClicked();
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
        worldGame.getWorld().dispose();
    }


    @Override
    public boolean keyDown(int keycode) {


        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.BACK) {
            Gdx.app.exit();
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {


        if (buttonPlayNotPressed.getBoundingRectangle().contains(screenX, screenY)) {
            isButtonPlayPressedDown = true;
        } else {
            isButtonPlayPressedDown = false;
        }


        if (buttonShopNotPressed.getBoundingRectangle().contains(screenX, screenY)) {
            isButtonShopDown = true;
        } else {
            isButtonShopDown = false;
        }


        if (buttonHighScoreNotPressed.getBoundingRectangle().contains(screenX, screenY)) {
            isButtonHighScorePressedDown = true;
        } else {
            isButtonHighScorePressedDown = false;
        }


        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        Vector3 touchPosition = new Vector3(screenX, screenY, 0);
        myGame.camera.unproject(touchPosition);

        if (buttonPlayNotPressed.getBoundingRectangle().contains(touchPosition.x, touchPosition.y)) {
            isButtonPlayPressedDown = false;
            isButtonPlayPressedUp = true;

        } else {
            isButtonPlayPressedDown = false;
        }


        if (buttonShopNotPressed.getBoundingRectangle().contains(touchPosition.x, touchPosition.y)) {
            isButtonShopDown = false;
            isButtonShopUp = true;

        } else {
            isButtonShopDown = false;
        }

        if (buttonHighScoreNotPressed.getBoundingRectangle().contains(touchPosition.x, touchPosition.y)) {
            isButtonHighScorePressedDown = false;
            isButtonHighScorePressedUp = true;

        } else {
            isButtonHighScorePressedDown = false;
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
