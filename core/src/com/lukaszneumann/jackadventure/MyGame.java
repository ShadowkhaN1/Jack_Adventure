package com.lukaszneumann.jackadventure;


import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;


public class MyGame extends ApplicationAdapter {


    public static float WIDTH_SCREEN = 0;
    public static float HEIGHT_SCREEN = 0;

    SpriteBatch batch;
    OrthographicCamera camera;
    Content content;
    SoundGame soundGame;
    MusicGame musicGame;
    InitializeGameContent initializeGame;
    WorldGame worldGame;
    Box2DDebugRenderer renderer;
    RayHandler rayHandler;
    FilterCollision filterCollision;


    Touch touch;
    float deltaTime = 0;
    Screen screen;
    FileHandle fileScoreCandy;
    FileHandle fileScoreHeight;
    boolean isOver = false;
    boolean isPause = false;
    boolean isSound = true;
    boolean isMusic = false;
    boolean isReplay = false;


    BitmapFont font;


    @Override
    public void create() {

        batch = new SpriteBatch();

        worldGame = new WorldGame(new Vector2(0, -9.8f).scl(Gdx.graphics.getDeltaTime()), false);
        worldGame.getWorld().setContactListener(new MyContactListener());

        WIDTH_SCREEN = Gdx.graphics.getWidth(); //* worldGame.PIXELS_TO_METERS;
        HEIGHT_SCREEN = Gdx.graphics.getHeight(); //* worldGame.PIXELS_TO_METERS;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH_SCREEN, HEIGHT_SCREEN);
        //camera.update();


        soundGame = new SoundGame(this);
        musicGame = new MusicGame(this);
        musicGame.getMusicGame().setLooping(true);
        musicGame.getMusicGame().setVolume(0.5f);

        renderer = new Box2DDebugRenderer();

        //   RayHandler.setGammaCorrection(true);
        //RayHandler.useDiffuseLight(true);

        rayHandler = new RayHandler(worldGame.getWorld());

        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.setAmbientLight(1f);
        //rayHandler.isDiffuse = true;
        //rayHandler.setBlurNum(3);

        filterCollision = new FilterCollision();

        touch = new Touch();
        touch.setCamera(camera);

        content = new Content();
        initializeGame = new InitializeGameContent();
        // font = new BitmapFont(Gdx.files.internal("Font/text.fnt"));
        Gdx.input.setCatchBackKey(true);

//        fileScoreCandy = Gdx.files.external("ScoreCandy.txt");
//        fileScoreHeight = Gdx.files.external("ScoreHeight.txt");

        fileScoreCandy = Gdx.files.local("Files/ScoreCandy.txt");
        fileScoreHeight = Gdx.files.local("Files/ScoreHeight.txt");

        if (fileScoreCandy.readString() == null) {
            fileScoreCandy.writeString("0", false);
        }


        if (fileScoreHeight.readString() == null) {
            fileScoreHeight.writeString("0", false);
        }


        setScreen(new InitializeScreen(this));


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        deltaTime = Gdx.graphics.getDeltaTime();

        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();


        getScreen().render(deltaTime);
        // batch.begin();
        //font.draw(batch, Integer.toString(Integer.parseInt(fileScoreCandy.readString())), Gdx.graphics.getWidth() / 2 - font.getBounds(fileScoreCandy.readString()).width / 2, Gdx.graphics.getHeight() / 2);
        //font.draw(batch, Float.toString(Gdx.graphics.getDensity()), Gdx.graphics.getWidth() / 2 - font.getBounds(fileScoreCandy.readString()).width / 2, Gdx.graphics.getHeight() / 2);
        // batch.end();

//        if (isMusic == true) {
//            musicGame.getMusicGame().play();
//        } else {
//            musicGame.getMusicGame().stop();
//        }


//        System.out.println("FPS:" + Gdx.graphics.getFramesPerSecond());
        // System.out.println(Gdx.graphics.getDeltaTime());

    }


    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }

    public Content getContent() {

        if (content.getAssetManager().getProgress() <= 1) {
            content.getAssetManager().update();
        }
        return content;
    }

    public InitializeGameContent getInitializeGame() {

        if (initializeGame.getAssetManager().getProgress() <= 1) {
            initializeGame.getAssetManager().update();
        }
        return initializeGame;
    }

    @Override
    public void pause() {
        super.pause();

        this.isPause = true;
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    @Override
    public void dispose() {
        super.dispose();
        worldGame.getWorld().dispose();
        rayHandler.dispose();
        renderer.dispose();
        batch.dispose();
    }


}

