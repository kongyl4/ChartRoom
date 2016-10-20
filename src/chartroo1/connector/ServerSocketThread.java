package chartroo1.connector;

import chartroo1.main.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kongyl4 on 2016/10/19.
 */
public class ServerSocketThread implements Runnable {
    private ServerSocket serverSocket=null;
    private Socket socket=null;
    @Override
    public void run() {
        while(true){
            try {
                socket=serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
