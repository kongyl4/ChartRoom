package transfer.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyx on 2016/10/14.
 */
public class AbstractInputThread implements Runnable {
    private final Socket socket;


    private volatile boolean running = true;
    LinkedList<Socket> list = new LinkedList<Socket>();

    public AbstractInputThread(Socket socket) {
        this.socket = socket;
    }

    public void addSocket(Socket socket) {
        list.add(socket);
    }

    public void shutdown() {
        running = false;
    }

    @Override
    public void run() {
        DataInputStream is = null;
        try {
            is = new DataInputStream(socket.getInputStream());
            while (running) {
                String str = is.readUTF();
                //1 asdasd
                //dsadsa
                if (!list.isEmpty()) {
                    String[] arr = str.split("\\s+");
                    int index = 0;
                    if (arr.length == 2) {
                        index = Integer.valueOf(arr[0]);
                    }
                    if (index == 0) {
                        Iterator<Socket> it = list.iterator();
                        while (it.hasNext()) {
                            Socket socket = it.next();
                            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                            os.writeUTF(str);
                        }
                    } else {
                        Socket socket = list.get(index - 1);
                        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                        os.writeUTF(str);
                    }
                }
                System.out.println(str);
            }
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            System.out.println("关闭输入连接");
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
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
