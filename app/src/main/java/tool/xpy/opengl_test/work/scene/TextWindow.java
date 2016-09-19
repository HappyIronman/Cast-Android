package tool.xpy.opengl_test.work.scene;

import javax.microedition.khronos.opengles.GL10;

import tool.xpy.opengl_test.work.util.TextCanvas;

/**
 * Created by root on 15-11-11.
 */
public class TextWindow {

    private XRect xRect;

    public TextWindow(float x, float y, float z, float width, float height){
        xRect = new XRect(XRect.XZ_PANEL, x, y, z, width, height);
    }

    public void print(GL10 gl, boolean leftView, String text){
        xRect.changeTexture(gl, TextCanvas.getTextBitmap(text, 400, 100), leftView);
    }

    public void loadTexture(GL10 gl, boolean leftView){
        xRect.loadTexture(TextCanvas.getTextBitmap("LIVE", 400, 100), gl, leftView);
    }

    public void draw(GL10 gl, boolean leftView){
        xRect.draw(gl, leftView);
    }
}
