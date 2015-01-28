package com.lukaszneumann.jackadventure;


import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import java.util.ArrayList;

/**
 * Created by Lukasz on 2014-12-25.
 */
public class GameScreen implements Screen, InputProcessor {


    private Player player;
    private MyGame myGame;
    private Cloud cloud;
    private LaunchingPlayer launchingPlayer;
    private PauseScreen pauseScreen;
    private GameOverScreen gameOverScreen;
    private Sprite topBorder;
    private Sprite counterCandy;
    private Sprite heightMeter;
    private Sprite headBackground;
    private ArrayList<Background> backgroundArrayList = new ArrayList<Background>(10);
    private ArrayList<Candy> yellowCandyArrayList = new ArrayList<Candy>(10);
    private ArrayList<Candy> redCandyArrayList = new ArrayList<Candy>(10);
    private ArrayList<Candy> blueCandyArrayList = new ArrayList<Candy>(10);
    private ArrayList<PowerUp> powerUpArrayList = new ArrayList<PowerUp>(10);
    private ArrayList<GameObjectAnimation> boostHeightArrayList = new ArrayList<GameObjectAnimation>(10);
    private TextureRegion[] boostHeightTextureArray;
    private ArrayList<EnemyBat> enemyBatArrayList = new ArrayList<EnemyBat>(10);
    private TextureRegion[] movingPlatformTextureDestroyAllEnemyArray;
    private TextureRegion[] movingPlatformTextureSetAllCandyOnBlueArray;
    private TextureRegion[] movingPlatformTextureGetRandomPowerUpArray;
    private ArrayList<EnemySpike> enemySpikeArrayList = new ArrayList<EnemySpike>(10);
    private ScoreCandy scoreCandy;
    private ScoreHeight scoreHeight;
    private Sprite pauseButton_1;
    private Sprite pauseButton_2;
    private BitmapFont textScoreCandy;
    private BitmapFont textScoreHeight;

    private ArrayList<SmokeExplosion> smokeExplosionArrayList = new ArrayList<SmokeExplosion>(10);
    private ArrayList<Ghost> ghostArrayList = new ArrayList<Ghost>(10);


    private Sprite launchText;

    private float respawnTimeEnemy = 4;
    private float respawnTimeRedCandy = 2;
    private float respawnTimeYellowCandy = 5;
    private float respawnTimeBlueCandy = 8;
    private float timeEnemy = 0;
    private float timeYellowCandy = 0;
    private float timeRedCandy = 0;
    private float timeBlueCandy = 0;
    private float timePowerUp = 0;
    private float respawnPowerUp = 30;
    private float timeLaunchText = 0;
    private float timeWitch = 0;
    private float respawnTimeWitch = 40;
    private float timeHealthPotion = 0;
    private float timeShakeCamera = 0;
    private float maxTimeShaeCamera = 7;
    float shakeAngle = 0;
    private boolean isShakeCamera = false;
    private Witch witch;
    private BlinkWitch blinkWitch;
    private ArrayList<BulletWitch> bulletWitchArrayList = new ArrayList<BulletWitch>(10);
    private float timeBulletWitch = 0;
    private float respawnTimeBulletWitch = 2;
    private SmallCandyLight smallCandyLight;
    private ArrayList<ExplosionSpike> explosionSpikeArrayList = new ArrayList<ExplosionSpike>(10);
    private PanelUpgrade panelUpgrade;
    private Vector2 gravity;
    private Tombstone tombstone;
    private WorldGame worldGame;
    private RayHandler rayHandler;
    private Box2DDebugRenderer renderer;
    private boolean writeScore = false;
    private Health health;
    private Stage stage;
    private HealthPotion healthPotion;
    private float respawnHealthPotion = 20;

    private boolean isPauseButtonPressedDown = false;
    private boolean isPauseButtonPressedUp = false;

    float rotateCamera = 0;

    private float startTime = 0;

    public GameScreen(MyGame myGame) {
        this.myGame = myGame;
        show();
    }


    @Override
    public void render(float deltaTime) {

        Gdx.input.setInputProcessor(this);


        if (myGame.isPause == false && isPauseButtonPressedDown == false && Gdx.input.justTouched()) {
            launchingPlayer.setLaunched(true);
        }


        if (Gdx.input.isTouched()) {
            touchDown((int) myGame.touch.calcX(Gdx.input.getX()), (int) myGame.touch.calcY(Gdx.input.getY()), 0, 0);
        }


        drawObjects();

        if (launchingPlayer.isFlying()) {

            if (!myGame.isPause && !myGame.isOver) {
                update(deltaTime);
            }
        }

        if (!myGame.isPause) {
            updateLaunchingPlayer(deltaTime);
        }


        if (myGame.isPause == true) {
            pauseScreen.render(deltaTime);

        } else if (myGame.isGameOver == true) {


            gameOverScreen.render(deltaTime);
        }
    }


