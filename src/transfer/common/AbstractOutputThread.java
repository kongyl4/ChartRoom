package transfer.common;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by wyx on 2016/10/14.
 */
public abstract class AbstractOutputThread implements Runnable {
    private final List<Socket> socketList = new LinkedList<Socket>();

    private volatile boolean running = true;

    public void addSocket(Socket socket) {
        socketList.add(socket);
    }

    public List<Socket> getSocketList() {
        return socketList;
    }

    public void shutdown() {
        running = false;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (running) {
            String str = scanner.nextLine();
            Iterator<Socket> it = socketList.iterator();
            while (it.hasNext()) {
                Socket socket = it.next();
                DataOutputStream os = null;
                try {
                    os = new DataOutputStream(socket.getOutputStream());
                    os.writeUTF(str);
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("关闭输出连接");
                    it.remove();
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e1) {
                            e.printStackTrace();
                        }
                    }
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
        for (Socket socket : socketList) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*finally {
            try {
                os.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/


    }
}
