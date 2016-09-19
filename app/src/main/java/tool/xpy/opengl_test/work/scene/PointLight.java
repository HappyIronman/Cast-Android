package tool.xpy.opengl_test.work.scene;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by root on 15-10-28.
 */
public class PointLight {

    public final float[] ambient_light = {1f, 1f, 1f, 1};
    public final float[] coordinate = {100, 100, 200, 1};

    public void enable(GL10 gl){
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, ambient_light, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, coordinate, 0);
    }

    public void disable(GL10 gl){
        gl.glDisable(GL10.GL_LIGHT0);
        gl.glDisable(GL10.GL_LIGHTING);
    }
}
