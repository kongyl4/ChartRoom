package transfer;

import java.net.Socket;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ServerOutputThread extends AbstractOutputThread {

    public ServerOutputThread(Socket socket) {
        super(socket);
    }

}
