package chartroo1.listener;

import chartroo1.connector.SocketConnector;
import chartroo1.message.Message;

/**
 * Created by kongyl4 on 2016/10/20.
 */
public interface Handler {
    public void handle(Message message, SocketConnector socketConnector);
}
