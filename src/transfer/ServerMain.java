package transfer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ServerMain {
    //psvm

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10002);
        while (true){
            Socket socket = serverSocket.accept();
            if(socket.isConnected()){
                System.out.println("****");
                ServerInputThread serverInputThread = new ServerInputThread(socket);
                Thread t1=new Thread(serverInputThread);
                t1.start();
                Thread t2=new Thread(new ServerOutputThread(socket));
                t2.start();
            }

        }

    }

}
