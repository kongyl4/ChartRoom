package transfer.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import transfer.common.AbstractInputThread;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ClientInputThread implements Runnable{
    Socket socket=null;
    public ClientInputThread(Socket socket) {
        super();
        this.socket=socket;
    }
    @Override
    public void run() {
        InputStream is=null;
        byte[] b=new byte[100];
        //while(true){
            try {
                System.out.println("port="+socket.getPort());
                is=socket.getInputStream();
                is.read(b);
                System.out.println(new String(b));
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*finally {
                try {
                    is.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }*/
        //}

    }
}
