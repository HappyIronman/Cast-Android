package tool.xpy.opengl_test.work.ui;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import tool.xpy.opengl_test.work.scene.Ball;
import tool.xpy.opengl_test.work.util.Constant;

/**
 * Created by XPY on 15-10-10.
 */


/**
 *  在Android下，要想使用 OpenGL ES API 来创建和操纵图形，需要使用：GLSurfaceView和 GLSurfaceView.Renderer
 *  1. GLSurfaceView：
 *      这是一个视图类，可以直接调用 OpenGL API在上面绘制图形和操纵物体
 *  2. GLSurfaceView.Renderer
 *      这个接口定义了OpenGL的GLSurfaceView中绘制图形所需要的方法，我们必须在一个单独的类中实现这个接口，这里指的是MyRender
 *      需要实现的方法:
 *          onSurfaceCreated(): 系统在创建GLSurfaceView时调用它一次, 用来设置opengl的环境变量
 *          onDrawFrame():      系统在每次重绘GLSurfaceView时调用这个方法，用来完成绘制图形操作
 *          onSurfaceChanged(): 系统在GLSurfaceView的几何属性发生改变时调用该方法，包括大小或是设备屏幕的方向发生变化。
 *
 *  以上具体参照:http://www.2cto.com/kf/201205/129839.html
 */
public class MyRender implements GLSurfaceView.Renderer {

    private Scene myScene = null;

    public float eyeX = 0, eyeY = 3, eyeZ = 1.8f;
    public float viewX = 0, viewY = 1000, viewZ = eyeZ;
    private float[] sky_color = {.6f, .6f, 1, 0};

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(sky_color[0], sky_color[1], sky_color[2], sky_color[3]);
        gl.glClearDepthf(1);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        myScene.loadTexture(gl);
    }

    public MyRender(boolean leftView){
        super();
        if(leftView) eyeX += Constant.eye_b;
        myScene = new Scene(leftView);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45, (float) width / height, 0.1f, 1000);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, eyeX, eyeY, eyeZ,
                viewX, viewY, viewZ, 0, 0, 1);
        myScene.draw(gl);
        gl.glFinish();
    }

    public void replay(){
        myScene.resetBall();
    }

    public void setBall(Ball ball){
        myScene.setBall(ball);
    }

}
