package transfer;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ClientMain {
    public static void main(String[] args) {
        //while(true){
        Socket socket=null;
         try {
            socket=new Socket("localhost",10002);
             System.out.println(socket.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
            ClientOutputThread clientOutputThread=new ClientOutputThread(socket);
            Thread t1=new Thread(clientOutputThread);
            t1.start();
        Thread t2=new Thread(new ClientInputThread(socket));
        t2.start();
        //}

    }
}
