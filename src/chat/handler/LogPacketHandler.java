package chat.handler;

import chat.net.SocketConnector;
import chat.packet.Packet;

/**
 * Created by wyx on 2016/10/17.
 */
public class LogPacketHandler implements Handler<Packet> {
    @Override
    public boolean handle(Packet packet, SocketConnector connector) {
        System.out.println(packet);
        return true;
    }
}
