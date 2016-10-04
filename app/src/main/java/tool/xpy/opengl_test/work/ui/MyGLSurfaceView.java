package tool.xpy.opengl_test.work.ui;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by dell on 9/20/2016.
 */
public class MyGLSurfaceView extends GLSurfaceView implements SensorEventListener{
    public MyRender mrender;
    private float ini_angle;
    private int flag;
    public SensorManager msensorManager;
    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context,attrs);
        // TODO Auto-generated constructor stub
        msensorManager=(SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mrender=null;
        ini_angle=0f;
        flag=0;
        enablesensor();
    }


    public void setMrender(MyRender r){
        mrender=r;
        setRenderer(mrender);
    }

      public void enablesensor() {
        msensorManager.registerListener(this, msensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);
          msensorManager.registerListener(this, msensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);

    };
    public void disablesensor()
    {
        msensorManager.unregisterListener(this);
    }


    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent arg0) {
        // TODO Auto-generated method stub
        if(arg0.sensor.getType()==Sensor.TYPE_ORIENTATION) {
            float[] values = arg0.values;
            float angle = values[0];
            if(flag==0)
            {
                ini_angle=angle;
                flag=1;
            }
            else {
                mrender.viewX = ( angle-ini_angle) *10.0f;
                Log.e("QQQ", "onSensorChanged: " + angle);

            }


        }
        else if(arg0.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            float[] values = arg0.values;

            float angle = values[2];
            if(angle>0) {
                mrender.eyeZ = 1.8f + (int) (angle/3);
                Log.e("QQQ", "onSensorChanged: " + angle);

            }
        }
    }
}
