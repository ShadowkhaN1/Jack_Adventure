package com.lukaszneumann.jackadventure;

import com.badlogic.gdx.Gdx;

/**
 * Created by Lukasz on 2015-01-22.
 */
public class AssetsHelper {


    static final String usesLdpi = "ldpi";
    static final String usesMdpi = "mdpi";
    static final String usesHdpi = "hdpi";
    static final String usesXdpi = "xhdpi";

    static String usesDpi;


    public AssetsHelper() {

        fixAssetResolutions();
    }

    private static void fixAssetResolutions() {


        float landscapeWidth = Gdx.graphics.getWidth();
        //float landscapeHeight=Gdx.graphics.getHeight();
        float screenDensity = Gdx.graphics.getDensity();
        /*
        System.out.println("density:"+screenDensity);
		System.out.println("width:"+landscapeWidth+" height:"+landscapeHeight);
		System.out.println("PpcX:"+Gdx.graphics.getPpcX()+" PpcY:"+Gdx.graphics.getPpcY());
		System.out.println("PpiX:"+Gdx.graphics.getPpiX()+" PpiY:"+Gdx.graphics.getPpiY());
		*/
        if (landscapeWidth <= 320) {
            if (screenDensity <= 1.00f) {
                usesDpi = usesLdpi;
            } else {
                usesDpi = usesMdpi;
            }
        } else if (landscapeWidth <= 480) {
            if (screenDensity < 1.00f) {
                usesDpi = usesLdpi;
            } else if (screenDensity == 1.00f) {
                usesDpi = usesMdpi;
            } else if (screenDensity >= 2.00f) {
                usesDpi = usesXdpi;
            } else {
                usesDpi = usesHdpi;
            }
        } else if (landscapeWidth <= 854) {
            if (screenDensity <= 1.00f) {
                usesDpi = usesHdpi;
            } else {
                usesDpi = usesXdpi;
            }
        } else {
            if (screenDensity < 1.00f) {
                usesDpi = usesHdpi;
            } else {
                usesDpi = usesXdpi;
            }
        }

//        if (usesDpi == usesLdpi) {
//            assumedWidth = 320;
//            assumedHeight = 240;
//        } else if (usesDpi == usesMdpi) {
//            assumedWidth = 480;
//            assumedHeight = 320;
//        } else if (usesDpi == usesHdpi) {
//            assumedWidth = 800;
//            assumedHeight = 480;
//        } else if (usesDpi == usesXdpi) {
//            assumedWidth = 960;
//            assumedHeight = 640;
//        } else {
//            loadDefaults();
//            //System.out.println("No valid DPI, using default.");
//        }
    }


}
