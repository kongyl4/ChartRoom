package chat.net.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import chat.handler.Handler;
import chat.handler.HandlerObserverble;
import chat.handler.LogPacketHandler;
import chat.net.ServerSocketConnector;
import chat.net.SocketConnector;
import chat.packet.BroadcastPacket;
import chat.packet.LoginPacket;
import chat.packet.LogoutPacket;
import chat.packet.Packet;
import chat.packet.SendPacket;
import chat.packet.UserListPacket;

/**
 * Created by wyx on 2016/10/17.
 */
public class Server extends HandlerObserverble {

    private static final String SERVER_USERNAME = "***server***";

    private final int port;

    private ServerSocketConnector serverSocketConnector;

    private Map<String, SocketConnector> connectorMap = new ConcurrentHashMap<String, SocketConnector>();

    @Override
    public void onClose(SocketConnector connector) {
        for (Map.Entry<String, SocketConnector> entry : connectorMap.entrySet()) {
            if (entry.getValue() == connector) {
                LogoutPacket logoutPacket = new LogoutPacket();
                logoutPacket.setUsername(entry.getKey());
                broadcast(logoutPacket);
                break;
            }
        }
    }

    private class LoginHandler implements Handler<LoginPacket> {
        @Override
        public boolean handle(LoginPacket packet, SocketConnector connector) {
            UserListPacket userListPacket = new UserListPacket();
            userListPacket.getUserList().addAll(connectorMap.keySet());
            String username = packet.getUsername();
            connectorMap.put(username, connector);
            connector.send(userListPacket);
            return true;
        }
    }

    private class LoginLogoutBroadcastHandler implements Handler<Packet> {

        @Override
        public boolean handle(Packet packet, SocketConnector connector) {
            if (packet instanceof LoginPacket || packet instanceof LogoutPacket) {
                broadcast(packet);
            }
            return true;
        }
    }

    private class LogoutHandler implements Handler<LogoutPacket> {
        @Override
        public boolean handle(LogoutPacket packet, SocketConnector connector) {
            String username = packet.getUsername();
            connectorMap.remove(username);
            return true;
        }
    }

    private class SendHandler implements Handler<SendPacket> {

        @Override
        public boolean handle(SendPacket packet, SocketConnector connector) {
            send(packet.getTo(), packet);
            send(packet.getUsername(), packet);
            return true;
        }
    }

    private class BroadcastHandler implements Handler<BroadcastPacket> {

        @Override
        public boolean handle(BroadcastPacket packet, SocketConnector connector) {
            broadcast(packet);
            return true;
        }
    }

    public Server(int port) {
        this.port = port;
        this.registerHandler(new LoginLogoutBroadcastHandler());
        this.registerHandler(new LoginHandler());
        this.registerHandler(new LogoutHandler());
        this.registerHandler(new SendHandler());
        this.registerHandler(new BroadcastHandler());
    }

    public void start() {
        serverSocketConnector = new ServerSocketConnector(port, this);
        boolean ret = serverSocketConnector.start();
        if (ret) {
            System.out.println("Server start successful.");
        }
    }

    public void shutdown() {
        serverSocketConnector.shutdown();
    }

    public void send(String to, Packet packet) {
        SocketConnector connector = connectorMap.get(to);
        if (connector != null) {
            connector.send(packet);
        }
    }

    public void send(String from, String to, String msg) {
        SendPacket packet = new SendPacket();
        packet.setUsername(from);
        packet.setTo(to);
        packet.setMsg(msg);
        this.send(to, packet);
    }

    public void broadcast(Packet packet) {
        for (SocketConnector connector : connectorMap.values()) {
            connector.send(packet);
        }
    }

    public void broadcast(String msg) {
        BroadcastPacket packet = new BroadcastPacket();
        packet.setUsername(SERVER_USERNAME);
        packet.setMsg(msg);
        this.broadcast(packet);
    }

    public static void main(String[] args) {
        Server server = new Server(8888);
        server.registerHandler(new LogPacketHandler());
        server.start();
    }
}
