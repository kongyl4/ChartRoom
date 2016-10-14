package transfer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ServerInputThread implements Runnable{
    Socket socket=null;
    public ServerInputThread(Socket socket) {
        this.socket=socket;
    }

    @Override
    public void run() {
        InputStream is=null;
        try {
            is=socket.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*finally {
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
      /*  InputStreamReader isr=new InputStreamReader(is);
        BufferedReader bf=new BufferedReader(isr);
        try {
            System.out.println(bf.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bf!=null){
                    bf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

      byte[] b=new byte[100];
        try {
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

    }
}
