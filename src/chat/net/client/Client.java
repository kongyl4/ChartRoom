package chat.net.client;

import chat.handler.HandlerObserverble;
import chat.net.SocketConnector;
import chat.packet.BroadcastPacket;
import chat.packet.LoginPacket;
import chat.packet.LogoutPacket;
import chat.packet.Packet;
import chat.packet.SendPacket;

/**
 * Created by wyx on 2016/10/17.
 */
public class Client extends HandlerObserverble {

    private final String host;

    private final int port;

    private final String username;

    private SocketConnector connector;

    public Client(String host, int port, String username) {
        this.host = host;
        this.port = port;
        this.username = username;
    }

    public void login() {
        connector = new SocketConnector(host, port, this);
        boolean succ = connector.start();
        if (succ) {
            System.out.println("Client start successful.");
            Packet packet = new LoginPacket();
            packet.setUsername(username);
            connector.send(packet);
        }
    }

    public void logout() {
        Packet packet = new LogoutPacket();
        packet.setUsername(username);
        connector.send(packet);
        connector.shutdown();
    }

    public void send(String to, String msg) {
        SendPacket packet = new SendPacket();
        packet.setUsername(username);
        packet.setTo(to);
        packet.setMsg(msg);
        connector.send(packet);
    }

    public void broadcast(String msg) {
        BroadcastPacket packet = new BroadcastPacket();
        packet.setUsername(username);
        packet.setMsg(msg);
        connector.send(packet);
    }

    @Override
    public void onClose(SocketConnector connector) {
        LogoutPacket logoutPacket = new LogoutPacket();
        logoutPacket.setUsername(username);
        notifyHandlers(logoutPacket, connector);
    }
}
