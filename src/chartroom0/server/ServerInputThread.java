package chartroom0.server;


import chartroom0.common.AbstractInputThread;

import java.net.Socket;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ServerInputThread extends AbstractInputThread {

    public ServerInputThread(Socket socket) {
        super(socket);
    }

}