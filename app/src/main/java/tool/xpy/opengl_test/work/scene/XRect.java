package tool.xpy.opengl_test.work.scene;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import tool.xpy.opengl_test.work.util.BufferUtil;

/**
 * Created by root on 15-11-8.
 * Commented by oyyw on 16-6-1
 */
public class XRect implements Drawable{

    public final static int XY_PANEL = 0, YZ_PANEL = 1, XZ_PANEL = 2;

    private FloatBuffer vertexSet = BufferUtil.toFloatBuffer(4 * 3);
    private FloatBuffer textureOrder;           //texture:质地

    private int texture[] = {-1, -1};
    private boolean textureExist = false;     //textureExist: 质地是否存在

    private float r = .5f, g = .5f, b = .5f, a = 1;
    private float nx = 0, ny = 0, nz = 1;

    private float[] mat_ambient;
    private float[] mat_specular;       //specular:高光
    private float[] mat_diffuse;        //diffuse :漫反射

    public void setValue_Ambient(float[] mat_ambient){
        this.mat_ambient = mat_ambient;
    }

    public void setValue_Specular(float[] mat_specular){
        this.mat_specular = mat_specular;
    }

    public void setValue_Diffuse(float[] mat_diffuse){
        this.mat_diffuse = mat_diffuse;
    }

    /**
     * 设置重复的矩形
     * @param h
     * @param v
     * @param reverse
     */
    public void setRepeatMatrix(float h, float v, boolean reverse){
        textureOrder = BufferUtil.getRepeatBuffer(h, v, reverse);
    }

    public void loadTexture(Bitmap bitmap, GL10 gl, boolean leftView){

        int index = leftView ? 0: 1;
        if(texture[index] != -1) return;

        int textures[] = new int[1];
        gl.glGenTextures(1, textures, 0);
        texture[index] = textures[0];
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[index]);

        //set texture func when closer or further
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                GL10.GL_REPEAT);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        textureExist = true;
    }

    public void changeTexture(GL10 gl, Bitmap bitmap, boolean leftView){
        int index = leftView ? 0: 1;
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[index]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);


    }

    private void addVertex(float x, float y, float z){
        vertexSet.put(x);
        vertexSet.put(y);
        vertexSet.put(z);
    }

    public void setData(int type, float x, float y, float z, float vec0, float vec1){
        vertexSet.clear();
        vertexSet.position(0);
        addVertex(x, y, z);
        switch(type){
            case XY_PANEL:
                addVertex(x + vec0, y, z);
                addVertex(x, y + vec1, z);
                addVertex(x + vec0, y + vec1, z);
                break;
            case YZ_PANEL:
                addVertex(x, y + vec0, z);
                addVertex(x, y, z + vec1);
                addVertex(x, y + vec0, z + vec1);
                break;
            case XZ_PANEL:
                addVertex(x + vec0, y, z);
                addVertex(x, y, z + vec1);
                addVertex(x + vec0, y, z + vec1);
                break;
        }
        vertexSet.position(0);
    }

    public XRect(int type, float x, float y, float z, float v0, float v1){
        setData(type, x, y, z, v0, v1);
        setRepeatMatrix(1, 1, false);

        setValue_Ambient(new float[]{.5f, .5f, .5f, 1});
        setValue_Diffuse(new float[]{.5f, .5f, .5f, 1});
        setValue_Specular(new float[]{.2f, .2f, .18f, 1});
    }

    public void setColor(float r, float g, float b, float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void setNormalVector(float nx, float ny, float nz){
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
    }

    public void draw(GL10 gl, boolean leftView){

        gl.glColor4f(r, g, b, a);
        gl.glNormal3f(nx, ny, nz);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambient, 0);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_diffuse, 0);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_specular, 0);

        if(textureExist) {

            gl.glBindTexture(GL10.GL_TEXTURE_2D, leftView ? texture[0] : texture[1]);

            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glEnable(GL10.GL_TEXTURE_2D);

            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexSet);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureOrder);


            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

            //texture unbind
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glDisable(GL10.GL_TEXTURE_2D);
            gl.glDisable(GL10.GL_BLEND);
        } else {
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexSet);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        }
    }
}
