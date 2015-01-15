package com.lukaszneumann.jackadventure;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukasz on 2014-11-11.
 */
public class Background extends Sprite {

    static float WIDTH = 0;
    static float HEIGHT = 0;
    static float MAX_VELOCITY = 1500;

    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();


    public Background(Texture texture) {
        super(texture);

        WIDTH = getTexture().getWidth() * 0.01f;
        HEIGHT = getTexture().getHeight() * 0.01f;
        setSize(WIDTH, HEIGHT);

        position.add(0, 0);
        setPosition(position.x, position.y);

    }


    public void update(float deltaTime) {

        velocity.y = -MAX_VELOCITY;
        velocity.scl(deltaTime);
        position.add(velocity);
        setPosition(position.x, position.y);

    }
}
