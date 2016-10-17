package transfer.common;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public abstract class AbstractOutputThread implements Runnable {
    private final List<Socket> socketList = new LinkedList<Socket>();
   // private final Map<String, Socket> mapList = new HashMap<String, Socket>();
    private boolean running = true;

    public void addSocket( Socket socket) {
        //mapList.put(str, socket);
        socketList.add(socket);
    }

     public List<Socket> getSocketList() {
         return socketList;
     }
  /*  public Map<String, Socket> getSocketList() {
        return mapList;
    }*/

    public void shutdown() {
        running = false;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
 /*       while (running) {
            String str = scanner.nextLine();
            for (Map.Entry<String, Socket> entry : mapList.entrySet()) {
                Socket socket = entry.getValue();
                String name =entry.getKey();
                DataOutputStream os = null;
                try {
                    os = new DataOutputStream(socket.getOutputStream());
                    os.writeUTF(str);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("关闭输出连接" + socket.getLocalPort());
                    System.out.println("size=" + mapList.size());
                    mapList.remove(name);
                    System.out.println("size=" + mapList.size());
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
        }*/
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
                    e.printStackTrace();
                    System.out.println("关闭输出连接" + socket.getLocalPort());
                    System.out.println("size=" + socketList.size());
                    it.remove();
                    System.out.println("size=" + socketList.size());
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
