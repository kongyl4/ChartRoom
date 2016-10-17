package chartroom;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kongyl4 on 2016/10/15.
 */
public class Connector {
    List<Listener> listenerList=new LinkedList<Listener>();
    public void connect(Socket socket){


    }
    public void disConnect(Socket socket){
        if(socket!=null)
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void addListener(Listener listener){
        listenerList.add(listener);
    }
}
