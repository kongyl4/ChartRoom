package transfer.client;

import java.net.Socket;

import transfer.common.AbstractInputThread;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ClientInputThread extends AbstractInputThread {

    public ClientInputThread(Socket socket) {
        super(socket);
    }

}
