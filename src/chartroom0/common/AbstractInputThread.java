package chartroom0.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


public class AbstractInputThread implements Runnable {
    private final Socket socket;


    private  boolean running = true;
    private String name=null;
    LinkedList<Socket> list = new LinkedList<Socket>();

    private Map<String,Socket> map=new HashMap<String, Socket>();
    public AbstractInputThread(Socket socket) {
        this.socket = socket;
    }
    public Map<String, Socket> getMapList(){
        return map;
    }
    public void addMapList(String name,Socket socket) {
       map.put(name,socket);
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
                String str=is.readUTF();
             //   if(!map.isEmpty()){
                    String[] arr = str.split("\\s+");
                    if(arr.length==2){
                        if(arr[1].equals("login")){
                            addMapList(arr[0],socket);
                            System.out.println("输出");
                        }else{
                            Socket socket=map.get(arr[0]);
                            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                            os.writeUTF(str);
                        }
                    }else {
                        for (Map.Entry<String, Socket> entry : map.entrySet()) {
                            Socket socket = entry.getValue();
                            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                            os.writeUTF(str);
                        }
                    }
                //}
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
