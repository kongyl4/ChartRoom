package chat.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import chat.handler.HandlerObserverble;

/**
 * Created by wyx on 2016/10/17.
 */
public class ServerSocketConnector {
    private final int port;

    private ServerSocket serverSocket;

    private ExecutorService threadPool;

    private HandlerObserverble observerble;

    private volatile boolean start;

    private List<SocketConnector> socketConnectorList = new CopyOnWriteArrayList<SocketConnector>();

    public ServerSocketConnector(int port, HandlerObserverble observerble) {
        this.port = port;
        this.observerble = observerble;
        threadPool = Executors.newCachedThreadPool();
    }

    public boolean start() {
        try {
            serverSocket = new ServerSocket(port);
            threadPool.execute(new ServerSocketThread());
            start = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            shutdown();
            return false;
        }
    }

    public void shutdown() {
        start = false;
        close();
    }

    public void close() {
        for (SocketConnector connector : socketConnectorList) {
            if (connector != null) {
                connector.close();
            }
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ServerSocketThread implements Runnable {

        @Override
        public void run() {
            try {
                while (start && !serverSocket.isClosed()) {
                    try {
                        Socket socket = serverSocket.accept();
                        socketConnectorList.add(new SocketConnector(socket, observerble, threadPool));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                close();
            }
        }
    }
}
