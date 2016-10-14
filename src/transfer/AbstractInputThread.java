package transfer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by wyx on 2016/10/14.
 */
public class AbstractInputThread implements Runnable {
    private final Socket socket;

    public AbstractInputThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        DataInputStream is = null;
        try {
            is = new DataInputStream(socket.getInputStream());
            String str=is.readUTF();
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
            if (is != null) {
                try {
                    is.close();
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
