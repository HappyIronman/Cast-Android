package tool.xpy.opengl_test.work.scene;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by root on 15-10-13.
 */
public interface Drawable {
    void draw(GL10 gl, boolean leftView);
}
