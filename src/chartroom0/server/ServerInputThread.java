package chartroom0.server;


import chartroom0.common.AbstractInputThread;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ServerInputThread extends AbstractInputThread {

    public ServerInputThread(Socket socket) {
        super(socket);
    }

    @Override
    protected void handleInput(String str) throws IOException {
        System.out.println(str);
        String[] arr = str.split("\\s+");
        if(arr.length==2){
            Socket socket=map.get(arr[0]);
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            os.writeUTF(str);
        }else {
            for (Iterator<Map.Entry<String,Socket>> it= map.entrySet().iterator();it.hasNext();) {
                try {
                    Map.Entry<String,Socket> entry=it.next();
                    Socket socket = entry.getValue();
                    DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                    os.writeUTF(str);
                }catch(Exception e){
                    e.printStackTrace();
                    it.remove();
                }
            }
        }
    }

}
