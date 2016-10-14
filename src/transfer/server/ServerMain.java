package transfer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import transfer.common.Config;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ServerMain {
    //psvm

    private static final ExecutorService inputThreadPool = Executors.newCachedThreadPool();
    private static final ExecutorService outputThreadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Config.PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                if (socket.isConnected()) {
                    System.out.println("****");
                    inputThreadPool.execute(new ServerInputThread(socket));
                    outputThreadPool.execute(new ServerOutputThread(socket));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputThreadPool.shutdown();
            outputThreadPool.shutdown();
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
