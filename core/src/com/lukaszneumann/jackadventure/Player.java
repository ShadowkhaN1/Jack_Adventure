package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Lukasz on 2014-11-10.
 */
public class Player extends Sprite {

    private float WIDTH = 0;
    private float HEIGHT = 0;
    static float MAX_VELOCITY = 1300;
    static float velocityHorizontalAccelerometer = 500;
    static float VELOCITY_HORIZONTAL = 0;
    private boolean isDead = false;
    static boolean accelerometerControls = false;
    static boolean touchControls = true;

    public enum AnimationState {
        Jump, Dead, LeanLeft, LeanRight,
    }

    AnimationState animationState = AnimationState.Jump;

    public enum State {
        NormalState, CopterState, MagnetState, ShieldState
    }

    State state = State.NormalState;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    private float stateTime = 0;
    private float maxTimeUpgrade = 15f;
    private float stateTimeAnimation = 0;
    private MyGame myGame;
    private WorldGame worldGame;
    private Body bodyPlayer;
    private Animation jump;
    private Animation dead;
    private Animation fall;
    private Animation leanLeft;
    private Animation leanRight;
    private Animation magnetJump;
    private Animation magnetLeanLeft;
    private Animation magnetLeanRight;
    private Animation shieldJump;
    private Animation shieldLeanLeft;
    private Animation shieldLeanRight;
    private Animation copter;
    private TextureRegion[][] regions;
    private boolean isVisible = true;
    private boolean isMoving = true;
    private boolean isCreatePhysicsShield = false;


    private Sprite shieldCircle;
    private Texture shieldCircleHigher;
    private Texture shieldCircleSmaller;


    private float frameDuration = 0.15f;


