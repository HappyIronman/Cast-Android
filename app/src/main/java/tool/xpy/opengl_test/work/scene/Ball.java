package tool.xpy.opengl_test.work.scene;

import javax.microedition.khronos.opengles.GL10;

import tool.xpy.opengl_test.work.util.Constant;

/**
 * Created by root on 15-10-13.
 * Commented by oyyw on 16-6-1.
 */
public class Ball implements Drawable{

    //phy value

    private float x = 1, y = 8, z = 2;              // coordinate 坐标
    private float vx = 0, vy = 8, vz = 8;           // velocity    速度
    private float ax = 0, ay = 0, az = -9.8f;       // accelerated  加速度

    private long lastTime = -1;
    private double d;

    // phy value

    private XRect xRect;
    private float objectSize = .5f;

    public Ball(){
        lastTime = System.currentTimeMillis();
        xRect = new XRect(XRect.XZ_PANEL, x, y, z, objectSize, objectSize);
        xRect.setNormalVector(0, 1, 0);
    }

    public void loadTexture(GL10 gl, boolean leftView){
        //call when render created
        xRect.loadTexture(Constant.ball_bitmap, gl, leftView);
    }

    public void next(double t){

        x += vx * t + 0.5 * ax * t * t;
        y += vy * t + 0.5 * ay * t * t;
        z += vz * t + 0.5 * az * t * t;

        vx += ax * t;
        vy += ay * t;
        vz += az * t;

        if(z < 0) {
            vz = -vz * 0.3f;
            vx = vx * 0.6f;
            vy = vy * 0.6f;
            z = 0;
        }

    }

    public void setA(float[] a_set){
        ax = a_set[0];
        ay = a_set[1];
        az = a_set[2];
    }

    public void setV(float[] v_set){
        vx = v_set[0];
        vy = v_set[1];
        vz = v_set[2];
    }

    public void setP(float[] p_set){
        x = p_set[0];
        y = p_set[1];
        z = p_set[2];
    }

    private synchronized void rebuild(){
        xRect.setData(XRect.XZ_PANEL, x, y, z, objectSize, objectSize);
    }

    public void draw(GL10 gl, boolean left){
        d = System.currentTimeMillis() - lastTime;
        if(d != 0){
            next(d / 1000.0 / Constant.timeScale);
            rebuild();
            lastTime = System.currentTimeMillis();
            //if(refreshWindow != null) refreshWindow.print(gl, left, toString());
        }
        xRect.draw(gl, left);
    }

    public void reset(){
        x = 1; y = 8; z = 2;
        vx = 0; vy = 8; vz = 8;
        ax = 0; ay = 0; az = -9.8f;
    }

    public String toString(){
        return "A(" + ax + "," + ay + "," + az + ")," +
                "V(" + vx + "," + vy + "," + vz + ")," +
                "P(" + x + "," + y + "," + z + ")";
    }

}
