package chartroom0.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


public abstract class AbstractInputThread implements Runnable {
    private final Socket socket;


    private  boolean running = true;
    private String name=null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    LinkedList<Socket> list = new LinkedList<Socket>();

    protected Map<String,Socket> map=new HashMap<String, Socket>();
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

    protected abstract void handleInput(String str) throws IOException;

    @Override
    public void run() {
        DataInputStream is = null;
        try {
            is = new DataInputStream(socket.getInputStream());

            while (running) {
                String str=is.readUTF();
             //   if(!map.isEmpty()){
                    handleInput(str);
                //}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭输入连接");

            for (Iterator<Map.Entry<String,Socket>> it= map.entrySet().iterator();it.hasNext();) {
                try {
                    Map.Entry<String,Socket> entry=it.next();
                    Socket socket = entry.getValue();
                    DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                    os.writeUTF(name +" logout");
                }catch(Exception e){
                    e.printStackTrace();
                    it.remove();
                }
            }

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