    public Player(float x, float y, MyGame myGame, WorldGame worldGame) {

        this.worldGame = worldGame;
        this.myGame = myGame;

        regions = new TextureRegion[10][8];

        regions[0][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Jump (1).png", Texture.class));
        regions[0][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Jump (2).png", Texture.class));
        regions[0][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Jump (3).png", Texture.class));
        regions[0][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Jump (4).png", Texture.class));
        jump = new Animation(frameDuration, regions[0][0], regions[0][1], regions[0][2], regions[0][3]);
        jump.setPlayMode(Animation.PlayMode.NORMAL);

        regions[1][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Lean Left (1).png", Texture.class));
        regions[1][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Lean Left (2).png", Texture.class));
        regions[1][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Lean Left (3).png", Texture.class));
        regions[1][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Lean Left (4).png", Texture.class));
        regions[1][4] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Lean Left (5).png", Texture.class));
        leanLeft = new Animation(frameDuration, regions[1][0], regions[1][1], regions[1][2], regions[1][3], regions[1][4]);
        leanLeft.setPlayMode(Animation.PlayMode.NORMAL);

        regions[2][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Lean Right (1).png", Texture.class));
        regions[2][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Lean Right (2).png", Texture.class));
        regions[2][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Lean Right (3).png", Texture.class));
        regions[2][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Lean Right (4).png", Texture.class));
        regions[2][4] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Lean Right (5).png", Texture.class));
        leanRight = new Animation(frameDuration, regions[2][0], regions[2][1], regions[2][2], regions[2][3], regions[2][4]);
        leanRight.setPlayMode(Animation.PlayMode.NORMAL);

        regions[3][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Dead (1).png", Texture.class));
        regions[3][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Dead (2).png", Texture.class));
        regions[3][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Dead (3).png", Texture.class));
        regions[3][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Dead (4).png", Texture.class));
        regions[3][4] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Dead (5).png", Texture.class));
        regions[3][5] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Dead (6).png", Texture.class));
        regions[3][6] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Dead (7).png", Texture.class));
        regions[3][7] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Dead (8).png", Texture.class));
        dead = new Animation(frameDuration, regions[3][0], regions[3][1], regions[3][2], regions[3][3], regions[3][4], regions[3][5],
                regions[3][6], regions[3][7]);
        dead.setPlayMode(Animation.PlayMode.LOOP);

        regions[4][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Fall (1).png", Texture.class));
        regions[4][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Fall (2).png", Texture.class));
        regions[4][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Fall (3).png", Texture.class));
        regions[4][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Fall (4).png", Texture.class));
        fall = new Animation(frameDuration, regions[4][0], regions[4][1], regions[4][2], regions[4][3]);
        fall.setPlayMode(Animation.PlayMode.NORMAL);

//        regions[5][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Rocket (1).png", Texture.class));
//        regions[5][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Rocket (2).png", Texture.class));
//        regions[5][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Rocket (3).png", Texture.class));
//        regions[5][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Rocket (4).png", Texture.class));
//        rocket = new Animation(frameDuration, regions[5][0], regions[5][1], regions[5][2], regions[5][3]);
//        rocket.setPlayMode(Animation.PlayMode.LOOP);

        regions[5][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Jump (1).png", Texture.class));
        regions[5][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Jump (2).png", Texture.class));
        regions[5][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Jump (3).png", Texture.class));
        regions[5][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Jump (4).png", Texture.class));
        magnetJump = new Animation(frameDuration, regions[5][0], regions[5][1], regions[5][2], regions[5][3]);
        magnetJump.setPlayMode(Animation.PlayMode.NORMAL);


        regions[6][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Lean Left (1).png", Texture.class));
        regions[6][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Lean Left (2).png", Texture.class));
        regions[6][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Lean Left (3).png", Texture.class));
        regions[6][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Lean Left (4).png", Texture.class));
        regions[6][4] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Lean Left (5).png", Texture.class));
        magnetLeanLeft = new Animation(frameDuration, regions[6][0], regions[6][1], regions[6][2], regions[6][3], regions[6][4]);
        magnetLeanLeft.setPlayMode(Animation.PlayMode.NORMAL);


        regions[7][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Lean Right (1).png", Texture.class));
        regions[7][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Lean Right (2).png", Texture.class));
        regions[7][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Lean Right (3).png", Texture.class));
        regions[7][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Lean Right (4).png", Texture.class));
        regions[7][4] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Magnet/Lean Right (5).png", Texture.class));
        magnetLeanRight = new Animation(frameDuration, regions[7][0], regions[7][1], regions[7][2], regions[7][3], regions[7][4]);
        magnetLeanRight.setPlayMode(Animation.PlayMode.NORMAL);


        regions[8][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Jump (1).png", Texture.class));
        regions[8][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Jump (2).png", Texture.class));
        regions[8][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Jump (3).png", Texture.class));
        regions[8][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Jump (4).png", Texture.class));
        shieldJump = new Animation(frameDuration, regions[8][0], regions[8][1], regions[8][2], regions[8][3]);
        shieldJump.setPlayMode(Animation.PlayMode.NORMAL);


        regions[7][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Lean Left (1).png", Texture.class));
        regions[7][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Lean Left (2).png", Texture.class));
        regions[7][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Lean Left (3).png", Texture.class));
        regions[7][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Lean Left (4).png", Texture.class));
        regions[7][4] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Lean Left (5).png", Texture.class));
        shieldLeanLeft = new Animation(frameDuration, regions[7][0], regions[7][1], regions[7][2], regions[7][3], regions[7][4]);
        shieldLeanLeft.setPlayMode(Animation.PlayMode.NORMAL);


        regions[8][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Lean Right (1).png", Texture.class));
        regions[8][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Lean Right (2).png", Texture.class));
        regions[8][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Lean Right (3).png", Texture.class));
        regions[8][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Lean Right (4).png", Texture.class));
        regions[8][4] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Armored/Lean Right (5).png", Texture.class));
        shieldLeanRight = new Animation(frameDuration, regions[8][0], regions[8][1], regions[8][2], regions[8][3], regions[8][4]);
        shieldLeanRight.setPlayMode(Animation.PlayMode.NORMAL);


        regions[9][0] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Copter/Copter Normal (1).png", Texture.class));
        regions[9][1] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Copter/Copter Normal (2).png", Texture.class));
        regions[9][2] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Copter/Copter Normal (3).png", Texture.class));
        regions[9][3] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Copter/Copter Normal (4).png", Texture.class));
        regions[9][4] = new TextureRegion(myGame.content.getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "Jack/Copter/Copter Normal (5).png", Texture.class));
        copter = new Animation(frameDuration, regions[9][0], regions[9][1], regions[9][2], regions[9][3], regions[9][4]);
        copter.setPlayMode(Animation.PlayMode.LOOP);


        shieldCircleHigher = (myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "PowerUp/Shield (1).png", Texture.class));
        shieldCircleSmaller = (myGame.getContent().getAssetManager().get(myGame.assetsHelper.usesDpi + "/" + "PowerUp/Shield (2).png", Texture.class));
        shieldCircle = new Sprite(shieldCircleHigher);

        VELOCITY_HORIZONTAL = myGame.WIDTH_SCREEN;

        WIDTH = getTextureRegion().getWidth();
        HEIGHT = getTextureRegion().getHeight();
        velocity.y = MAX_VELOCITY;
        position.set(x - WIDTH / 2, y);
        setPosition(position.x, position.y);
        setSize(WIDTH, HEIGHT);
        setOrigin(WIDTH / 2, HEIGHT / 2);


    }

    public void update(float deltaTime) {

        stateTimeAnimation += deltaTime;
        WIDTH = getTextureRegion().getWidth();
        HEIGHT = getTextureRegion().getHeight();
        setSize(WIDTH, HEIGHT);


        if (isDead == true) {

            isMoving = false;
            state = State.NormalState;
            animationState = AnimationState.Dead;
            worldGame.setGravity(50);
            // setDynamicBody();
            // setX(bodyPlayer.getPosition().x * WorldGame.METERS_TO_PIXELS - this.getWidth() / 2);
            //  setY(bodyPlayer.getPosition().y * WorldGame.METERS_TO_PIXELS - this.getHeight() / 2);

        }


        if (!isDead) {

            if (state == State.CopterState) {
                worldGame.getWorld().setGravity(new Vector2(0, -1f));

            } else {
                worldGame.setGravity(worldGame.getGravity());
            }

        }


        stateTime += deltaTime;


        if (stateTime >= maxTimeUpgrade) {

            state = State.NormalState;
        }


        if (!isCreatePhysicsShield && state == State.ShieldState) {
            isCreatePhysicsShield = true;
            createPhysics();

        } else if (state != State.ShieldState) {
            isCreatePhysicsShield = false;

            if (bodyPlayer != null) {
                destroyBodyShield();
            }

        }


        if (getX() + WIDTH < 0) {
            position.x = -WIDTH;
        } else if (getX() > myGame.WIDTH_SCREEN) {
            position.x = myGame.WIDTH_SCREEN;
        }


        setBounds(getX(), getY(), WIDTH, HEIGHT);

        movePlayer();

        velocity.scl(deltaTime);
        position.add(velocity);
        setX(position.x);
    }

    private void movePlayer() {

        if (isMoving == true) {

            if (touchControls) {

                for (int i = 0; i < 2; i++) {

                    if (myGame.touch.calcX(Gdx.input.getX(i)) > myGame.WIDTH_SCREEN / 2 && Gdx.input.isTouched(i)) {
                        animationState = AnimationState.LeanRight;
                        velocity.x = VELOCITY_HORIZONTAL;

                    }
                    if (myGame.touch.calcX(Gdx.input.getX(i)) < myGame.WIDTH_SCREEN / 2 && Gdx.input.isTouched(i)) {
                        animationState = AnimationState.LeanLeft;
                        velocity.x = -VELOCITY_HORIZONTAL;
                    }
                    if (!isDead && !Gdx.input.isTouched(0) && !Gdx.input.isTouched(1)) {
                        animationState = AnimationState.Jump;
                    }
                }
            } else if (accelerometerControls) {
                velocity.x = -velocityHorizontalAccelerometer * Gdx.input.getAccelerometerX();

                if (Gdx.input.getAccelerometerX() == 0) {
                    animationState = AnimationState.Jump;
                } else if (Gdx.input.getAccelerometerX() < 0) {
                    animationState = AnimationState.LeanRight;
                } else {
                    animationState = AnimationState.LeanLeft;
                }
            }
        }

        if (bodyPlayer != null) {
            bodyPlayer.setTransform((this.getX() + this.getWidth() / 2) * WorldGame.PIXELS_TO_METERS, (this.getY() + this.getHeight() / 2) * WorldGame.PIXELS_TO_METERS, 0);
        }

    }

    public void drawShield() {

        if (state == State.ShieldState) {


            for (Contact contact : worldGame.getWorld().getContactList()) {

                if (contact.getFixtureB().getBody() != (bodyPlayer) && contact.getFixtureA().getBody() != (bodyPlayer)) {
                    shieldCircle.set(new Sprite(shieldCircleHigher));

                } else {
                    shieldCircle.set(new Sprite(shieldCircleSmaller));
                }
            }

            shieldCircle.setPosition(this.getX() + this.getWidth() / 2 - shieldCircle.getWidth() / 2, this.getY() + this.getHeight() / 2 - shieldCircle.getHeight() / 2);
            shieldCircle.draw(myGame.batch, 0.5f);
        }
    }


    private Texture getTextureRegion() {

        switch (animationState) {
            case Jump:
                return regions[0][0].getTexture();
            case Dead:
                return regions[3][0].getTexture();
            case LeanLeft:
                return regions[1][0].getTexture();
            case LeanRight:
                return regions[2][0].getTexture();
            default:
                return regions[0][0].getTexture();
        }

    }

    public TextureRegion render() {

        switch (state) {
            case NormalState:
                return getNormalAnimation();
            case CopterState:
                return getCopterAnimation();
            case MagnetState:
                return getMagnetAnimation();
            case ShieldState:
                return getShieldAnimation();
            default:
                return getNormalAnimation();

        }
    }


    private TextureRegion getNormalAnimation() {

        switch (animationState) {
            case Jump:
                return jump.getKeyFrame(stateTimeAnimation);
            case Dead:
                return dead.getKeyFrame(stateTimeAnimation);
            case LeanLeft:
                return leanLeft.getKeyFrame(stateTimeAnimation);
            case LeanRight:
                return leanRight.getKeyFrame(stateTimeAnimation);
            default:
                return jump.getKeyFrame(stateTimeAnimation);
        }
    }

    private TextureRegion getCopterAnimation() {

        switch (animationState) {

            case Jump:
                return copter.getKeyFrame(stateTimeAnimation);
            default:
                return copter.getKeyFrame(stateTimeAnimation);
        }
    }

    private TextureRegion getMagnetAnimation() {

        switch (animationState) {

            case Jump:
                return magnetJump.getKeyFrame(stateTimeAnimation);
            case LeanLeft:
                return magnetLeanLeft.getKeyFrame(stateTimeAnimation);
            case LeanRight:
                return magnetLeanRight.getKeyFrame(stateTimeAnimation);
            default:
                return magnetJump.getKeyFrame(stateTimeAnimation);
        }
    }

    private TextureRegion getShieldAnimation() {

        switch (animationState) {

            case Jump:
                return shieldJump.getKeyFrame(stateTimeAnimation);
            case LeanLeft:
                return shieldLeanLeft.getKeyFrame(stateTimeAnimation);
            case LeanRight:
                return shieldLeanRight.getKeyFrame(stateTimeAnimation);
            default:
                return shieldJump.getKeyFrame(stateTimeAnimation);
        }
    }


    private void createPhysics() {

//        PolygonShape jackShape = new PolygonShape();
//        jackShape.set(new Vector2[]{
//                new Vector2(0, this.getHeight() / 2.2f * WorldGame.PIXELS_TO_METERS),
//                new Vector2(this.getWidth() / 2 * WorldGame.PIXELS_TO_METERS, -this.getHeight() / 2.2f * WorldGame.PIXELS_TO_METERS),
//                new Vector2(-this.getWidth() / 2 * WorldGame.PIXELS_TO_METERS, -this.getHeight() / 2.2f * WorldGame.PIXELS_TO_METERS),
//
//        });


        CircleShape jackShape = new CircleShape();
        jackShape.setRadius(shieldCircleSmaller.getWidth() / 1.8f * WorldGame.PIXELS_TO_METERS);

        FixtureDef fixtureDefPlayer = new FixtureDef();
        fixtureDefPlayer.shape = jackShape;
        fixtureDefPlayer.density = 1f;
        fixtureDefPlayer.friction = 0.f;
        fixtureDefPlayer.restitution = 0f;
        fixtureDefPlayer.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);
        fixtureDefPlayer.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);


        BodyDef bodyDefPlayer = new BodyDef();
        bodyDefPlayer.type = BodyDef.BodyType.StaticBody;
        bodyDefPlayer.position.x = (this.getX() + this.getWidth() / 2f) * WorldGame.PIXELS_TO_METERS;
        bodyDefPlayer.position.y = (this.getY() + this.getHeight() / 2f) * WorldGame.PIXELS_TO_METERS;


        bodyPlayer = worldGame.getWorld().createBody(bodyDefPlayer);
        bodyPlayer.createFixture(fixtureDefPlayer).setUserData("Player");


        jackShape.dispose();


    }

    private void destroyBodyShield() {
        worldGame.getWorld().destroyBody(bodyPlayer);
        bodyPlayer = null;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    private void setDynamicBody() {
        bodyPlayer.setType(BodyDef.BodyType.DynamicBody);
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    private void setSizeTexture() {
        for (int i = 0; i < regions.length; i++) {
            for (int j = 0; j < regions[i].length; j++) {
                regions[i][j].setRegionWidth(50);
            }
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;

        stateTime = 0;
    }

    public float getStateTimeAnimation() {
        return stateTimeAnimation;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }
}
