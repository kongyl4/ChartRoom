package chat.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import chat.handler.HandlerObserverble;
import chat.packet.Packet;

/**
 * Created by wyx on 2016/10/17.
 */
public class SocketConnector {

    private String host;

    private int port;

    private Socket socket;

    private ObjectInputStream ois;

    private ObjectOutputStream oos;

    private HandlerObserverble observerble;

    private ExecutorService threadPool;

    private volatile boolean start;

    public SocketConnector(String host, int port, HandlerObserverble observerble) {
        this.host = host;
        this.port = port;
        this.observerble = observerble;
        threadPool = Executors.newSingleThreadExecutor();
    }

    public SocketConnector(Socket socket, HandlerObserverble observerble, ExecutorService threadPool) {
        this.socket = socket;
        this.observerble = observerble;
        this.threadPool = threadPool;
        start();
    }

    public boolean start() {
        try {
            if (socket == null) {
                this.socket = new Socket(host, port);
            }
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
            start = true;
            threadPool.execute(new SocketThread());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            shutdown();
            return false;
        }
    }

    public boolean isStarting() {
        return start;
    }

    public void shutdown() {
        start = false;
        close();
    }

    public void send(Packet packet) {
        if (start && socket.isConnected()) {
            try {
                oos.writeObject(packet);
            } catch (IOException e) {
                e.printStackTrace();
                shutdown();
            }
        }
    }

    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class SocketThread implements Runnable {

        @Override
        public void run() {
            try {
                while (start && socket != null && socket.isConnected()) {
                    Packet packet = (Packet) ois.readObject();
                    observerble.notifyHandlers(packet, SocketConnector.this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }

        }
    }

}
