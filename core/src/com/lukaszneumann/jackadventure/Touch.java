package com.lukaszneumann.jackadventure;

/**
 * Created by Łukasz on 2014-05-29.
 */
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Touch {



    private static OrthographicCamera camera;
    private static Vector3 calcVector;


    public static void setCamera(OrthographicCamera camera)
    {
        Touch.camera = camera;
        calcVector = new Vector3(0, 0, 0);
    }


    public static OrthographicCamera getCamera()
    {
        return camera;
    }


    public static float calcX(int x)
    {
        calcVector.x = x;
        camera.unproject(calcVector);
        return  calcVector.x;
    }


    public static float calcY(int y)
    {
        calcVector.y = y;
        camera.unproject(calcVector);
        return  calcVector.y;
    }

}