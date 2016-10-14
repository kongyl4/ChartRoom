package transfer;

import java.net.Socket;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ClientOutputThread extends AbstractOutputThread {

    public ClientOutputThread(Socket socket) {
        super(socket);
    }
}
