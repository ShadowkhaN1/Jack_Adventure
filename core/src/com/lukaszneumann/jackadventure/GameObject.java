package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukasz on 2014-12-01.
 */
public class GameObject extends Sprite {


    private float maxVelocity = 0;


    private Vector2 velocity = new Vector2();
    private Vector2 position = new Vector2();

    public GameObject() {
        super();
    }

    public GameObject(Texture texture) {
        super(texture);


    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public void update(float deltaTime) {

        velocity.y = -maxVelocity;
        velocity.scl(deltaTime);
        position.add(velocity);
        setPosition(position.x, position.y);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);

        position.x = x;
        position.y = y;

    }


}
