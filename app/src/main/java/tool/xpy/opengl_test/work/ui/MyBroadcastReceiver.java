package tool.xpy.opengl_test.work.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by dell on 10/4/2016.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
     private String x,y,z,vx,vy,vz;
     private String content="";
    java.text.DecimalFormat df=new java.text.DecimalFormat("#.##");

    /* 覆写该方法，对广播事件执行响应的动作  */
    public void onReceive(Context context, Intent intent) {
        x=df.format(intent.getFloatExtra("x",0));
        y=df.format(intent.getFloatExtra("y",0));
        vx=df.format(intent.getFloatExtra("vx",0));
        vy=df.format(intent.getFloatExtra("vy",0));
        vz=df.format(intent.getFloatExtra("vz",0));

        content="投掷信息：\n x方向初速度："+vx+"\n y方向初速度："+vy+" \n z方向初速度："+vz+"\n"
                +"飞行信息：\n x方向飞行距离："+x+"\n y方向飞行距离："+y+" \n";
        Toast.makeText(context,content, Toast.LENGTH_LONG).show();
    }

}
