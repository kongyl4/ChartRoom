package chartroo1.listener;

import chartroo1.connector.SocketConnector;
import chartroo1.message.Message;

/**
 * Created by kongyl4 on 2016/10/20.
 */
public class LoginHandler implements Handler{

    @Override
    public void handle(Message message, SocketConnector socketConnector) {

        System.out.println(message.getUsername()+"上线");
    }
}
