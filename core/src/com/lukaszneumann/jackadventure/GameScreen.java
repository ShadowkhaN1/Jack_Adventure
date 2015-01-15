package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Lukasz on 2014-12-14.
 */
public class GameScreen implements Screen {


    private Player player;
    private MyGame myGame;
    private Cloud cloud;
    // private ArrayList<Background> backgroundArrayList;
    private LaunchingPlayer launchingPlayer;
    private PauseScreen pauseScreen;
    private Sprite topBorder;
    private Sprite counterCandy;
    private Sprite heightMeter;
    private Sprite headBackground;
    private ArrayList<GameObject> backgroundArrayList;
    private ArrayList<Candy> yellowCandyArrayList;
    private ArrayList<Candy> redCandyArrayList;
    private ArrayList<Candy> blueCandyArrayList;
    private ArrayList<PowerUp> powerUpArrayList = new ArrayList<PowerUp>(10);
    private ArrayList<GameObject> platformArrayList;
    private ArrayList<GameObjectAnimation> boostHeightArrayList;
    private TextureRegion[] boostHeightTextureArray;
    private ArrayList<EnemyBat> enemyBatArrayList = new ArrayList<EnemyBat>(10);
    private ArrayList<GameObjectAnimation> movingPlatformArrayList;
    private TextureRegion[] movingPlatformTextureDestroyAllEnemyArray;
    private TextureRegion[] movingPlatformTextureSetAllCandyOnBlueArray;
    private TextureRegion[] movingPlatformTextureGetRandomPowerUpArray;
    private ArrayList<GameObjectAnimation> smokeArrayList;
    private ArrayList<EnemySpike> enemySpikeArrayList = new ArrayList<EnemySpike>(10);
    private ArrayList<Hammer> hammerArrayList;
    private ArrayList<Collect> collectArrayList;
    private ScoreCandy scoreCandy;
    private ScoreHeight scoreHeight;
    private Sprite pauseButton_1;
    private Sprite pauseButton_2;
    private Icon magnetIcon;
    private Icon copterIcon;
    private Icon shieldIcon;
    private BitmapFont textScoreCandy;
    private BitmapFont textScoreHeight;

    private ArrayList<SmokeExplosion> smokeExplosionArrayList = new ArrayList<SmokeExplosion>(10);
    private ArrayList<Ghost> ghostArrayList = new ArrayList<Ghost>(10);


    private Sprite launchText;

    private int respawnTimeBat = 6;
    private int respawnPlatform = 10;
    private int respawnTimeRedCandy = 2;
    private float respawnTimeYellowCandy = 5;
    private int respawnTimeBlueCandy = 8;
    private float stateTime = 0;
    private float stateTimeBat = 0;
    private float stateTimeMovingPlatform = 0;
    private float stateTimePlatform = 0;
    private float stateTimeYellowCandy = 0;
    private float stateTimeRedCandy = 0;
    private float stateTimeBlueCandy = 0;
    private float stateTimePowerUp = 0;
    private float stateTimeSpike = 0;
    private float stateTimeLaunchText = 0;
    private float stateTimeWitch = 0;
    private Witch witch;
    private BlinkWitch blinkWitch;
    private ArrayList<ProjectileWitch> projectilesWitchArrayList = new ArrayList<ProjectileWitch>(10);
    private float stateTimeProjectileWitch = 0;
    private SmallCandyLight smallCandyLight;
    private ArrayList<ExplosionSpike> explosionSpikeArrayList = new ArrayList<ExplosionSpike>(10);
    private Sprite hearth;
    private PanelUpgrade panelUpgrade;
    private Sprite lifeBar;
    private Sprite life;
    private Sprite tombstone;
    private Vector2 gravity = new Vector2(0, -9.8f);

    public GameScreen(MyGame myGame) {
        this.myGame = myGame;
        show();
        setAnimationTexture();
    }


