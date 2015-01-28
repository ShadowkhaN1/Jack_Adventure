package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MyGame extends ApplicationAdapter {


    public static float WIDTH_SCREEN = 0;
    public static float HEIGHT_SCREEN = 0;

    SpriteBatch batch;
    OrthographicCamera camera;
    Content content;
    SoundGame soundGame;
    MusicGame musicGame;
    InitializeGameContent initializeGame;
    FilterCollision filterCollision;
    DataStorage dataStorage;
    AssetsHelper assetsHelper;


    Touch touch;
    float deltaTime = 0;
    Screen screen;
    boolean isOver = false;
    boolean isPause = false;
    boolean isSound = true;
    boolean isMusic = true;
    boolean isGameOver = false;


    @Override
    public void create() {

        batch = new SpriteBatch();


        assetsHelper = new AssetsHelper();

        WIDTH_SCREEN = Gdx.graphics.getWidth();
        HEIGHT_SCREEN = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH_SCREEN, HEIGHT_SCREEN);
        //camera.update();

        dataStorage = new DataStorage();


        soundGame = new SoundGame(this);
        musicGame = new MusicGame(this);
        musicGame.getMusicGame().setLooping(true);
        musicGame.getMusicGame().setVolume(0.4f);


        filterCollision = new FilterCollision();

        touch = new Touch();
        touch.setCamera(camera);

        content = new Content(assetsHelper.usesDpi);
        initializeGame = new InitializeGameContent(assetsHelper.usesDpi);


        Gdx.input.setCatchBackKey(true);


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


        if (isMusic) {
            musicGame.getMusicGame().play();

        } else {
            musicGame.getMusicGame().stop();
        }


        System.out.println("FPS:" + Gdx.graphics.getFramesPerSecond());

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
        batch.dispose();
    }

}



