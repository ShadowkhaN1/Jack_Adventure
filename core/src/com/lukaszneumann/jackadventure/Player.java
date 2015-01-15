package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Lukasz on 2014-11-10.
 */
public class Player extends Sprite {

    private float WIDTH = 0;
    private float HEIGHT = 0;
    static float MAX_VELOCITY = 1300;
    static float velocityHorizontalAccelerometer = 500;
    static float VELOCITY_HORIZONTAL = 1200;
    static boolean isRocket = false;
    static boolean isCopter = false;
    static boolean isMagnet = false;
    static boolean isShield = false;
    static boolean isDead = false;
    static boolean accelerometerControls = false;
    static boolean touchControls = true;

    public enum State {
        Jump, Dead, Fall, LeanLeft, LeanRight, Rocket, MagnetJump, ArmoredJump, Copter
    }

    State state = State.Jump;
    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    float stateTime = 0;
    private MyGame myGame;
    private Body bodyPlayer;
    private Animation jump;
    private Animation dead;
    private Animation fall;
    private Animation leanLeft;
    private Animation leanRight;
    private Animation rocket;
    private Animation magnetJump;
    private Animation armoredJump;
    private Animation copter;
    private TextureRegion[][] regions;

    private float frameDuration = 0.15f;


    public Player(float x, float y, MyGame myGame) {

        this.myGame = myGame;

        regions = new TextureRegion[8][8];

        regions[0][0] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Jump (1).png", Texture.class));
        regions[0][1] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Jump (2).png", Texture.class));
        regions[0][2] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Jump (3).png", Texture.class));
        regions[0][3] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Jump (4).png", Texture.class));
        jump = new Animation(frameDuration, regions[0][0], regions[0][1], regions[0][2], regions[0][3]);
        jump.setPlayMode(Animation.PlayMode.NORMAL);

        regions[1][0] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Lean Left (1).png", Texture.class));
        regions[1][1] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Lean Left (2).png", Texture.class));
        regions[1][2] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Lean Left (3).png", Texture.class));
        regions[1][3] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Lean Left (4).png", Texture.class));
        regions[1][4] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Lean Left (5).png", Texture.class));
        leanLeft = new Animation(frameDuration, regions[1][0], regions[1][1], regions[1][2], regions[1][3], regions[1][4]);
        leanLeft.setPlayMode(Animation.PlayMode.NORMAL);

        regions[2][0] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Lean Right (1).png", Texture.class));
        regions[2][1] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Lean Right (2).png", Texture.class));
        regions[2][2] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Lean Right (3).png", Texture.class));
        regions[2][3] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Lean Right (4).png", Texture.class));
        regions[2][4] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Lean Right (5).png", Texture.class));
        leanRight = new Animation(frameDuration, regions[2][0], regions[2][1], regions[2][2], regions[2][3], regions[2][4]);
        leanRight.setPlayMode(Animation.PlayMode.NORMAL);

        regions[3][0] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Dead (1).png", Texture.class));
        regions[3][1] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Dead (2).png", Texture.class));
        regions[3][2] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Dead (3).png", Texture.class));
        regions[3][3] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Dead (4).png", Texture.class));
        regions[3][4] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Dead (5).png", Texture.class));
        regions[3][5] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Dead (6).png", Texture.class));
        regions[3][6] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Dead (7).png", Texture.class));
        regions[3][7] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Dead (8).png", Texture.class));
        dead = new Animation(frameDuration, regions[3][0], regions[3][1], regions[3][2], regions[3][3], regions[3][4], regions[3][5],
                regions[3][6], regions[3][7]);
        dead.setPlayMode(Animation.PlayMode.LOOP);

        regions[4][0] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Fall (1).png", Texture.class));
        regions[4][1] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Fall (2).png", Texture.class));
        regions[4][2] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Fall (3).png", Texture.class));
        regions[4][3] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Fall (4).png", Texture.class));
        fall = new Animation(frameDuration, regions[4][0], regions[4][1], regions[4][2], regions[4][3]);
        fall.setPlayMode(Animation.PlayMode.NORMAL);

//        regions[5][0] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Rocket (1).png", Texture.class));
//        regions[5][1] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Rocket (2).png", Texture.class));
//        regions[5][2] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Rocket (3).png", Texture.class));
//        regions[5][3] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Rocket (4).png", Texture.class));
//        rocket = new Animation(frameDuration, regions[5][0], regions[5][1], regions[5][2], regions[5][3]);
//        rocket.setPlayMode(Animation.PlayMode.LOOP);

        regions[5][0] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Magnet/Jump (1).png", Texture.class));
        regions[5][1] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Magnet/Jump (2).png", Texture.class));
        regions[5][2] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Magnet/Jump (3).png", Texture.class));
        regions[5][3] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Magnet/Jump (4).png", Texture.class));
        magnetJump = new Animation(frameDuration, regions[5][0], regions[5][1], regions[5][2], regions[5][3]);
        magnetJump.setPlayMode(Animation.PlayMode.NORMAL);

        regions[6][0] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Armored/Jump (1).png", Texture.class));
        regions[6][1] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Armored/Jump (2).png", Texture.class));
        regions[6][2] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Armored/Jump (3).png", Texture.class));
        regions[6][3] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Armored/Jump (4).png", Texture.class));
        armoredJump = new Animation(frameDuration, regions[6][0], regions[6][1], regions[6][2], regions[6][3]);
        armoredJump.setPlayMode(Animation.PlayMode.NORMAL);

        regions[7][0] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Copter/Copter Normal (1).png", Texture.class));
        regions[7][1] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Copter/Copter Normal (2).png", Texture.class));
        regions[7][2] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Copter/Copter Normal (3).png", Texture.class));
        regions[7][3] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Copter/Copter Normal (4).png", Texture.class));
        regions[7][4] = new TextureRegion(myGame.content.getAssetManager().get("Jack/Copter/Copter Normal (5).png", Texture.class));
        copter = new Animation(frameDuration, regions[7][0], regions[7][1], regions[7][2], regions[7][3], regions[7][4]);
        copter.setPlayMode(Animation.PlayMode.LOOP);


        WIDTH = getTextureRegion().getWidth();
        HEIGHT = getTextureRegion().getHeight();
        velocity.y = MAX_VELOCITY;
        position.set(x - WIDTH / 2, y);
        setPosition(position.x, position.y);
        setSize(WIDTH, HEIGHT);
        setOrigin(WIDTH / 2, HEIGHT / 2);

        createPhysics();

    }

