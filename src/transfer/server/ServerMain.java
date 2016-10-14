package transfer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.corba.se.spi.activation.Server;
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
            ServerOutputThread command = new ServerOutputThread();
            outputThreadPool.execute(command);
            List<ServerInputThread> serverInputThreadList = new LinkedList<ServerInputThread>();
            while (true) {
                Socket socket = serverSocket.accept();
                if (socket.isConnected()) {
                    System.out.println("****");
                    command.addSocket(socket);
                    ServerInputThread sit = new ServerInputThread(socket);
                    for (Socket s : command.getSocketList()) {
                        sit.addSocket(s);
                    }
                    for (ServerInputThread serverInputThread : serverInputThreadList) {
                        serverInputThread.addSocket(socket);
                    }
                    serverInputThreadList.add(sit);
                    inputThreadPool.execute(sit);
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
