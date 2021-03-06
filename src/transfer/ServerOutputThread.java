package transfer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ServerOutputThread implements Runnable {
    Socket socket = null;

    public ServerOutputThread(Socket socket) {
        super();
        this.socket=socket;
    }

    @Override
    public void run() {
        OutputStream os = null;
        Scanner scanner = new Scanner(System.in);

        try {
            os = socket.getOutputStream();
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


    }
}
