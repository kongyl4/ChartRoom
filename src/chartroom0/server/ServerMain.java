package chartroom0.server;

import chartroom0.common.Config;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                    command.addSocket(socket);
                    //System.out.println(command.getSocketList().size()+"上线");
                    ServerInputThread sit = new ServerInputThread(socket);
                    inputThreadPool.execute(sit);
                    String name=null;
                    Socket socket0=null;
                    for (Map.Entry<String, Socket> entry : sit.getMapList().entrySet()){
                         name=entry.getKey();
                         socket0=entry.getValue();
                    }
                    for (Map.Entry<String, Socket> entry : command.getMapList().entrySet()){
                        sit.addMapList(entry.getKey(),entry.getValue());
                        DataOutputStream os=new DataOutputStream(entry.getValue().getOutputStream());
                        //sit.addMapList();
                        os.writeUTF(entry.getKey()+"上线");
                    }

                    for (ServerInputThread serverInputThread : serverInputThreadList) {
                        serverInputThread.addMapList(name,socket0);
                    }
                    serverInputThreadList.add(sit);
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
