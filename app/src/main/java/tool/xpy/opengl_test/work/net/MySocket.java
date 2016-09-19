package tool.xpy.opengl_test.work.net;

import android.util.Log;

import java.io.DataInputStream;
import java.net.Socket;

import tool.xpy.opengl_test.work.scene.Ball;
import tool.xpy.opengl_test.work.ui.Notifier;

/**
 * Created by Andrew on 2016/6/2.
 */
public class MySocket {

    private Socket currentClient = null;
    private String ip;
    private int port;
    private DataInputStream dataInputStream = null;

    private boolean errorFlag = false, once = false;
    private float[] receiveBuffer = new float[3];
    private Ball ball;


    public MySocket(Ball ball, boolean once, String ip, int port){
        this.ball = ball;
        this.once = once;
        this.ip = ip;
        this.port = port;
    }

    private boolean nextBuffer(){
            if(errorFlag) return false;
            try{
                //read four bytes and transfer to float from dataInputStream
                receiveBuffer[0] = dataInputStream.readFloat();
                receiveBuffer[1] = dataInputStream.readFloat();
                receiveBuffer[2] = dataInputStream.readFloat();
                return true;
            }catch(Exception e){
                errorFlag = true;
                return false;
            }
        }

    public boolean connect(){
        try{
            currentClient = new Socket(this.ip, this.port);
            dataInputStream = new DataInputStream(currentClient.getInputStream());
            return true;
        }catch(Exception e){
            Notifier.write("Cannot connect with the server");
            return false;
        }
    }

    public void getData(){
        while(!currentClient.isClosed() && !errorFlag){
            if(nextBuffer()) ball.setA(receiveBuffer);
            if(nextBuffer()) ball.setV(receiveBuffer);
            if(nextBuffer()) ball.setP(receiveBuffer);
            if(once) break;
        }
    }

}
