package tool.xpy.opengl_test.work.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import tool.xpy.opengl_test.R;

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


        LayoutInflater inflater = LayoutInflater.from(MainActivity.main_activity);
        View view = inflater.inflate(R.layout.toastview,null,false);
        TextView l_toast = (TextView) view.findViewById(R.id.left_toast);
        TextView r_toast = (TextView) view.findViewById(R.id.right_toast);

        content="投掷信息：\n x方向初速度："+vx+"\n y方向初速度："+vy+" \n z方向初速度："+vz+"\n\n"
                +"飞行信息：\n x方向飞行距离："+x+"\n y方向飞行距离："+y+" \n";
        l_toast.setText(content);
        r_toast.setText(content);
        Toast toast = new Toast(MainActivity.main_activity);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();


       // Toast.makeText(context,content, Toast.LENGTH_LONG).show();
    }

}
