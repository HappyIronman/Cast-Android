package tool.xpy.opengl_test.work.ui;

import javax.microedition.khronos.opengles.GL10;

import tool.xpy.opengl_test.work.scene.Ball;
import tool.xpy.opengl_test.work.scene.PointLight;
import tool.xpy.opengl_test.work.scene.Route;

/**
 * Created by root on 15-10-14.
 */
public class Scene {

    private Ball ball;
    private Route route;

    private boolean leftView = false;

    private PointLight pointLight = new PointLight();

    private float route_start_x = -2, route_width = 8;

    public Scene(boolean leftView){
        this.leftView = leftView;
        initScene();
    }

    public void initScene(){
        //build graphic scene
        route = new Route(route_start_x, route_width);
    }

    public void loadTexture(GL10 gl){
        ball.loadTexture(gl, leftView);
        route.loadTexture(gl, leftView);
    }

    public void draw(GL10 gl){
        pointLight.enable(gl);

        route.draw(gl, leftView);
        ball.draw(gl, leftView);

        pointLight.disable(gl);
    }

    public void setBall(Ball ball){
        this.ball = ball;
    }

    public void resetBall(){
        ball.reset();
    }

}
