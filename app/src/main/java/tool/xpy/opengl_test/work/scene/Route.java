package tool.xpy.opengl_test.work.scene;

import javax.microedition.khronos.opengles.GL10;

import tool.xpy.opengl_test.work.util.Constant;

/**
 * Created by root on 15-10-13.
 */
public class Route implements Drawable{

    public final static float stone_width = .4f;
    public final static float grass_width = 40;

    private XRect mainRoute;
    private XRect stone_left0, stone_left1;
    private XRect stone_right0, stone_right1;
    private XRect left_grass, right_grass;

    private final float[] route_ambient = {.01f, .01f, .01f, 1};
    private final float[] grass_ambient = {.01f, .01f, .01f, 1};
    private final float[] stone_dark_ambient = {.01f, .01f, .01f, 1};
    private final float[] stone_light_ambient = {.01f, .01f, .01f, 1};

    private final float[] route_specular = {.01f, .01f, .01f, 1};
    private final float[] grass_specular = {.01f, .01f, .01f, 1};
    private final float[] stone_dark_specular = {.01f, .01f, .01f, 1};
    private final float[] stone_light_specular = {.01f, .01f, .01f, 1};

    private final float[] route_diffuse = {.8f, .8f, .8f, 1};
    private final float[] grass_diffuse = {.8f, .8f, .8f, 1};
    private final float[] stone_dark_diffuse = {.8f, .8f, .8f, 1};
    private final float[] stone_light_diffuse = {.8f, .8f, .8f, 1};

    private void configure(float route_x, float route_width){
        float route_h_times = route_width / 2;
        float route_v_times = Constant.FrontLength / 2;
        // the real rate of texture
        float stone_h_times = stone_width / 10;
        float stone_v_times = Constant.FrontLength / 10;
        // use scale value: .1
        float grass_h_times = grass_width / 2;
        float grass_v_times = Constant.FrontLength / 2;

        mainRoute.setValue_Ambient(route_ambient);
        stone_left0.setValue_Ambient(stone_dark_ambient);
        stone_left1.setValue_Ambient(stone_light_ambient);
        stone_right0.setValue_Ambient(stone_dark_ambient);
        stone_right1.setValue_Ambient(stone_light_ambient);
        left_grass.setValue_Ambient(grass_ambient);
        right_grass.setValue_Ambient(grass_ambient);

        mainRoute.setValue_Specular(route_specular);
        stone_left0.setValue_Specular(stone_dark_specular);
        stone_left1.setValue_Specular(stone_light_specular);
        stone_right0.setValue_Specular(stone_dark_specular);
        stone_right1.setValue_Specular(stone_light_specular);
        left_grass.setValue_Specular(grass_specular);
        right_grass.setValue_Specular(grass_specular);

        mainRoute.setValue_Diffuse(route_diffuse);
        stone_left0.setValue_Diffuse(stone_dark_diffuse);
        stone_left1.setValue_Diffuse(stone_light_diffuse);
        stone_right0.setValue_Diffuse(stone_dark_diffuse);
        stone_right1.setValue_Diffuse(stone_light_diffuse);
        left_grass.setValue_Diffuse(grass_diffuse);
        right_grass.setValue_Diffuse(grass_diffuse);

        mainRoute.setNormalVector(0, 0, 1);
        stone_left0.setNormalVector(1, 0, 0);
        stone_left1.setNormalVector(0, 0, 1);
        stone_right0.setNormalVector(1, 0, 0);
        stone_right1.setNormalVector(0, 0, 1);
        left_grass.setNormalVector(0, 0, 1);
        right_grass.setNormalVector(0, 0, 1);

        mainRoute.setRepeatMatrix(route_h_times, route_v_times, false);
        stone_left0.setRepeatMatrix(stone_h_times, stone_v_times, true);
        stone_left1.setRepeatMatrix(stone_h_times, stone_v_times, false);
        stone_right0.setRepeatMatrix(stone_h_times, stone_v_times, true);
        stone_right1.setRepeatMatrix(stone_h_times, stone_v_times, false);
        left_grass.setRepeatMatrix(grass_h_times, grass_v_times, false);
        right_grass.setRepeatMatrix(grass_h_times, grass_v_times, false);
    }


    /**
     * 绘制地图
     * @param route_x  地图的起始x
     * @param route_width   地图的宽度
     * @param route_length  地图的长度
     */
    public Route(float route_x, float route_width){

        //mainRoute,stone_left0,stone_left1具体指什么，参照 "地图.png"
        mainRoute = new XRect(XRect.XY_PANEL, route_x, 0, 0,
                route_width, Constant.FrontLength);

        stone_left0 = new XRect(XRect.YZ_PANEL, route_x, 0, 0, Constant.FrontLength,
                stone_width);
        stone_left1 = new XRect(XRect.XY_PANEL, route_x, 0, stone_width,
                -stone_width, Constant.FrontLength);
        stone_right0 = new XRect(XRect.YZ_PANEL, route_x + route_width, 0, 0,
                Constant.FrontLength, stone_width);
        stone_right1 = new XRect(XRect.XY_PANEL, route_x + route_width, 0, stone_width,
                stone_width, Constant.FrontLength);

        left_grass = new XRect(XRect.XY_PANEL, route_x - 0.1f, 0,
                0, -grass_width, Constant.FrontLength);
        right_grass = new XRect(XRect.XY_PANEL, route_x + route_width + 0.1f, 0,
                0, grass_width, Constant.FrontLength);

        configure(route_x, route_width);
    }

    public void loadTexture(GL10 gl, boolean leftView) {
        mainRoute.loadTexture(Constant.route_bitmap, gl, leftView);
        stone_left1.loadTexture(Constant.stone_light_bitmap, gl, leftView);
        stone_left0.loadTexture(Constant.stone_dark_bitmap, gl, leftView);
        stone_right1.loadTexture(Constant.stone_light_bitmap, gl, leftView);
        stone_right0.loadTexture(Constant.stone_dark_bitmap, gl, leftView);
        left_grass.loadTexture(Constant.grass_bitmap, gl, leftView);
        right_grass.loadTexture(Constant.grass_bitmap, gl, leftView);
    }

    public void draw(GL10 gl, boolean leftView){
        mainRoute.draw(gl, leftView);
        stone_left0.draw(gl, leftView);
        stone_left1.draw(gl, leftView);
        stone_right0.draw(gl, leftView);
        stone_right1.draw(gl, leftView);
        left_grass.draw(gl, leftView);
        right_grass.draw(gl, leftView);
        //line0.draw(gl, camera);
    }
}
