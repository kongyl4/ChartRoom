package chartroo1.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kongyl4 on 2016/10/19.
 */
public class ServerSocketConnector {

    private static final int port=55000;
    ServerSocket serverSocket=null;
    Socket socket=null;
    Map<String,Socket> socketMap=new HashMap<String,Socket>();
    public ServerSocketConnector() {
    }
    public ServerSocket serverSocketOpen(int port){

        try {
            serverSocket=new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  serverSocket;
    }
    public void close(Socket socket){//serverSocket用关闭么
        if(socket!=null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private class ServerSocketThread implements Runnable {
        @Override
        public void run() {
            while(true){
                try {
                    socket=serverSocket.accept();
                    if(socket.isConnected()){
                        //把该socket添加到server的socket列表中
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