    private void update(float deltaTime) {


        if (tombstone.isStationary() == true) {
            myGame.isGameOver = true;
        }

        if (player.isDead() == true) {


            if (writeScore == true) {

                writeScoreCandyToFile();
                writeScoreHeightToFile();

                writeScore = false;
            }

            if (player.getBoundingRectangle().overlaps(backgroundArrayList.get(backgroundArrayList.size() - 1).getBoundingRectangle())) {
                player.setVisible(false);
                tombstone.setVisible(true);
                tombstone.createPhysics(player.getX() + player.getOriginX(), player.getY() + player.getOriginY());
            }
        } else {

            scoreHeight.update(deltaTime, startTime);
        }

        if (player.isDead() == false && health.isEmptyHealth() == true) {

            writeScoreCandyToFile();
            writeScoreHeightToFile();
            player.setDead(true);
        }

        updateRedCandy(deltaTime);
        updateYellowCandy(deltaTime);
        updateBlueCandy(deltaTime);
        updateEnemy(deltaTime);
        updateExplosionSpike(deltaTime);
        updateWitch(deltaTime);
        updatePowerUp(deltaTime);
        stage.update(deltaTime, scoreHeight.getScore(), scoreCandy);
        updateHealthPotion(deltaTime);
        updateShakeCamera(deltaTime);


        if (tombstone.isVisible() == false) {
            cloud.update(deltaTime, startTime);
        }

        tombstone.update();

        panelUpgrade.update(deltaTime);

//        if user touched a screen and game is playing then elements of background update
        for (Background background : backgroundArrayList) {
            background.update(deltaTime, player.isDead());
        }


        updatePlayer(deltaTime);

        worldGame.getWorld().step(1 / 60f, 8, 3);
        rayHandler.updateAndRender();
        //renderer.render(worldGame.getWorld(), myGame.camera.combined);
        //worldGame.getWorld().setGravity(gravity.set(0, worldGame.getGravity()));

    }


    private void updatePlayer(float deltaTime) {


        //run upgrade if he touched
        if (panelUpgrade.isTouchedCopter()) {
            panelUpgrade.setTouchedCopter(false);
            player.setState(Player.State.CopterState);

        } else if (panelUpgrade.isTouchedMagnet()) {
            panelUpgrade.setTouchedMagnet(false);
            player.setState(Player.State.MagnetState);

        } else if (panelUpgrade.isTouchedShield()) {
            panelUpgrade.setTouchedShield(false);
            player.setState(Player.State.ShieldState);
        }


        if (panelUpgrade.isTouchedPanelUpgrade() == false) {
            player.setMoving(true);

        } else {
            player.setMoving(false);
        }


        player.update(deltaTime);
    }

    private void updateLaunchingPlayer(float deltaTime) {

        timeLaunchText += deltaTime;

        int width = myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Text/Launch.png", Texture.class).getWidth();
        int height = myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Text/Launch.png", Texture.class).getHeight();

        launchText.setSize(width * Math.abs(MathUtils.sin(timeLaunchText)), height * Math.abs(MathUtils.sin(timeLaunchText)));

        launchingPlayer.update(deltaTime);

    }


