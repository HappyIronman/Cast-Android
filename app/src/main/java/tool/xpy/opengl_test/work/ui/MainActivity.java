package tool.xpy.opengl_test.work.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import tool.xpy.opengl_test.R;
import tool.xpy.opengl_test.work.scene.Ball;
import tool.xpy.opengl_test.work.net.MyServer;
//****************************************************************
import tool.oyyw.opengl_test.work.MYViewPagerAdapter;
import tool.xpy.opengl_test.work.net.MySocket;
//****************************************************************
import tool.xpy.opengl_test.work.util.Constant;

public class MainActivity extends Activity {

    public class MyHandler extends Handler{
        public MyHandler(Looper l) {super(l);}
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String text = bundle.getString(Constant.MessageKey);
            Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
        }

        public void showMessage(final String text){
            post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public Ball ball = null;
    public MyServer myServer = null;
    //****************************************************************
    public MySocket mySocket = null;
    private String ip_string;
    private int port_num;
    //****************************************************************

    public MyRender leftRender = null;
    public MyRender rightRender = null;

    public GLSurfaceView videoView0 = null;
    public GLSurfaceView videoView1 = null;

    private void loadResource(){
        Constant.ball_bitmap = BitmapFactory.decodeResource(
                getResources(), R.mipmap.ball);
        Constant.route_bitmap = BitmapFactory.decodeResource(
                getResources(), R.mipmap.route2);
        Constant.stone_light_bitmap = BitmapFactory.decodeResource(
                getResources(), R.mipmap.stone_light);
        Constant.stone_dark_bitmap = BitmapFactory.decodeResource(
                getResources(), R.mipmap.stone_dark);
        Constant.grass_bitmap = BitmapFactory.decodeResource(
                getResources(), R.mipmap.grass);

    }

    private void initializeObject(){
        ball = new Ball();

        leftRender = new MyRender(true);
        rightRender = new MyRender(false);

        videoView0 = (GLSurfaceView)findViewById(R.id.surfaceView);
        videoView1 = (GLSurfaceView)findViewById(R.id.surfaceView2);

        videoView0.setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        videoView0.setRenderer(leftRender);

        videoView1.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        videoView1.setRenderer(rightRender);

        myServer = new MyServer(ball);

        Notifier.handler = new MyHandler(getMainLooper());

        View.OnClickListener reset = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftRender.replay();
                rightRender.replay();
            }
        };

        videoView0.setOnClickListener(reset);
        videoView1.setOnClickListener(reset);

        leftRender.setBall(ball);
        rightRender.setBall(ball);

        /**
         * 改于2016.6.30,添加滑动功能.添加失败》。。。黑影。。。
         */
        /*
        //**********************************************************
        //begin
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        View view1 = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.layout1, null);
        ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        MYViewPagerAdapter adapter = new MYViewPagerAdapter();
        adapter.setViews(views);
        viewPager.setAdapter(adapter);
        //**********************************************************
        */
    }

    //******************************************************************************
    public void connectDialog () {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog dialog;
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.connect, null);
        final EditText ip = (EditText)view.findViewById(R.id.edit_ip);
        final EditText port = (EditText)view.findViewById(R.id.edit_port);
        final Button confirm = (Button)view.findViewById(R.id.id_confirm);
        final Button cancel = (Button)view.findViewById(R.id.id_cancel);
        builder.setView(view);
        dialog=builder.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip_string = ip.getText().toString().trim();
                String port_string = port.getText().toString().trim();
                if (ip_string.equals("")) {
                    Notifier.write("Please enter ip");
                } else if (port_string.equals("")) {
                    Notifier.write("Please enter port");
                } else {
                    port_num = Integer.valueOf(port_string).intValue();

                    mySocket = new MySocket(ball, false, ip_string, port_num);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (mySocket.connect() == true) {
                                Notifier.write("Connect succeed");
                                dialog.dismiss();
                                mySocket.getData();
                            }
                        }
                    }).start();

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    //***********************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadResource();

        //videoView.setEGLContextClientVersion(2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //set window to full screen

        setContentView(R.layout.activity_main);
        initializeObject();
        //*******************************************************************
        connectDialog();
        //*******************************************************************
    }


    //**********************************************************************
    //以下代码没有什么卵用
    //**********************************************************************
    @Override
    protected void onPause() {
        super.onPause();
        videoView0.onPause();
        videoView1.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView0.onResume();
        videoView1.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(Constant.ball_bitmap != null)
            Constant.ball_bitmap.recycle();
        myServer.stop();
        System.exit(0);
    }

}




