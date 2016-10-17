package chat.handler;

import chat.net.SocketConnector;
import chat.packet.Packet;

/**
 * Created by wyx on 2016/10/17.
 */
public interface Handler<T extends Packet> {
    boolean handle(T packet, SocketConnector connector);
}