    public void update(float deltaTime) {

        stateTime += deltaTime;
        WIDTH = getTextureRegion().getWidth();
        HEIGHT = getTextureRegion().getHeight();
        setSize(WIDTH, HEIGHT);


        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            isDead = true;
        }

        if (isDead == true) {

            state = State.Dead;
            myGame.worldGame.setGravity(100);
            // setDynamicBody();
            setX(bodyPlayer.getPosition().x * WorldGame.METERS_TO_PIXELS - this.getWidth() / 2);
            setY(bodyPlayer.getPosition().y * WorldGame.METERS_TO_PIXELS - this.getHeight() / 2);

        } else {

            movePlayer();
        }


        if (getX() + WIDTH < 0) {
            position.x = -WIDTH;
        } else if (getX() > myGame.WIDTH_SCREEN) {
            position.x = myGame.WIDTH_SCREEN;
        }

        if (MAX_VELOCITY < 0) {
            state = State.Fall;
        }

        if (isRocket) {
            state = State.Rocket;
            MAX_VELOCITY = 1500;

        }
        if (isShield) {
            state = State.ArmoredJump;
        }
        if (isMagnet) {
            state = State.MagnetJump;
        }
        if (isDead) {
            state = State.Dead;
        }

        if (myGame.worldGame.getWorld().getGravity().y > 0) {
            state = State.Dead;
        }

        if (isCopter) {
            myGame.worldGame.getWorld().setGravity(new Vector2(0, -1f));
            state = State.Copter;
        } else {
            myGame.worldGame.getWorld().setGravity(new Vector2(0, -9.8f));
        }

        setBounds(getX(), getY(), WIDTH, HEIGHT);

        //MAX_VELOCITY -= deltaTime * deltaTime;