    private void updatePowerUp(float deltaTime) {


        if (player.isDead() == false) {

            timePowerUp += deltaTime;

            if (timePowerUp >= respawnPowerUp) {
                timePowerUp = 0;
                respawnPowerUp = MathUtils.random(40 * startTime / worldGame.getWorld().getGravity().y, 60 * startTime / worldGame.getWorld().getGravity().y);

                createPowerUp();
            }
        }

        for (int i = 0; i < powerUpArrayList.size(); i++) {
            powerUpArrayList.get(i).update();

            if (powerUpArrayList.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {


                switch (powerUpArrayList.get(i).getTypeOfPowerUp()) {
                    case Magnet:
                        myGame.dataStorage.setMagnetPowerUp(myGame.dataStorage.getMagnetPowerUp() + 1);
                        break;
                    case Copter:
                        myGame.dataStorage.setCopterPowerUp(myGame.dataStorage.getCopterPowerUp() + 1);
                        break;
                    case Shield:
                        myGame.dataStorage.setShieldPowerUp(myGame.dataStorage.getShieldPowerUp() + 1);
                        break;
                }

                myGame.soundGame.getPowerUp();
                powerUpArrayList.get(i).destroyBody();
                powerUpArrayList.remove(i);

            } else if (powerUpArrayList.get(i).getY() + powerUpArrayList.get(i).getOriginY() < 0) {
                powerUpArrayList.get(i).destroyBody();
                powerUpArrayList.remove(i);
            }
        }

    }

    private void createPowerUp() {

        int whichPowerUp = MathUtils.random(0, 3);

        PowerUp powerUp;

        switch (whichPowerUp) {
            case 0:
                powerUp = new PowerUp(myGame, worldGame, myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "PowerUp/Shield.png", Texture.class));
                powerUp.setPower(PowerUp.TypeOfPowerUp.Shield);
                break;
            case 1:
                powerUp = new PowerUp(myGame, worldGame, myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "PowerUp/Magnet.png", Texture.class));
                powerUp.setPower(PowerUp.TypeOfPowerUp.Magnet);
                break;
            case 2:
                powerUp = new PowerUp(myGame, worldGame, myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "PowerUp/Copter.png", Texture.class));
                powerUp.setPower(PowerUp.TypeOfPowerUp.Copter);
                break;
            default:
                powerUp = new PowerUp(myGame, worldGame, myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "PowerUp/Magnet.png", Texture.class));
                powerUp.setPower(PowerUp.TypeOfPowerUp.Magnet);
                break;

        }
        powerUpArrayList.add(powerUp);
    }

    private void updateHealthPotion(float deltaTime) {

        timeHealthPotion += deltaTime;


        if (player.isDead() == false) {
            if (timeHealthPotion > respawnHealthPotion && healthPotion == null) {
                timeHealthPotion = 0;
                respawnHealthPotion = MathUtils.random(15 * startTime / worldGame.getWorld().getGravity().y, 35 * startTime / worldGame.getWorld().getGravity().y);

                healthPotion = new HealthPotion(myGame, worldGame);
            }
        }

        if (healthPotion != null) {
            healthPotion.update();


            if (healthPotion.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {

                myGame.soundGame.getHealth();
                health.setValueOfHealth(health.getValueOfHealth() + MathUtils.random(health.getMaxValueOfHealth() / 4, health.getMaxValueOfHealth() / 2));
                healthPotion.destroyBody();
                healthPotion = null;

            } else if (healthPotion.getY() + healthPotion.getHeight() < 0) {
                healthPotion.destroyBody();
                healthPotion = null;
            }
        }

    }

    private void updateWitch(float deltaTime) {

        blinkWitch.update(deltaTime);
        timeWitch += deltaTime;


        if (blinkWitch.isFinishedAnimationBlink() == true) {
            witch.setPosition(blinkWitch.getX(), blinkWitch.getY());
            witch.setVisible(true);
            blinkWitch.setFinishedAnimationBlink(false);
        }


        if (player.isDead() == false) {
//        create witch
            if (timeWitch >= respawnTimeWitch && timeWitch <= respawnTimeWitch + 1) {
                blinkWitch.setVisible(true);
//        remove witch
            } else if (timeWitch >= respawnTimeWitch + 10 * startTime / worldGame.getWorld().getGravity().y) {
                timeWitch = 0;
                respawnTimeWitch = MathUtils.random(25 * startTime / worldGame.getWorld().getGravity().y, 40 * startTime / worldGame.getWorld().getGravity().y);
                witch.setVisible(false);
            }
        }


        witch.update(deltaTime);


        //create bullets
        if (witch.isVisible() && player.isDead() == false) {

            timeBulletWitch += deltaTime;

            if (timeBulletWitch >= respawnTimeBulletWitch) {
                timeBulletWitch = 0;
                respawnTimeBulletWitch = MathUtils.random(1.2f * startTime / worldGame.getWorld().getGravity().y, 2f * startTime / worldGame.getWorld().getGravity().y);

                BulletWitch projectileWitch = new BulletWitch(myGame, worldGame, witch);
                projectileWitch.createPhysics(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);
                bulletWitchArrayList.add(projectileWitch);
            }
        }


        for (int i = 0; i < bulletWitchArrayList.size(); i++) {

            bulletWitchArrayList.get(i).update();


            if (bulletWitchArrayList.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {

                health.setValueOfHealth(health.getValueOfHealth() - (health.getMaxValueOfHealth() / MathUtils.random(3, 10)));
                isShakeCamera = true;
                bulletWitchArrayList.get(i).destroyBody();
                bulletWitchArrayList.remove(i);
            }

//            destroy projectile if he out down bound screen
            else if (bulletWitchArrayList.get(i).getY() + bulletWitchArrayList.get(i).getHeight() < 0) {
                bulletWitchArrayList.get(i).destroyBody();
                bulletWitchArrayList.remove(i);
            }
        }
    }


    private void updateEnemy(float deltaTime) {

        timeEnemy += deltaTime;


        if (player.isDead() == false && timeEnemy >= respawnTimeEnemy) {
            timeEnemy = 0;
            respawnTimeEnemy = MathUtils.random(0.75f * startTime / worldGame.getWorld().getGravity().y, startTime / worldGame.getWorld().getGravity().y);
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
                    break;
            }
        }

        updateEnemyBat(deltaTime);
        updateEnemySpike();
        updateGhost(deltaTime);

    }


    private void createEnemySpike() {

        EnemySpike enemySpike = new EnemySpike(myGame, worldGame);
        enemySpike.setPosition(MathUtils.random(0, myGame.WIDTH_SCREEN - enemySpike.getWidth()), myGame.HEIGHT_SCREEN + enemySpike.getHeight());
        enemySpike.createPhysics();

        enemySpikeArrayList.add(enemySpike);
    }

    private void updateEnemySpike() {

        for (int i = 0; i < enemySpikeArrayList.size(); i++) {
            enemySpikeArrayList.get(i).update();


//            destroy spike enemy if he out down bound screen or he collision with player
            if (enemySpikeArrayList.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {

                health.setValueOfHealth(health.getValueOfHealth() - (health.getMaxValueOfHealth() / MathUtils.random(3, 10)));
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

        explosionSpikeArrayList.add(new ExplosionSpike(myGame, worldGame, rayHandler, radiusDistance, x, y));
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

        enemyBatArrayList.add(new EnemyBat(myGame, worldGame));
    }

    private void updateEnemyBat(float deltaTime) {


        for (int i = 0; i < enemyBatArrayList.size(); i++) {
            enemyBatArrayList.get(i).update(deltaTime);

            if (enemyBatArrayList.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {

                health.setValueOfHealth(health.getValueOfHealth() - (health.getMaxValueOfHealth() / MathUtils.random(3, 10)));
                enemyBatArrayList.get(i).destroyBody();
                enemyBatArrayList.remove(i);

            } else if (enemyBatArrayList.get(i).getY() + enemyBatArrayList.get(i).getOriginY() < 0) {

                enemyBatArrayList.get(i).destroyBody();
                enemyBatArrayList.remove(i);
            }
        }
    }

    private void createGhost() {

        if (player.isDead() == false) {

            SmokeExplosion smokeExplosion = new SmokeExplosion(myGame);
            smokeExplosion.setPosition(MathUtils.random(myGame.WIDTH_SCREEN), myGame.HEIGHT_SCREEN / 3 + MathUtils.random(myGame.HEIGHT_SCREEN / 2));

            smokeExplosionArrayList.add(smokeExplosion);
        }

    }

    private void updateGhost(float deltaTime) {

        for (int i = 0; i < smokeExplosionArrayList.size(); i++) {

            if (smokeExplosionArrayList.get(i).isFinishedAnimation() == true) {

                Ghost ghost = new Ghost(myGame, worldGame, rayHandler);
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

            if (ghostArrayList.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {

                health.setValueOfHealth(health.getValueOfHealth() - (health.getMaxValueOfHealth() / MathUtils.random(3, 10)));
                ghostArrayList.get(i).destroy();
                ghostArrayList.remove(i);

            } else if (ghostArrayList.get(i).getY() + ghostArrayList.get(i).getHeight() < 0) {
                ghostArrayList.get(i).destroy();
                ghostArrayList.remove(i);
            }
        }
    }


    public void updateRedCandy(float deltaTime) {

        smallCandyLight.update();

        for (int i = 0; i < redCandyArrayList.size(); i++) {

            redCandyArrayList.get(i).update();

            if (player.getState() == Player.State.MagnetState && redCandyArrayList.get(i).getY() < 0.75f * myGame.HEIGHT_SCREEN) {
                redCandyArrayList.get(i).setFollowTheObject(true, player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);

            } else {
                redCandyArrayList.get(i).setFollowObject(false);
            }


            if (redCandyArrayList.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {

                scoreCandy.addScore(redCandyArrayList.get(i).getPoint());
                myGame.soundGame.getRandomCandy();

                smallCandyLight.setRadiusLight(redCandyArrayList.get(i).getWidth() / 2);
                smallCandyLight.setPosition(redCandyArrayList.get(i).getX(), redCandyArrayList.get(i).getY());


                redCandyArrayList.get(i).destroyBody();
                redCandyArrayList.remove(i);

            } else if (redCandyArrayList.get(i).getY() + redCandyArrayList.get(i).getHeight() < 0) {
                redCandyArrayList.get(i).destroyBody();
                redCandyArrayList.remove(i);
            }
        }


        //  System.out.println(* (int) worldGame.getWorld().getGravity().y);

        timeRedCandy += deltaTime;

        if (timeRedCandy >= respawnTimeRedCandy) {
            respawnTimeRedCandy = MathUtils.random(startTime / worldGame.getWorld().getGravity().y, 1.5f * startTime / worldGame.getWorld().getGravity().y);
            timeRedCandy = 0;
            createRedCandy();
        }

    }

    private void createRedCandy() {

        if (player.isDead() == false) {

            Candy candyRed = new Candy(myGame, worldGame, rayHandler);
            candyRed.set(new Sprite(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Candy/Candy (2).png", Texture.class)));
            candyRed.setPosition(MathUtils.random(0, myGame.WIDTH_SCREEN - candyRed.getWidth()), myGame.HEIGHT_SCREEN - candyRed.getHeight());
            candyRed.setColorCandy(Color.RED);
            candyRed.createPhysics();
            candyRed.setPoint(Candy.RED_SCORE);

            redCandyArrayList.add(candyRed);
        }
    }


    public void updateYellowCandy(float deltaTime) {

        smallCandyLight.update();

        for (int i = 0; i < yellowCandyArrayList.size(); i++) {

            yellowCandyArrayList.get(i).update();

            if (player.getState() == Player.State.MagnetState && yellowCandyArrayList.get(i).getY() < 0.75f * myGame.HEIGHT_SCREEN) {
                yellowCandyArrayList.get(i).setFollowTheObject(true, player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);

            } else {
                yellowCandyArrayList.get(i).setFollowObject(false);
            }


            if (yellowCandyArrayList.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {

                scoreCandy.addScore(yellowCandyArrayList.get(i).getPoint());
                myGame.soundGame.getRandomCandy();

                smallCandyLight.setRadiusLight(yellowCandyArrayList.get(i).getWidth() / 2);
                smallCandyLight.setPosition(yellowCandyArrayList.get(i).getX(), yellowCandyArrayList.get(i).getY());


                yellowCandyArrayList.get(i).destroyBody();
                yellowCandyArrayList.remove(i);

            } else if (yellowCandyArrayList.get(i).getY() + yellowCandyArrayList.get(i).getHeight() < 0) {
                yellowCandyArrayList.get(i).destroyBody();
                yellowCandyArrayList.remove(i);
            }
        }


        timeYellowCandy += deltaTime;

        if (timeYellowCandy >= respawnTimeYellowCandy) {
            respawnTimeYellowCandy = MathUtils.random(10f * startTime / worldGame.getWorld().getGravity().y, 20 * startTime / worldGame.getWorld().getGravity().y);
            timeYellowCandy = 0;
            createYellowCandy();
        }

    }


    private void createYellowCandy() {

        if (player.isDead() == false) {

            Candy candyYellow = new Candy(myGame, worldGame, rayHandler);
            candyYellow.set(new Sprite(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Candy/Candy (1).png", Texture.class)));
            candyYellow.setPosition(MathUtils.random(0, myGame.WIDTH_SCREEN - candyYellow.getWidth()), myGame.HEIGHT_SCREEN - candyYellow.getHeight());
            candyYellow.setColorCandy(Color.YELLOW);
            candyYellow.createPhysics();
            candyYellow.setPoint(Candy.YELLOW_SCORE);

            yellowCandyArrayList.add(candyYellow);
        }
    }


    public void updateBlueCandy(float deltaTime) {

        smallCandyLight.update();

        for (int i = 0; i < blueCandyArrayList.size(); i++) {

            blueCandyArrayList.get(i).update();

            if (player.getState() == Player.State.MagnetState && blueCandyArrayList.get(i).getY() < 0.75f * myGame.HEIGHT_SCREEN) {
                blueCandyArrayList.get(i).setFollowTheObject(true, player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);

            } else {
                blueCandyArrayList.get(i).setFollowObject(false);
            }


            if (blueCandyArrayList.get(i).getBoundingRectangle().overlaps(player.getBoundingRectangle())) {

                scoreCandy.addScore(blueCandyArrayList.get(i).getPoint());
                myGame.soundGame.getRandomCandy();

                smallCandyLight.setRadiusLight(blueCandyArrayList.get(i).getWidth() / 2);
                smallCandyLight.setPosition(blueCandyArrayList.get(i).getX(), blueCandyArrayList.get(i).getY());


                blueCandyArrayList.get(i).destroyBody();
                blueCandyArrayList.remove(i);

            } else if (blueCandyArrayList.get(i).getY() + blueCandyArrayList.get(i).getHeight() < 0) {
                blueCandyArrayList.get(i).destroyBody();
                blueCandyArrayList.remove(i);
            }
        }


        timeBlueCandy += deltaTime;

        if (timeBlueCandy >= respawnTimeBlueCandy) {
            respawnTimeBlueCandy = MathUtils.random(15 * startTime / worldGame.getWorld().getGravity().y, 30 * startTime / worldGame.getWorld().getGravity().y);
            timeBlueCandy = 0;
            createBlueCandy();
        }

    }

    private void createBlueCandy() {

        if (player.isDead() == false) {

            Candy blueCandy = new Candy(myGame, worldGame, rayHandler);
            blueCandy.set(new Sprite(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Candy/Candy (3).png", Texture.class)));
            blueCandy.setPosition(MathUtils.random(0, myGame.WIDTH_SCREEN - blueCandy.getWidth()), myGame.HEIGHT_SCREEN - blueCandy.getHeight());
            blueCandy.setColorCandy(Color.BLUE);
            blueCandy.createPhysics();
            blueCandy.setPoint(Candy.BLUE_SCORE);

            blueCandyArrayList.add(blueCandy);
        }
    }


    private void updateShakeCamera(float deltaTime) {

        if (player.isDead()) {
            headBackground.setRotation(0);

        } else if (isShakeCamera == true) {
            timeShakeCamera += deltaTime;

            if (timeShakeCamera <= maxTimeShaeCamera) {

                rotateCamera = (rotateCamera + deltaTime * 10) % 360;

                shakeAngle = MathUtils.sin(rotateCamera) * 5;

                headBackground.setRotation(shakeAngle);

            } else {
                timeShakeCamera = 0;
                this.isShakeCamera = false;
                headBackground.setRotation(0);

            }
        }
    }


    private void drawObjects() {

        myGame.batch.begin();

        myGame.batch.draw(headBackground, 0, 0, headBackground.getOriginX(), headBackground.getOriginY(), myGame.WIDTH_SCREEN, myGame.HEIGHT_SCREEN,
                headBackground.getScaleX(), headBackground.getScaleY(), headBackground.getRotation());


        cloud.draw();


        for (Background background : backgroundArrayList) {
            myGame.batch.draw(background, background.getX(), background.getY(), background.getOriginX(), background.getOriginY(),
                    background.getWidth(), background.getHeight(), background.getScaleX(), background.getScaleY(), background.getRotation());
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


        for (EnemySpike spike : enemySpikeArrayList) {
            myGame.batch.draw(spike, spike.getX(), spike.getY(), spike.getOriginX(), spike.getOriginY(), spike.getWidth(), spike.getHeight(), spike.getScaleX(),
                    spike.getScaleY(), spike.getRotation());
        }


        for (PowerUp copter : powerUpArrayList) {
            copter.draw(myGame.batch);
        }


        if (blinkWitch.isVisible() == true) {
            myGame.batch.draw(blinkWitch.render(), blinkWitch.getX(), blinkWitch.getY(), blinkWitch.getWidth(), blinkWitch.getHeight());
        }


        myGame.batch.draw(launchingPlayer.render(), launchingPlayer.getX(), launchingPlayer.getY(), launchingPlayer.getOriginX(),
                launchingPlayer.getOriginY(), launchingPlayer.WIDTH, launchingPlayer.HEIGHT, launchingPlayer.getScaleX(), launchingPlayer.getScaleY(), launchingPlayer.getRotation());


        myGame.batch.draw(topBorder, 0, topBorder.getY(), topBorder.getWidth(), topBorder.getHeight());
        myGame.batch.draw(counterCandy, counterCandy.getX(), counterCandy.getY(), counterCandy.getWidth(), counterCandy.getHeight());
        myGame.batch.draw(heightMeter, heightMeter.getX(), heightMeter.getY(), heightMeter.getWidth(), heightMeter.getHeight());


        if (!launchingPlayer.isFlying()) {
            myGame.batch.draw(launchText, myGame.WIDTH_SCREEN / 2 - launchText.getWidth() / 2, myGame.HEIGHT_SCREEN / 2 - launchText.getHeight() / 2, launchText.getOriginY(),
                    launchText.getOriginY(), launchText.getWidth(), launchText.getHeight(), launchText.getScaleX(), launchText.getScaleY(), launchText.getRotation());
        }


        textScoreCandy.draw(myGame.batch, scoreCandy.toString(), ((counterCandy.getX() + counterCandy.getWidth() / 2) + (0.1f * textScoreCandy.getBounds(scoreCandy.toString()).width) / 2),
                counterCandy.getY() + counterCandy.getHeight() / 2 + (textScoreCandy.getBounds(scoreCandy.toString()).height / 2));

        textScoreHeight.draw(myGame.batch, scoreHeight.toString(), (heightMeter.getX() + heightMeter.getWidth() / 2) - (0.2f * textScoreHeight.getBounds(scoreHeight.toString()).width),
                heightMeter.getY() + heightMeter.getHeight() / 2 + (textScoreHeight.getBounds(scoreHeight.toString()).height / 2));


        if (launchingPlayer.isFlying() == true && player.isVisible() == true) {
            myGame.batch.draw(player.render(), player.getX(), player.getY(), player.getOriginX(), player.getOriginY(), player.getWidth(), player.getHeight(),
                    player.getScaleX(), player.getScaleY(), player.getRotation());
        }


        if (player.isDead() == false) {

            witch.draw();
        }

        for (BulletWitch bulletWitch : bulletWitchArrayList) {
            bulletWitch.draw();
        }


        drawPauseButton();


        if (launchingPlayer.isFlying() == true && player.isDead() == false) {
            panelUpgrade.drawPanelUpgrade();
        }


        if (tombstone.isVisible() == true) {
            myGame.batch.draw(tombstone, tombstone.getX(), tombstone.getY(), tombstone.getWidth(), tombstone.getHeight());
        }

        stage.drawStage();

        health.drawHearth();
        player.drawShield();

        if (healthPotion != null) {
            healthPotion.draw(myGame.batch);
        }

        myGame.batch.end();

        health.drawLife();


    }

    public WorldGame getWorldGame() {
        return worldGame;
    }

    public void writeScoreCandyToFile() {

        myGame.dataStorage.setCandySCore(myGame.dataStorage.getCandyScore() + scoreCandy.getScore());

    }

    public void writeScoreHeightToFile() {

        if (scoreHeight.getScore() > myGame.dataStorage.getScoreHeight()) {
            myGame.dataStorage.setScoreHeight(scoreHeight.getScore());
        }
    }


    private void drawPauseButton() {

        if (!isPauseButtonPressedDown) {
            myGame.batch.draw(pauseButton_1, pauseButton_1.getX(), pauseButton_1.getY(), pauseButton_1.getWidth(), pauseButton_1.getHeight());

        } else {
            myGame.batch.draw(pauseButton_2, pauseButton_2.getX(), pauseButton_2.getY(), pauseButton_2.getWidth(), pauseButton_2.getHeight());
        }

        if (isPauseButtonPressedUp) {
            myGame.musicGame.getButtonClicked();
            isPauseButtonPressedUp = false;
            myGame.isPause = true;
        }
    }


    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void hide() {


    }

    @Override
    public void pause() {
        myGame.isPause = true;
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

        rayHandler.dispose();
        rayHandler.removeAll();

        worldGame.getWorld().dispose();
    }


    @Override
    public void show() {

        gravity = new Vector2(0, -myGame.WIDTH_SCREEN / 2 * Gdx.graphics.getDeltaTime());

        worldGame = new WorldGame(gravity, false);
        worldGame.setGravity(gravity.y);
        worldGame.getWorld().setContactListener(new MyContactListener());

        startTime = gravity.y;

        rayHandler = new RayHandler(worldGame.getWorld());
        rayHandler.setCombinedMatrix(myGame.camera.combined);
        rayHandler.setAmbientLight(1f);
        rayHandler.setBlurNum(3);

        renderer = new Box2DDebugRenderer();

        Texture.setAssetManager(myGame.content.getAssetManager());
        textScoreCandy = myGame.initializeGame.getFont();
        textScoreHeight = myGame.initializeGame.getFont();


        topBorder = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Indicator/Top Border.png", Texture.class));
        topBorder.setSize(myGame.WIDTH_SCREEN, topBorder.getHeight());
        topBorder.setPosition(0, myGame.HEIGHT_SCREEN - topBorder.getHeight());

        counterCandy = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Indicator/Candy.png", Texture.class));
        counterCandy.setPosition(0, myGame.HEIGHT_SCREEN - 1.1f * counterCandy.getHeight());

        heightMeter = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Indicator/Height.png", Texture.class));
        heightMeter.setPosition(1.2f * counterCandy.getWidth(), myGame.HEIGHT_SCREEN - 1.1f * heightMeter.getHeight());

        headBackground = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/10.png", Texture.class));
        headBackground.setPosition(myGame.WIDTH_SCREEN, myGame.HEIGHT_SCREEN);

        backgroundArrayList = new ArrayList<Background>(9);
        backgroundArrayList.add(new Background(myGame, worldGame, new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/9.png", Texture.class))));
        backgroundArrayList.add(new Background(myGame, worldGame, new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/8.png", Texture.class))));
        backgroundArrayList.add(new Background(myGame, worldGame, new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/7.png", Texture.class))));
        backgroundArrayList.add(new Background(myGame, worldGame, new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/6.png", Texture.class))));
        backgroundArrayList.add(new Background(myGame, worldGame, new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/5.png", Texture.class))));
        backgroundArrayList.add(new Background(myGame, worldGame, new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/4.png", Texture.class))));
        backgroundArrayList.add(new Background(myGame, worldGame, new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/3.png", Texture.class))));
        backgroundArrayList.add(new Background(myGame, worldGame, new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/2.png", Texture.class))));
        backgroundArrayList.add(new Background(myGame, worldGame, new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Background/1.png", Texture.class))));


        launchingPlayer = new LaunchingPlayer(myGame);
        player = new Player(launchingPlayer.getX() + launchingPlayer.WIDTH / 2, launchingPlayer.getY() + launchingPlayer.HEIGHT / 2f, myGame, worldGame);


        scoreCandy = new ScoreCandy(myGame);
        scoreHeight = new ScoreHeight(myGame, worldGame);

        launchText = new Sprite(myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Text/Launch.png", Texture.class));
        launchText.setOriginCenter();


        pauseButton_1 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Buttons/Pause (1).png", Texture.class));
        pauseButton_1.setPosition(myGame.WIDTH_SCREEN - 1.2f * pauseButton_1.getWidth(), myGame.HEIGHT_SCREEN - 1.1f * pauseButton_1.getHeight());


        pauseButton_2 = new Sprite(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Buttons/Pause (2).png", Texture.class));
        pauseButton_2.setPosition(myGame.WIDTH_SCREEN - 1.2f * pauseButton_2.getWidth(), myGame.HEIGHT_SCREEN - 1.1f * pauseButton_2.getHeight());


        pauseScreen = new PauseScreen(myGame, this);
        gameOverScreen = new GameOverScreen(myGame);


        myGame.soundGame.createSoundGame();

        witch = new Witch(myGame, worldGame);

        cloud = new Cloud(myGame, worldGame);

        blinkWitch = new BlinkWitch(myGame);
        blinkWitch.setPosition(myGame.WIDTH_SCREEN / 2, myGame.HEIGHT_SCREEN / 2 + myGame.HEIGHT_SCREEN / 6);

        smallCandyLight = new SmallCandyLight(myGame, worldGame, rayHandler);


        panelUpgrade = new PanelUpgrade(myGame, worldGame);


        tombstone = new Tombstone(myGame, worldGame);

        health = new Health(myGame);

        stage = new Stage(myGame, worldGame);


        boostHeightTextureArray = new TextureRegion[3];
        boostHeightTextureArray[0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "PowerUp/Boost (1).png", Texture.class));
        boostHeightTextureArray[1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "PowerUp/Boost (2).png", Texture.class));
        boostHeightTextureArray[2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "PowerUp/Boost (3).png", Texture.class));

    }


    @Override
    public boolean keyDown(int keycode) {


        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {

            myGame.musicGame.getButtonBackClicked();
            myGame.isPause = true;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (pauseButton_1.getBoundingRectangle().contains(screenX, screenY)) {
            isPauseButtonPressedDown = true;

        } else {
            isPauseButtonPressedDown = false;
        }


        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        Vector3 touchPosition = new Vector3(screenX, screenY, 0);
        myGame.camera.unproject(touchPosition);

        if (pauseButton_1.getBoundingRectangle().contains(touchPosition.x, touchPosition.y)) {
            isPauseButtonPressedDown = false;
            isPauseButtonPressedUp = true;

        } else {
            isPauseButtonPressedDown = false;
            isPauseButtonPressedUp = false;
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
