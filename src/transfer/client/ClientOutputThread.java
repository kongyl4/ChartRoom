package transfer.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import transfer.common.AbstractOutputThread;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ClientOutputThread implements Runnable {
    Socket socket=null;
    public ClientOutputThread(Socket socket) {
        super();
        this.socket=socket;
    }

    @Override
    public void run() {
        OutputStream os=null;
        Scanner scanner=new Scanner(System.in);

     //   while(true){
            try {
                os=socket.getOutputStream();
                os.write(scanner.nextLine().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*finally {
                try {
                    os.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
      //  }

    }
}
