package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Lukasz on 2014-12-11.
 */
public class MenuScreen implements Screen {

    private MyGame myGame;
    private Sprite background;
    private Sprite buttonPlayNotPressed;
    private Sprite buttonPlayPressed;
    private Sprite buttonShopNotPressed;
    private Sprite buttonShopPressed;
    private Sprite buttonHighScoreNotPressed;
    private Sprite title;
    private boolean change = false;
    private float stateTime = 0;
    private Animation animationJack;
    private TextureRegion[] texturesRegionJack;
    private Sprite jackSprite;
    private float realWidthJack = 0;
    private float realHeightJack = 0;
    private float frameDuration = 0.15f;
    private Body jackBody;
    private Body boundsScreenBody;

    public MenuScreen(MyGame myGame) {
        this.myGame = myGame;
        show();

    }

    @Override
    public void render(float delta) {

        stateTime += delta;

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }

        myGame.batch.begin();
        myGame.batch.draw(background, 0, 0, myGame.WIDTH_SCREEN, myGame.HEIGHT_SCREEN);

        myGame.batch.draw(renderJack(stateTime), jackBody.getPosition().x * WorldGame.METERS_TO_PIXELS - realWidthJack / 2, jackBody.getPosition().y * WorldGame.METERS_TO_PIXELS - realHeightJack / 2, realWidthJack, realHeightJack);


        if (Gdx.input.isTouched() && buttonPlayNotPressed.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
            myGame.isPause = false;
            buttonPlayPressed.draw(myGame.batch);
            removeBodyFromWorld();
            myGame.setScreen(new LoadingScreen(myGame));
        } else {
            myGame.batch.draw(buttonPlayNotPressed, buttonPlayNotPressed.getX(), buttonPlayNotPressed.getY(),
                    buttonPlayNotPressed.getWidth(), buttonPlayNotPressed.getHeight());
        }

        if (Gdx.input.isTouched() && buttonShopNotPressed.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
            buttonShopPressed.draw(myGame.batch);
            myGame.setScreen(new UpgradeScreen(myGame));
        } else {
            myGame.batch.draw(buttonShopNotPressed, buttonShopNotPressed.getX(), buttonShopNotPressed.getY(), buttonShopNotPressed.getWidth(), buttonShopNotPressed.getHeight());
        }


        myGame.batch.draw(buttonHighScoreNotPressed, buttonHighScoreNotPressed.getX(), buttonHighScoreNotPressed.getY(), buttonHighScoreNotPressed.getWidth(), buttonHighScoreNotPressed.getHeight());
        myGame.batch.draw(title, title.getX(), title.getY(), title.getWidth(), title.getHeight());


        myGame.worldGame.getWorld().step(1 / 60f, 8, 3);

        myGame.batch.end();

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

        background = new Sprite(myGame.initializeGame.getAssetManager().get("Background/BG.png", Texture.class));

        buttonPlayNotPressed = new Sprite(myGame.initializeGame.getAssetManager().get("Buttons/Play (1).png", Texture.class));
        buttonPlayNotPressed.setPosition((myGame.WIDTH_SCREEN / 2 - buttonPlayNotPressed.getWidth() / 2), (myGame.HEIGHT_SCREEN / 2 - (2 * buttonPlayNotPressed.getHeight() / 2)));
        buttonPlayNotPressed.setBounds(buttonPlayNotPressed.getX(), buttonPlayNotPressed.getY(), buttonPlayNotPressed.getWidth(), buttonPlayNotPressed.getHeight());

        buttonPlayPressed = new Sprite(myGame.initializeGame.getAssetManager().get("Buttons/Play (2).png", Texture.class));
        buttonPlayPressed.setPosition((myGame.WIDTH_SCREEN / 2) - buttonPlayNotPressed.getWidth() / 2, (myGame.HEIGHT_SCREEN / 2) - (2 * buttonPlayNotPressed.getHeight() / 2));

        buttonShopNotPressed = new Sprite(myGame.initializeGame.getAssetManager().get("Buttons/Shop (1).png", Texture.class));
        buttonShopNotPressed.setPosition(buttonPlayNotPressed.getX() - (buttonShopNotPressed.getWidth()),
                buttonPlayNotPressed.getY() - (buttonShopNotPressed.getHeight()));
        buttonShopNotPressed.setBounds(buttonShopNotPressed.getX(), buttonShopNotPressed.getY(), buttonShopNotPressed.getWidth(), buttonShopNotPressed.getHeight());

        buttonShopPressed = new Sprite(myGame.initializeGame.getAssetManager().get("Buttons/Shop (2).png", Texture.class));
        buttonShopPressed.setPosition(buttonPlayNotPressed.getX() - buttonShopNotPressed.getWidth(), buttonPlayNotPressed.getY() - buttonShopNotPressed.getHeight());

        buttonHighScoreNotPressed = new Sprite(myGame.initializeGame.getAssetManager().get("Buttons/Score (1).png", Texture.class));
        buttonHighScoreNotPressed.setPosition(buttonPlayNotPressed.getX() + buttonHighScoreNotPressed.getWidth(),
                buttonPlayNotPressed.getY() - buttonHighScoreNotPressed.getHeight());
        buttonHighScoreNotPressed.setBounds(buttonHighScoreNotPressed.getX(), buttonHighScoreNotPressed.getY(), buttonHighScoreNotPressed.getWidth(), buttonHighScoreNotPressed.getHeight());

        title = new Sprite(myGame.initializeGame.getAssetManager().get("Text/Title 1.png", Texture.class));
        title.setPosition((myGame.WIDTH_SCREEN / 2) - title.getWidth() / 2,
                (myGame.HEIGHT_SCREEN / 2) + buttonPlayNotPressed.getHeight() / 2);


        texturesRegionJack = new TextureRegion[4];
        texturesRegionJack[0] = new TextureRegion(myGame.getInitializeGame().getAssetManager().get("Jack/Rocket (1).png", Texture.class));
        texturesRegionJack[1] = new TextureRegion(myGame.getInitializeGame().getAssetManager().get("Jack/Rocket (2).png", Texture.class));
        texturesRegionJack[2] = new TextureRegion(myGame.getInitializeGame().getAssetManager().get("Jack/Rocket (3).png", Texture.class));
        texturesRegionJack[3] = new TextureRegion(myGame.getInitializeGame().getAssetManager().get("Jack/Rocket (4).png", Texture.class));

        realWidthJack = texturesRegionJack[0].getRegionWidth();
        realHeightJack = texturesRegionJack[0].getRegionHeight();

        animationJack = new Animation(frameDuration, texturesRegionJack);
        animationJack.setPlayMode(Animation.PlayMode.LOOP);


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


        jackBody = myGame.worldGame.getWorld().createBody(bodyDefRocket);
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


        boundsScreenBody = myGame.worldGame.getWorld().createBody(bodyDefBounds);
        boundsScreenBody.createFixture(fixtureDefBounds);


        chainShape.dispose();
    }

    private void removeBodyFromWorld() {

        myGame.worldGame.getWorld().destroyBody(jackBody);
        myGame.worldGame.getWorld().destroyBody(boundsScreenBody);

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
        background.getTexture().dispose();
        buttonPlayNotPressed.getTexture().dispose();

    }
}
