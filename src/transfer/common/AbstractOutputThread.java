package transfer.common;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by wyx on 2016/10/14.
 */
public abstract class AbstractOutputThread implements Runnable {
    private final Socket socket;

    public AbstractOutputThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        DataOutputStream os = null;
        Scanner scanner = new Scanner(System.in);

        try {
            os = new DataOutputStream(socket.getOutputStream());
            os.writeUTF(scanner.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
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
