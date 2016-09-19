package tool.xpy.opengl_test.work.scene;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import tool.xpy.opengl_test.work.util.BufferUtil;

/**
 * Created by root on 15-10-13.
 * Commented by oyyw on 16-5-31.
 */
public class TQuate implements Drawable {

    public final static int XY_PANEL = 0, YZ_PANEL = 1, XZ_PANEL = 2;       //代表 面平行于XY,YZ,XZ

    private int type = -1;
    private float r, g, b, alpha;
    private FloatBuffer coordinateBuffer = BufferUtil.toFloatBuffer(12);     //存放4个顶点的坐标值(x,y,z)

    private float[] mat_ambient = {.5f, .5f, .5f, 1};
    private float[] mat_specular = {.2f, .2f, .18f, 1};

    private int[] normal_vector = {0, 0, 1};


    public TQuate(int type, float s_x, float s_y, float s_z, float vec0, float vec1){
        this.type = type;
        setColor(0.5f, 0.5f, 0.5f, 1);
        rebuild(s_x, s_y, s_z, vec0, vec1);
    }

    public TQuate(int type){
        this.type = type;
        setColor(0.5f, 0.5f, 0.5f, 1);
    }

    public void setColor(float r, float g, float b, float alpha){
        mat_ambient[0] = this.r = r;
        mat_ambient[1] = this.g = g;
        mat_ambient[2] = this.b = b;
        mat_ambient[3] = this.alpha = alpha;
    }

    private void putPoint(float x, float y, float z){
        coordinateBuffer.put(x);
        coordinateBuffer.put(y);
        coordinateBuffer.put(z);
    }

    public void setNormal_vector(int x, int y, int z){
        normal_vector[0] = x;
        normal_vector[1] = y;
        normal_vector[2] = z;
    }

    public void rebuild(float x, float y, float z, float vec0, float vec1){
        coordinateBuffer.clear();
        putPoint(x, y, z);
        switch(type){
            case XY_PANEL:
                putPoint(x + vec0, y, z);
                putPoint(x, y + vec1, z);
                putPoint(x + vec0, y + vec1, z);
                break;
            case YZ_PANEL:
                putPoint(x, y + vec0, z);
                putPoint(x, y, z + vec1);
                putPoint(x, y + vec0, z + vec1);
                break;
            case XZ_PANEL:
                putPoint(x + vec0, y, z);
                putPoint(x, y, z + vec1);
                putPoint(x + vec0, y, z + vec1);
                break;
        }
        coordinateBuffer.position(0);
    }

    public void draw(GL10 gl, boolean leftView) {
        //gl.glLoadIdentity();
        gl.glColor4f(r, g, b, alpha);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambient, 0);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_specular, 0);

        /**
         * GL10.glVertexPointer(int size, int type, int stride, Buffer pointer)
         * size : 代表每个顶点包含几个坐标参数
         * type : 是一个枚举值，可以为 GL_FLOAT和 GL_FIXED,浮点数可对应java 的 float,要求pointer为 FloatBuffer
         * stride :指每个元素之间，间隔多少个值。加入buffer中坐标点数值间紧密相连，那么stride就是0
         * pointer ：存储坐标点
         * 详情参考 : http://blog.csdn.net/fr_han/article/details/9995935
         */
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, coordinateBuffer);
        gl.glNormal3x(normal_vector[0], normal_vector[1], normal_vector[2]);

        /**
         * glDrawArrays (GLenum mode, GLint first, GLsizei count)
         * mode : 绘制方式，这里GL10.GL_TRIANGLE_STRIP具体指绘制三角形的方式
         * first : 从数组缓存中的哪一位开始绘制，一般为0
         * count : 数组中定点的数量，这里为4，即绘制两个三角形，即面
         * 详情参考 : 参考:http://blog.csdn.net/xiajun07061225/article/details/7455283
         */
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glFinish();
    }
}
