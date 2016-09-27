package tool.xpy.opengl_test.work.util;

import android.graphics.Bitmap;

/**
 * Created by root on 15-10-13.
 */
public class Constant {


    public final static float FrontLength = 250;
    public final static int PORT = 6645;

    public final static boolean ServerDebugText = false;
    //****************************************************************
    public final static boolean ClientDebugText = true;
    //****************************************************************

    public final static String MessageKey = "message";

    public static double timeScale = 1;
    public static float eye_b = .07f;

    public static Bitmap ball_bitmap;
    public static Bitmap route_bitmap;
    public static Bitmap stone_dark_bitmap;
    public static Bitmap stone_light_bitmap;
    public static Bitmap grass_bitmap;
    public static Bitmap tree_bitmap;

    public static final float[] textureOrderF = new float[]{
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
    }; //  the order of texture

}