    @Override
    public void render(float deltaTime) {


        if (Gdx.input.isKeyPressed(Input.Buttons.BACK)) {
            myGame.setScreen(new MenuScreen(myGame));
        }

        drawObjects();

        if (launchingPlayer.isFlying()) {

            if (!myGame.isPause && !myGame.isOver) {
                update(deltaTime);
            }
        }
        if (!launchingPlayer.getEndAnimation()) {
            updateLaunchingPlayer(deltaTime);
        }


//        if (copterIcon.getCountPowerUp() > 0 && Gdx.input.justTouched()
//                && copterIcon.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
//            player.isCopter = true;
//            player.isDead = false;
//            player.isShield = false;
//            player.isMagnet = false;
//            player.isRocket = false;
//            copterIcon.removeCountPowerUp();
//        } else if (shieldIcon.getCountPowerUp() > 0 && Gdx.input.justTouched()
//                && shieldIcon.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
//            player.isCopter = false;
//            player.isDead = false;
//            player.isRocket = false;
//            player.isMagnet = false;
//            player.isShield = true;
//            shieldIcon.removeCountPowerUp();
//        } else if (magnetIcon.getCountPowerUp() > 0 && Gdx.input.justTouched()
//                && magnetIcon.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
//            player.isCopter = false;
//            player.isDead = false;
//            player.isRocket = false;
//            player.isShield = false;
//            player.isMagnet = true;
//            magnetIcon.removeCountPowerUp();
//        }


        if (myGame.isPause) {
            pauseScreen.render(deltaTime);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {


        Texture.setAssetManager(myGame.content.getAssetManager());
        textScoreCandy = myGame.initializeGame.assetManager.get("Font/text.fnt", BitmapFont.class);
        textScoreHeight = myGame.initializeGame.assetManager.get("Font/text.fnt", BitmapFont.class);


        hearth = new Sprite(myGame.content.getAssetManager().get("Health/Hearth.png", Texture.class));
        hearth.setPosition(0.3f * hearth.getWidth(), myGame.HEIGHT_SCREEN - (3.5f * hearth.getHeight()));


        topBorder = new Sprite(myGame.content.getAssetManager().get("Indicator/Top Border.png", Texture.class));
        topBorder.setSize(myGame.WIDTH_SCREEN, topBorder.getHeight());
        topBorder.setPosition(0, myGame.HEIGHT_SCREEN - topBorder.getHeight());

        counterCandy = new Sprite(myGame.content.getAssetManager().get("Indicator/Candy.png", Texture.class));
        counterCandy.setPosition(0, myGame.HEIGHT_SCREEN - 1.1f * counterCandy.getHeight());

        heightMeter = new Sprite(myGame.content.getAssetManager().get("Indicator/Height.png", Texture.class));
        heightMeter.setPosition(1.2f * counterCandy.getWidth(), myGame.HEIGHT_SCREEN - 1.1f * heightMeter.getHeight());

        headBackground = new Background(myGame.content.getAssetManager().get("Background/10.png", Texture.class));

        backgroundArrayList = new ArrayList<GameObject>(9);
        backgroundArrayList.add(new GameObject(myGame.content.getAssetManager().get("Background/9.png", Texture.class)));
        backgroundArrayList.add(new GameObject(myGame.content.getAssetManager().get("Background/8.png", Texture.class)));
        backgroundArrayList.add(new GameObject(myGame.content.getAssetManager().get("Background/7.png", Texture.class)));
        backgroundArrayList.add(new GameObject(myGame.content.getAssetManager().get("Background/6.png", Texture.class)));
        backgroundArrayList.add(new GameObject(myGame.content.getAssetManager().get("Background/5.png", Texture.class)));
        backgroundArrayList.add(new GameObject(myGame.content.getAssetManager().get("Background/4.png", Texture.class)));
        backgroundArrayList.add(new GameObject(myGame.content.getAssetManager().get("Background/3.png", Texture.class)));
        backgroundArrayList.add(new GameObject(myGame.content.getAssetManager().get("Background/2.png", Texture.class)));
        backgroundArrayList.add(new GameObject(myGame.content.getAssetManager().get("Background/1.png", Texture.class)));


        for (int i = 0; i < backgroundArrayList.size(); i++) {

            backgroundArrayList.get(i).setSize(myGame.WIDTH_SCREEN, backgroundArrayList.get(i).getHeight());
            backgroundArrayList.get(i).setPosition(backgroundArrayList.get(i).getX(), backgroundArrayList.get(i).getY());
            backgroundArrayList.get(i).setMaxVelocity(player.MAX_VELOCITY - 600 * WorldGame.PIXELS_TO_METERS);
        }


        yellowCandyArrayList = new ArrayList<Candy>(20);
        redCandyArrayList = new ArrayList<Candy>(20);
        blueCandyArrayList = new ArrayList<Candy>(20);


        launchingPlayer = new LaunchingPlayer(myGame);
        player = new Player(launchingPlayer.getX() + launchingPlayer.WIDTH / 2, launchingPlayer.getY() + launchingPlayer.HEIGHT / 3.5f, myGame);


        movingPlatformArrayList = new ArrayList<GameObjectAnimation>(10);
        platformArrayList = new ArrayList<GameObject>(10);
        smokeArrayList = new ArrayList<GameObjectAnimation>(10);


        scoreCandy = new ScoreCandy();
        scoreHeight = new ScoreHeight(myGame);

        launchText = new Sprite(new Texture(Gdx.files.internal("Text/Launch.png")));

        boostHeightArrayList = new ArrayList<GameObjectAnimation>(10);

        //textRocketCountPowerUp = new BitmapFont(Gdx.files.internal("Font/text.fnt"));
        //textMagnetCountPowerUp = new BitmapFont(Gdx.files.internal("Font/text.fnt"));
        //textShieldCountPowerUp = new BitmapFont(Gdx.files.internal("Font/text.fnt"));

        pauseButton_1 = new Sprite(myGame.content.getAssetManager().get("Buttons/Pause (1).png", Texture.class));
        pauseButton_1.setPosition(myGame.WIDTH_SCREEN - 1.2f * pauseButton_1.getWidth(), myGame.HEIGHT_SCREEN - 1.1f * pauseButton_1.getHeight());


        pauseButton_2 = new Sprite(myGame.content.getAssetManager().get("Buttons/Pause (2).png", Texture.class));
        pauseButton_2.setPosition(myGame.WIDTH_SCREEN - 1.2f * pauseButton_2.getWidth(), myGame.HEIGHT_SCREEN - 1.1f * pauseButton_2.getHeight());


        collectArrayList = new ArrayList<Collect>(10);
        hammerArrayList = new ArrayList<Hammer>(10);

        pauseScreen = new PauseScreen(myGame);


        myGame.soundGame.createSoundGame();


//        witch = new Sprite(myGame.content.getAssetManager().get("Enemy/witch.png", Texture.class));
//        witch.setSize(witch.getWidth() * WorldGame.PIXELS_TO_METERS, witch.getHeight() * WorldGame.PIXELS_TO_METERS);
//        witch.setOriginCenter();
//        witch.setPosition(myGame.WIDTH_SCREEN / 2 - witch.getOriginX(), myGame.HEIGHT_SCREEN / 2 + witch.getHeight());

        witch = new Witch(myGame);

        cloud = new Cloud(myGame);

        blinkWitch = new BlinkWitch(myGame);
        blinkWitch.setPosition(myGame.WIDTH_SCREEN / 2, myGame.HEIGHT_SCREEN / 2 + myGame.HEIGHT_SCREEN / 6);

        smallCandyLight = new SmallCandyLight(myGame);


        panelUpgrade = new PanelUpgrade(myGame);

        lifeBar = new Sprite(myGame.content.getAssetManager().get("Health/LifeBar.png", Texture.class));
        lifeBar.setPosition(hearth.getX() + 1.2f * hearth.getWidth(), hearth.getY() + hearth.getOriginX() - lifeBar.getHeight() / 2);

        life = new Sprite(myGame.content.getAssetManager().get("Health/Life.png", Texture.class));
        life.setPosition(lifeBar.getX(), lifeBar.getY());
        life.setColor(Color.WHITE);

        tombstone = new Sprite(myGame.content.getAssetManager().get("PowerUp/ShieldCircle (1).png", Texture.class));


    }

    private void setAnimationTexture() {


        movingPlatformTextureDestroyAllEnemyArray = new TextureRegion[6];
        movingPlatformTextureDestroyAllEnemyArray[0] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/1/Idle.png", Texture.class));
        movingPlatformTextureDestroyAllEnemyArray[1] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/1/Bounce (1).png", Texture.class));
        movingPlatformTextureDestroyAllEnemyArray[2] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/1/Bounce (2).png", Texture.class));
        movingPlatformTextureDestroyAllEnemyArray[3] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/1/Bounce (3).png", Texture.class));
        movingPlatformTextureDestroyAllEnemyArray[4] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/1/Bounce (4).png", Texture.class));
        movingPlatformTextureDestroyAllEnemyArray[5] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/1/Bounce (5).png", Texture.class));

        movingPlatformTextureSetAllCandyOnBlueArray = new TextureRegion[6];
        movingPlatformTextureSetAllCandyOnBlueArray[0] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/2/Idle.png", Texture.class));
        movingPlatformTextureSetAllCandyOnBlueArray[1] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/2/Bounce (1).png", Texture.class));
        movingPlatformTextureSetAllCandyOnBlueArray[2] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/2/Bounce (2).png", Texture.class));
        movingPlatformTextureSetAllCandyOnBlueArray[3] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/2/Bounce (3).png", Texture.class));
        movingPlatformTextureSetAllCandyOnBlueArray[4] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/2/Bounce (4).png", Texture.class));
        movingPlatformTextureSetAllCandyOnBlueArray[5] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/2/Bounce (5).png", Texture.class));

        movingPlatformTextureGetRandomPowerUpArray = new TextureRegion[6];
        movingPlatformTextureGetRandomPowerUpArray[0] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/3/Idle.png", Texture.class));
        movingPlatformTextureGetRandomPowerUpArray[1] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/3/Bounce (1).png", Texture.class));
        movingPlatformTextureGetRandomPowerUpArray[2] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/3/Bounce (2).png", Texture.class));
        movingPlatformTextureGetRandomPowerUpArray[3] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/3/Bounce (3).png", Texture.class));
        movingPlatformTextureGetRandomPowerUpArray[4] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/3/Bounce (4).png", Texture.class));
        movingPlatformTextureGetRandomPowerUpArray[5] = new TextureRegion(myGame.content.getAssetManager().get("Jumping Platform/3/Bounce (5).png", Texture.class));

        boostHeightTextureArray = new TextureRegion[3];
        boostHeightTextureArray[0] = new TextureRegion(myGame.content.getAssetManager().get("PowerUp/Boost (1).png", Texture.class));
        boostHeightTextureArray[1] = new TextureRegion(myGame.content.getAssetManager().get("PowerUp/Boost (2).png", Texture.class));
        boostHeightTextureArray[2] = new TextureRegion(myGame.content.getAssetManager().get("PowerUp/Boost (3).png", Texture.class));

    }

    private void update(float deltaTime) {

        stateTime += deltaTime;
        stateTimeSpike += deltaTime;
        stateTimePlatform += deltaTime;
        stateTimeMovingPlatform += deltaTime;


       // headBackground.update(deltaTime);

        if (player.isDead == false) {
            scoreHeight.update(deltaTime);
        }

        updateCandy(deltaTime);

        updateEnemy(deltaTime);

        updateExplosionSpike(deltaTime);

        cloud.update(deltaTime);

        panelUpgrade.update(deltaTime);


//        if user touched a screen and game is playing then elements of background update
        for (GameObject background : backgroundArrayList) {
            background.update(deltaTime);
        }


        stateTimePowerUp += deltaTime;

        if (stateTimePowerUp >= 6) {
            stateTimePowerUp = 0;

            int whichPowerUp = MathUtils.random(0, 2);

            PowerUp powerUp;

            switch (whichPowerUp) {
                case 0:
                    powerUp = new PowerUp(myGame, myGame.getContent().getAssetManager().get("PowerUp/Copter.png", Texture.class));
                    powerUp.setPower(PowerUp.TypeOfPowerUp.Copter);
                    break;
                case 1:
                    powerUp = new PowerUp(myGame, myGame.getContent().getAssetManager().get("PowerUp/Magnet.png", Texture.class));
                    powerUp.setPower(PowerUp.TypeOfPowerUp.Magnet);
                    break;
                case 2:
                    powerUp = new PowerUp(myGame, myGame.getContent().getAssetManager().get("PowerUp/Shield.png", Texture.class));
                    powerUp.setPower(PowerUp.TypeOfPowerUp.Shield);
                    break;
                default:
                    powerUp = new PowerUp(myGame, myGame.getContent().getAssetManager().get("PowerUp/Copter.png", Texture.class));
                    powerUp.setPower(PowerUp.TypeOfPowerUp.Copter);
                    break;

            }
            powerUpArrayList.add(powerUp);
        }


        for (int i = 0; i < powerUpArrayList.size(); i++) {
            powerUpArrayList.get(i).update();

            if (powerUpArrayList.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {

////                Add icon powerUp
//                switch (powerUpArrayList.get(i).getTypeOfPowerUp()) {
//                    case Copter:
//                        copterIcon.addCountPowerUp();
//                        break;
//                    case Magnet:
//                        magnetIcon.addCountPowerUp();
//                        break;
//                    case Shield:
//                        shieldIcon.addCountPowerUp();
//                        break;
//                }

                myGame.soundGame.getPowerUp().play();
                powerUpArrayList.get(i).destroyBody();
                powerUpArrayList.remove(i);

            } else if (powerUpArrayList.get(i).getY() + powerUpArrayList.get(i).getOriginY() < 0) {
                powerUpArrayList.get(i).destroyBody();
                powerUpArrayList.remove(i);
            }
        }


        stateTimeWitch += deltaTime;


        blinkWitch.update(deltaTime);

        if (blinkWitch.isFinishedAnimationBlink() == true) {
            witch.setPosition(blinkWitch.getX(), blinkWitch.getY());
            witch.setVisible(true);
            blinkWitch.setFinishedAnimationBlink(false);
        }


//        create witch
        if (stateTimeWitch >= 5 && stateTimeWitch <= 6) {
            blinkWitch.setVisible(true);
//        remove witch
        } else if (stateTimeWitch >= 15) {
            stateTimeWitch = 0;
            witch.setVisible(false);
        }


        witch.update(deltaTime);

        if (witch.isVisible()) {

            stateTimeProjectileWitch += deltaTime;

            if (stateTimeProjectileWitch >= 2) {
                stateTimeProjectileWitch = 0;

                ProjectileWitch projectileWitch = new ProjectileWitch(myGame, witch);
                projectileWitch.createPhysics();
                projectilesWitchArrayList.add(projectileWitch);
            }
        }


        for (int i = 0; i < projectilesWitchArrayList.size(); i++) {

            projectilesWitchArrayList.get(i).update();

//            destroy projectile if he out down bound screen
            if (projectilesWitchArrayList.get(i).getY() + projectilesWitchArrayList.get(i).getHeight() < 0) {
                projectilesWitchArrayList.get(i).setDestroy(true);
                projectilesWitchArrayList.remove(i);
            }
        }


        myGame.worldGame.getWorld().step(1 / 60f, 8, 3);


        myGame.rayHandler.updateAndRender();
        myGame.renderer.render(myGame.worldGame.getWorld(), myGame.camera.combined);
        myGame.worldGame.getWorld().setGravity(gravity.add(0, myGame.worldGame.getGravity()).scl(deltaTime));
        launchingPlayer.update(deltaTime);
        player.update(deltaTime);
    }

    private void updateLaunchingPlayer(float deltaTime) {

        stateTimeLaunchText += deltaTime;

        launchText.setSize(launchText.getWidth() * Math.abs(MathUtils.sin(stateTimeLaunchText)), launchText.getHeight() * Math.abs(MathUtils.sin(stateTimeLaunchText)));
        launchingPlayer.update(deltaTime);

    }

    private void updateEnemy(float deltaTime) {

        stateTimeBat += deltaTime;


        if (stateTimeBat >= respawnTimeBat) {
            stateTimeBat = 0;
            respawnTimeBat = MathUtils.random(4, 5);
            int whichEnemy = MathUtils.random(3);


            switch (whichEnemy) {
                case 0:
                    createEnemyBat();
                    break;
                case 1:
                    createEnemySpike();
                    break;
                case 2:
                    createGhost();

                default:
                    createEnemyBat();
                    break;
            }
        }

        updateEnemyBat(deltaTime);
        updateEnemySpike();
        updateGhost(deltaTime);

    }


    private void createEnemySpike() {

        EnemySpike enemySpike = new EnemySpike(myGame);
        enemySpike.setPosition(MathUtils.random(0, myGame.WIDTH_SCREEN - enemySpike.getWidth()), myGame.HEIGHT_SCREEN + enemySpike.getHeight());
        enemySpike.createPhysics();

        enemySpikeArrayList.add(enemySpike);
    }

    private void updateEnemySpike() {

        for (int i = 0; i < enemySpikeArrayList.size(); i++) {
            enemySpikeArrayList.get(i).update();


//            destroy spike enemy if he out down bound screen or he collision with player
            if (enemySpikeArrayList.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {

                createExplosionSpike(2 * enemySpikeArrayList.get(i).getWidth(), enemySpikeArrayList.get(i).getX() + enemySpikeArrayList.get(i).getOriginX(),
                        enemySpikeArrayList.get(i).getY() + enemySpikeArrayList.get(i).getOriginY());

                enemySpikeArrayList.get(i).destroyBody();
                enemySpikeArrayList.remove(i);

            } else if (enemySpikeArrayList.get(i).getY() + enemySpikeArrayList.get(i).getOriginY() < 0) {

                enemySpikeArrayList.get(i).destroyBody();
                enemySpikeArrayList.remove(i);

            }
        }
    }


    private void createExplosionSpike(float radiusDistance, float x, float y) {

        explosionSpikeArrayList.add(new ExplosionSpike(myGame, radiusDistance, x, y));
    }

    private void updateExplosionSpike(float deltaTime) {

        for (int i = 0; i < explosionSpikeArrayList.size(); i++) {

            if (explosionSpikeArrayList.get(i).isDestroyed() == true) {
                explosionSpikeArrayList.remove(i);

            } else {
                explosionSpikeArrayList.get(i).update(deltaTime);
            }
        }

    }

    private void createEnemyBat() {


        enemyBatArrayList.add(new EnemyBat(myGame));

    }

    private void updateEnemyBat(float deltaTime) {


        for (int i = 0; i < enemyBatArrayList.size(); i++) {
            enemyBatArrayList.get(i).update(deltaTime);

            if (enemyBatArrayList.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {


            } else if (enemyBatArrayList.get(i).getY() + enemyBatArrayList.get(i).getOriginY() < 0) {

                enemyBatArrayList.get(i).destroyBody();
                enemyBatArrayList.remove(i);
            }
        }
    }

    private void createGhost() {

        SmokeExplosion smokeExplosion = new SmokeExplosion(myGame);
        smokeExplosion.setPosition(MathUtils.random(myGame.WIDTH_SCREEN), myGame.HEIGHT_SCREEN / 3 + MathUtils.random(myGame.HEIGHT_SCREEN / 2));

        smokeExplosionArrayList.add(smokeExplosion);


    }

    private void updateGhost(float deltaTime) {

        for (int i = 0; i < smokeExplosionArrayList.size(); i++) {

            if (smokeExplosionArrayList.get(i).isFinishedAnimation() == true) {

                Ghost ghost = new Ghost(myGame);
                ghost.createPhysics(smokeExplosionArrayList.get(i).getX() + smokeExplosionArrayList.get(i).getOriginX(),
                        smokeExplosionArrayList.get(i).getY() + smokeExplosionArrayList.get(i).getOriginY());

                ghostArrayList.add(ghost);

//                destroy smoke explosion if he finished animation
                smokeExplosionArrayList.remove(i);
            }
        }


        for (SmokeExplosion smokeExplosion : smokeExplosionArrayList) {
            smokeExplosion.update(deltaTime);
        }


        for (int i = 0; i < ghostArrayList.size(); i++) {
            ghostArrayList.get(i).update(deltaTime);

            if (ghostArrayList.get(i).getY() + ghostArrayList.get(i).getHeight() < 0) {
                ghostArrayList.get(i).isDestroy();
                ghostArrayList.remove(i);
            }
        }
    }


    public void updateCandy(float deltaTime) {

        smallCandyLight.update();

        for (int i = 0; i < redCandyArrayList.size(); i++) {

            redCandyArrayList.get(i).update();


            if (redCandyArrayList.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {

                scoreCandy.addScore(redCandyArrayList.get(i).getPoint());
                myGame.soundGame.getRandomCandy().play();

                smallCandyLight.setRadiusLight(redCandyArrayList.get(i).getWidth());
                smallCandyLight.setPosition(redCandyArrayList.get(i).getX(), redCandyArrayList.get(i).getY());


                redCandyArrayList.get(i).destroyBody();
                redCandyArrayList.remove(i);

            } else if (redCandyArrayList.get(i).getY() + redCandyArrayList.get(i).getHeight() < 0) {
                redCandyArrayList.get(i).destroyBody();
                redCandyArrayList.remove(i);
            }
        }


        stateTimeRedCandy += deltaTime;

        if (stateTimeRedCandy >= MathUtils.random(2, 3)) {
            stateTimeRedCandy = 0;
            createCandy();
        }

    }

    private void createCandy() {

        Candy candyRed = new Candy(myGame);
        candyRed.set(new Sprite(myGame.getContent().getAssetManager().get("Candy/Candy (2).png", Texture.class)));
        candyRed.setPosition(MathUtils.random(0, myGame.WIDTH_SCREEN - candyRed.getWidth()), myGame.HEIGHT_SCREEN - candyRed.getHeight());
        candyRed.createPhysics();
        candyRed.setPoint(Candy.RED_SCORE);

        redCandyArrayList.add(candyRed);
    }


    private void drawObjects() {

        myGame.batch.begin();

        myGame.batch.draw(headBackground, 0, 0, myGame.WIDTH_SCREEN, myGame.HEIGHT_SCREEN);

        cloud.draw();

        for (GameObject background : backgroundArrayList) {
            myGame.batch.draw(background, background.getX(), background.getY(), background.getWidth(), background.getHeight());
        }


        for (Candy candy : redCandyArrayList) {
            myGame.batch.draw(candy, candy.getX(), candy.getY(), candy.getOriginX(), candy.getOriginY(), candy.getWidth(), candy.getHeight(),
                    candy.getScaleX(), candy.getScaleY(), candy.getRotation());
        }

        for (Candy candy : yellowCandyArrayList) {
            myGame.batch.draw(candy, candy.getX(), candy.getY(), candy.getOriginX(), candy.getOriginY(),
                    candy.getWidth(), candy.getHeight(), candy.getScaleX(), candy.getScaleY(), candy.getRotation());
        }

        for (Candy candy : blueCandyArrayList) {
            myGame.batch.draw(candy, candy.getX(), candy.getY(), candy.getOriginX(), candy.getOriginY(),
                    candy.getWidth(), candy.getHeight(), candy.getScaleX(), candy.getScaleY(), candy.getRotation());
        }

        for (GameObjectAnimation boostHeight : boostHeightArrayList) {
            myGame.batch.draw(boostHeight.render(), boostHeight.getX(), boostHeight.getY(), boostHeight.getOriginX(), boostHeight.getOriginY(),
                    boostHeight.getWidth(), boostHeight.getHeight(), boostHeight.getScaleX(), boostHeight.getScaleY(), boostHeight.getRotation());
        }

        for (EnemyBat bat : enemyBatArrayList) {
            myGame.batch.draw(bat.render(), bat.getX(), bat.getY(), bat.getOriginX(), bat.getOriginY(), bat.getWidth(), bat.getHeight(), bat.getScaleX(), bat.getScaleY(),
                    bat.getRotation());
        }

        for (SmokeExplosion smokeExplosion : smokeExplosionArrayList) {
            myGame.batch.draw(smokeExplosion.render(), smokeExplosion.getX(), smokeExplosion.getY(), smokeExplosion.getWidth(), smokeExplosion.getHeight());
        }

        for (Ghost ghost : ghostArrayList) {
            myGame.batch.draw(ghost, ghost.getX(), ghost.getY(), ghost.getWidth(), ghost.getHeight());
        }


        for (GameObjectAnimation movingPlatform : movingPlatformArrayList) {
            myGame.batch.draw(movingPlatform.render(), movingPlatform.getX(), movingPlatform.getY(), movingPlatform.getOriginX(), movingPlatform.getOriginY(),
                    movingPlatform.getWidth(), movingPlatform.getHeight(), movingPlatform.getScaleX(), movingPlatform.getScaleY(), movingPlatform.getRotation());
        }

        for (GameObjectAnimation smoke : smokeArrayList) {
            myGame.batch.draw(smoke.render(), smoke.getX(), smoke.getY(), smoke.getWidth(), smoke.getHeight());
        }

        for (EnemySpike spike : enemySpikeArrayList) {
            myGame.batch.draw(spike, spike.getX(), spike.getY(), spike.getOriginX(), spike.getOriginY(), spike.getWidth(), spike.getHeight(), spike.getScaleX(),
                    spike.getScaleY(), spike.getRotation());
        }


        for (GameObject platform : platformArrayList) {
            platform.draw(myGame.batch);
        }

        for (Hammer hammer : hammerArrayList) {
            hammer.draw(myGame.batch);
        }

        for (PowerUp copter : powerUpArrayList) {
            copter.draw(myGame.batch);
        }


        for (Collect collect : collectArrayList) {
            myGame.batch.draw(collect.render(), collect.getPosition().x - collect.getWidth() / 2, collect.getPosition().y - collect.getHeight() / 2,
                    collect.getWidth(), collect.getHeight());
        }

        if (blinkWitch.isVisible() == true) {
            myGame.batch.draw(blinkWitch.render(), blinkWitch.getX(), blinkWitch.getY(), blinkWitch.getWidth(), blinkWitch.getHeight());
        }


        myGame.batch.draw(launchingPlayer.render(), launchingPlayer.getX(), launchingPlayer.getY(), launchingPlayer.getOriginX(),
                launchingPlayer.getOriginY(), launchingPlayer.WIDTH, launchingPlayer.HEIGHT, launchingPlayer.getScaleX(), launchingPlayer.getScaleY(), launchingPlayer.getRotation());


//        shieldIcon.setAlpha();
//        shieldIcon.draw(myGame.batch);
//        magnetIcon.setAlpha();
//        magnetIcon.draw(myGame.batch);
//        copterIcon.setAlpha();
//        copterIcon.draw(myGame.batch);


        myGame.batch.draw(topBorder, 0, topBorder.getY(), topBorder.getWidth(), topBorder.getHeight());
        myGame.batch.draw(counterCandy, counterCandy.getX(), counterCandy.getY(), counterCandy.getWidth(), counterCandy.getHeight());
        myGame.batch.draw(heightMeter, heightMeter.getX(), heightMeter.getY(), heightMeter.getWidth(), heightMeter.getHeight());


        if (!launchingPlayer.isFlying()) {
            myGame.batch.draw(launchText, myGame.WIDTH_SCREEN / 2 - launchText.getWidth() / 2, myGame.HEIGHT_SCREEN / 2, launchText.getWidth(), launchText.getHeight());
        }


        textScoreCandy.draw(myGame.batch, scoreCandy.toString(), ((counterCandy.getX() + counterCandy.getWidth() / 2) + (0.1f * textScoreCandy.getBounds(scoreCandy.toString()).width) / 2),
                counterCandy.getY() + counterCandy.getHeight() / 2 + (textScoreCandy.getBounds(scoreCandy.toString()).height / 2));

        textScoreHeight.draw(myGame.batch, scoreHeight.toString(), (heightMeter.getX() + heightMeter.getWidth() / 2) - (0.2f * textScoreHeight.getBounds(scoreHeight.toString()).width),
                heightMeter.getY() + heightMeter.getHeight() / 2 + (textScoreHeight.getBounds(scoreHeight.toString()).height / 2));


//        tombstone.setPosition(player.getX() + player.getWidth() / 2 - tombstone.getWidth() / 2, player.getY() + player.getHeight() / 2 - tombstone.getHeight() / 2);
//        tombstone.setAlpha(0.5f);
//        tombstone.draw(myGame.batch);

        if (launchingPlayer.isFlying() == true) {
            myGame.batch.draw(player.render(), player.getX(), player.getY(), player.getOriginX(), player.getOriginY(), player.getWidth(), player.getHeight(),
                    player.getScaleX(), player.getScaleY(), player.getRotation());
        }


        witch.draw();

        for (ProjectileWitch projectileWitch : projectilesWitchArrayList) {
            projectileWitch.draw();
        }


        if (Gdx.input.isTouched() && pauseButton_1.getBoundingRectangle().contains(myGame.touch.calcX(Gdx.input.getX()), myGame.touch.calcY(Gdx.input.getY()))) {
            pauseButton_2.draw(myGame.batch);
            myGame.isPause = true;

        } else {
            pauseButton_1.draw(myGame.batch);
        }

        hearth.draw(myGame.batch);

        if (player.isDead == false) {
            panelUpgrade.drawPanelUpgrade();
        }

        lifeBar.draw(myGame.batch);
        life.draw(myGame.batch);


        myGame.batch.end();
    }


    private void writeScoreCandyToAfile() {
        myGame.fileScoreCandy.writeString(Integer.toString(Integer.parseInt(myGame.fileScoreCandy.readString()) + scoreCandy.getScore()), false);
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
