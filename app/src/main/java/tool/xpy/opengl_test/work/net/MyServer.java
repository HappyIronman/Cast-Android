package tool.xpy.opengl_test.work.net;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import tool.xpy.opengl_test.work.scene.Ball;
import tool.xpy.opengl_test.work.ui.Notifier;
import tool.xpy.opengl_test.work.util.Constant;

/**
 * Created by root on 15-10-21.
 * Commented by oyyw on 16-6-1.
 */
public class MyServer {

    private class ListenThread extends Thread{

        private Socket currentClient = null;
        private DataInputStream dataInputStream = null;
        private DataOutputStream dataOutputStream = null;
        private ServerSocket serverSocket;

        private boolean errorFlag = false, once = false;

        private float[] receiveBuffer = new float[3];

        /**
         * class MyServer accept the request from client
         * @return the accept answer : true or false
         */
        private boolean accept(){
            if(errorFlag) return false;
            try{
                /**
                 * accept will block until client request connection
                 * accept will return a socket and client can connect with server by this socket.
                 */
                currentClient = serverSocket.accept();

                //Creates a DataInputStream that uses the specified underlying InputStream.
                dataInputStream = new DataInputStream(currentClient.getInputStream());
                dataOutputStream = new DataOutputStream(currentClient.getOutputStream());
                return true;
            }catch(Exception e){
                currentClient = null;
                dataInputStream = null;
                dataOutputStream = null;
                errorFlag = true;
            }
            return false;
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

        //persistent: once flag
        public void run(){

            if(!init()){
                Log.v("Server", "Cannot bind to port");
            }
            while(!serverSocket.isClosed() && !errorFlag){
                if(accept()){
                    if(Constant.ServerDebugText)
                        Notifier.write("Client accepted:" +
                                currentClient.getInetAddress().getHostAddress());
                    if(nextBuffer()) ball.setA(receiveBuffer);
                    if(nextBuffer()) ball.setV(receiveBuffer);
                    if(nextBuffer()) ball.setP(receiveBuffer);
                    if(Constant.ServerDebugText)
                        Notifier.write(ball.toString());
                }
                if(once) break;
            }
        }

        private boolean init(){
            try{
                serverSocket = new ServerSocket(Constant.PORT);
                return true;
            }catch(Exception e){
                Notifier.write("PORT IS BUSY");
                return false;
            }
        }

        public void setOnce(boolean once){
            this.once = once;
        }

        public void shutdown(){
            try{
                serverSocket.close();
            }catch(Exception e){

            }
        }
    }

    private Ball ball;
    private ListenThread listenThread = null;

    public MyServer(Ball ball){this.ball = ball;}

    public void start(boolean once){
        stop();
        listenThread = new ListenThread();
        listenThread.setOnce(once);
        listenThread.start();
    }

    public void stop(){
        if(listenThread != null)
            listenThread.shutdown();
    }
}
