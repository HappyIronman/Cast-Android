package tool.xpy.opengl_test.work.scene;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by root on 15-10-13.
 */
public class Cube implements Drawable {

    private TQuate[] s = new TQuate[6];

    public Cube(float r, float g, float b){
        s[0] = new TQuate(TQuate.XY_PANEL);
        s[1] = new TQuate(TQuate.XY_PANEL);
        s[2] = new TQuate(TQuate.YZ_PANEL);
        s[3] = new TQuate(TQuate.YZ_PANEL);
        //s[2].setColor(0.4f, 0.4f, 0.4f, 1);
        //s[3].setColor(0.4f, 0.4f, 0.4f, 1);
        s[4] = new TQuate(TQuate.XZ_PANEL);
        s[5] = new TQuate(TQuate.XZ_PANEL);

        s[0].setNormal_vector(0, 0, -1);
        s[1].setNormal_vector(0, 0, 1);
        s[2].setNormal_vector(-1, 0, 0);
        s[3].setNormal_vector(1, 0, 0);
        s[4].setNormal_vector(0, -1, 0);
        s[5].setNormal_vector(0, 1, 0);

        s[0].setColor(r, g, b, 1);
        s[1].setColor(r, g, b, 1);
        s[2].setColor(r, g, b, 1);
        s[3].setColor(r, g, b, 1);
        s[4].setColor(r, g, b, 1);
        s[5].setColor(r, g, b, 1);
    }

    public void rebuild(float x, float y, float z, float sx, float sy, float sz){

        float base_x = x;
        float base_y = y;
        float base_z = z;

        float vect_x = base_x + sx;
        float vect_y = base_y + sy;
        float vect_z = base_z + sz;

        s[0].rebuild(base_x, base_y, base_z, sx, sy);
        s[1].rebuild(base_x, base_y, vect_z, sx, sy);



        s[2].rebuild(base_x, base_y, base_z, sy, sz);
        s[3].rebuild(vect_x, base_y, base_z, sy, sz);
        //s[2].setColor(0.4f, 0.4f, 0.4f, 1);
        //s[3].setColor(0.4f, 0.4f, 0.4f, 1);

        s[4].rebuild(base_x, base_y, base_z, sx, sz);
        s[5].rebuild(base_x, vect_y, base_z, sx, sz);
        //s[4].setColor(0.3f, 0.3f, 0.3f, 1);
        //s[5].setColor(0.3f, 0.3f, 0.3f, 1);


    }


    public void draw(GL10 gl, boolean leftView){
        for(int i = 0; i < 6; i ++)
            s[i].draw(gl, leftView);
    }

    public TQuate[] getS(){
        return s;
    }
}
