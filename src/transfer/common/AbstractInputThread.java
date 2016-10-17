package transfer.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;


public class AbstractInputThread implements Runnable {
    private final Socket socket;


    private  boolean running = true;
    LinkedList<Socket> list = new LinkedList<Socket>();

   // Map<String,Socket> map=new HashMap<String, Socket>();
    public AbstractInputThread(Socket socket) {
        this.socket = socket;
    }

    /*public void addSocket(Socket socket) {
        list.add(socket);
    }*/
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
//密语
                if (!list.isEmpty()) {
                    String[] arr = str.split("\\s+");
                    int index = 0;
                   if (arr.length == 2) {
                        index = Integer.valueOf(arr[0]);
                    }
                    /*if (arr[0].equals(0)) {
                        for(Map.Entry<String,Socket> entry:map.entrySet()){
                            Socket socket=entry.getValue();
                            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                            os.writeUTF(str);
                        }*/
                    if (index==0) {
                        Iterator<Socket> it = list.iterator();
                        while (it.hasNext()) {
                            Socket socket = it.next();
                            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                            os.writeUTF(str);
                        }
                    } else {
                        Socket socket = list.get(index - 1);
                        //Socket socket=map.get(arr[0]);
                        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                        os.writeUTF(str);
                    }
                }
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