        // velocity.y -= MAX_VELOCITY;
        velocity.scl(deltaTime);
        position.add(velocity);
        // MAX_VELOCITY = velocity.y;
        setX(position.x);

    }

    private void movePlayer() {


        if (touchControls) {

            for (int i = 0; i < 2; i++) {

                if (myGame.touch.calcX(Gdx.input.getX(i)) > myGame.WIDTH_SCREEN / 2 && Gdx.input.isTouched(i)) {
                    state = State.LeanRight;
                    velocity.x = VELOCITY_HORIZONTAL;

                }
                if (myGame.touch.calcX(Gdx.input.getX(i)) < myGame.WIDTH_SCREEN / 2 && Gdx.input.isTouched(i)) {
                    state = State.LeanLeft;
                    velocity.x = -VELOCITY_HORIZONTAL;
                }
                if (!isDead && !Gdx.input.isTouched(0) && !Gdx.input.isTouched(1)) {
                    state = State.Jump;
                }
            }
        } else if (accelerometerControls) {
            velocity.x = -velocityHorizontalAccelerometer * Gdx.input.getAccelerometerX();

            if (Gdx.input.getAccelerometerX() == 0) {
                state = State.Jump;
            } else if (Gdx.input.getAccelerometerX() < 0) {
                state = State.LeanRight;
            } else {
                state = State.LeanLeft;
            }
        }

        bodyPlayer.setTransform((this.getX() + this.getWidth() / 2) * WorldGame.PIXELS_TO_METERS, (this.getY() + this.getHeight() / 2) * WorldGame.PIXELS_TO_METERS, 0);
    }

    private Texture getTextureRegion() {
        switch (state) {
            case Jump:
                return regions[0][0].getTexture();
            case Dead:
                return regions[3][0].getTexture();
            case Fall:
                return regions[4][0].getTexture();
            case LeanLeft:
                return regions[1][0].getTexture();
            case LeanRight:
                return regions[2][0].getTexture();
            case MagnetJump:
                return regions[5][0].getTexture();
            case ArmoredJump:
                return regions[6][0].getTexture();
            case Copter:
                return regions[7][0].getTexture();
            default:
                return regions[0][0].getTexture();
        }

    }

    public TextureRegion render() {

        switch (state) {
            case Jump:
                return jump.getKeyFrame(stateTime);
            case Dead:
                return dead.getKeyFrame(stateTime);
            case Fall:
                return fall.getKeyFrame(stateTime);
            case LeanLeft:
                return leanLeft.getKeyFrame(stateTime);
            case LeanRight:
                return leanRight.getKeyFrame(stateTime);
            case Rocket:
                return rocket.getKeyFrame(stateTime);
            case MagnetJump:
                return magnetJump.getKeyFrame(stateTime);
            case ArmoredJump:
                return armoredJump.getKeyFrame(stateTime);
            case Copter:
                return copter.getKeyFrame(stateTime);
            default:
                return jump.getKeyFrame(stateTime);
        }
    }


    private void createPhysics() {

        PolygonShape jackShape = new PolygonShape();
        jackShape.set(new Vector2[]{
                new Vector2(0, this.getHeight() / 2 * WorldGame.PIXELS_TO_METERS),
                new Vector2(this.getWidth() / 2 * WorldGame.PIXELS_TO_METERS, -this.getHeight() / 2 * WorldGame.PIXELS_TO_METERS),
                new Vector2(-this.getWidth() / 2 * WorldGame.PIXELS_TO_METERS, -this.getHeight() / 2 * WorldGame.PIXELS_TO_METERS),

        });


        FixtureDef fixtureDefPlayer = new FixtureDef();
        fixtureDefPlayer.shape = jackShape;
        fixtureDefPlayer.density = 1f;
        fixtureDefPlayer.friction = 0.6f;
        fixtureDefPlayer.restitution = 0.5f;
        fixtureDefPlayer.filter.categoryBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);
        fixtureDefPlayer.filter.maskBits = myGame.filterCollision.getFilterCategory(FilterCollision.filterCategory.Player);


        BodyDef bodyDefPlayer = new BodyDef();
        bodyDefPlayer.type = BodyDef.BodyType.StaticBody;
        bodyDefPlayer.position.x = (this.getX() + this.getWidth() / 2) * WorldGame.PIXELS_TO_METERS;
        bodyDefPlayer.position.y = (this.getY() + this.getHeight() / 2) * WorldGame.PIXELS_TO_METERS;


        bodyPlayer = myGame.worldGame.getWorld().createBody(bodyDefPlayer);
        bodyPlayer.createFixture(fixtureDefPlayer);

        jackShape.dispose();

    }


    private void setDynamicBody() {
        bodyPlayer.setType(BodyDef.BodyType.DynamicBody);
    }


    private void setSizeTexture() {
        for (int i = 0; i < regions.length; i++) {
            for (int j = 0; j < regions[i].length; j++) {
                regions[i][j].setRegionWidth(50);
            }
        }
    }

    public float getStateTime() {
        return stateTime;
    }


}
