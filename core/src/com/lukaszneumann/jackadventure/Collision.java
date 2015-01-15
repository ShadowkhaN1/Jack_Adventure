package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by Lukasz on 2014-11-27.
 */
public class Collision extends Rectangle {


    public Collision() {
    }


    public boolean isCollision(float x1, float y1, float width1, float height1, float x2, float y2, float width2, float height2) {

        if (x1 > x2 && x1 < x2 + width2 && y1 > y2 && y1 < y2 + height2) {
            return true;
        }
        if ((x1 + width1) > x2 && (x1 + width1) < (x2 + width2) && y1 > y2 && y1 < (y2 + height2)) {
            return true;
        }
        if (x1 > x2 && x1 < (x2 + width2) && (y1 + height1) > y2 && (y1 + height1) < (y2 + height2)) {
            return true;
        }
        if ((x1 + width1) > x2 && (x1 + width1) < (x2 + width2) && (y1 + height1) > y2 && (y1 + height1) < (y2 + height2)) {
            return true;
        }
        if (x1 > x2 && x1 < (x2 + width2) && ((y1 + height1) / 2) > y2 && ((y1 + height1) / 2) < (y2 + height2)) {
            return true;
        }
        if ((x1 + width1) > x2 && (x1 + width1) < (x2 + width2) && ((y1 + height1) / 2) > y2 && ((y1 + height1) / 2) < (y2 + height2)) {
            return true;
        }


        return false;
    }

    public boolean candy(ArrayList<Candy> candy) {
        for (int i = 0; i < candy.size(); i++) {
            if (candy.get(candy.size() - 1).getBoundingRectangle().overlaps(candy.get(i).getBoundingRectangle())) {
                return true;
            }
        }
        return false;
    }


    public boolean bat(ArrayList<GameObjectAnimation> candy) {
        for (int i = 0; i < candy.size(); i++) {
            if (candy.get(candy.size() - 1).getBoundingRectangle().overlaps(candy.get(i).getBoundingRectangle())) {
                return true;
            }
        }
        return false;
    }


}
