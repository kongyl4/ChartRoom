package transfer.server;


import java.net.Socket;

import transfer.common.AbstractInputThread;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ServerInputThread extends AbstractInputThread {

    public ServerInputThread(Socket socket) {
        super(socket);
    }

}
