package chat.net.server;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import chat.handler.Handler;
import chat.handler.HandlerObserverble;
import chat.handler.LogPacketHandler;
import chat.net.ServerSocketConnector;
import chat.net.SocketConnector;
import chat.packet.BroadcastPacket;
import chat.packet.LoginPacket;
import chat.packet.LogoutPacket;
import chat.packet.SendPacket;

/**
 * Created by wyx on 2016/10/17.
 */
public class Server extends HandlerObserverble {

    private static final String SERVER_USERNAME = "***server***";

    private final int port;

    private ServerSocketConnector serverSocketConnector;

    private Map<String, SocketConnector> connectorMap = new ConcurrentHashMap<String, SocketConnector>();

    private class LoginHandler implements Handler<LoginPacket> {
        @Override
        public boolean handle(LoginPacket packet, SocketConnector connector) {
            String username = packet.getUsername();
            connectorMap.put(username, connector);
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
            send(packet.getTo(), packet.getMsg());
            return true;
        }
    }

    private class BroadcastHandler implements Handler<BroadcastPacket> {

        @Override
        public boolean handle(BroadcastPacket packet, SocketConnector connector) {
            broadcast(packet.getMsg());
            return true;
        }
    }

    public Server(int port) {
        this.port = port;
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

    public void send(String to, String msg) {
        SocketConnector connector = connectorMap.get(to);
        if (connector != null) {
            SendPacket packet = new SendPacket();
            packet.setUsername(SERVER_USERNAME);
            packet.setTo(to);
            packet.setMsg(msg);
            connector.send(packet);
        }
    }

    public void broadcast(String msg) {
        BroadcastPacket packet = new BroadcastPacket();
        packet.setUsername(SERVER_USERNAME);
        packet.setMsg(msg);
        for (SocketConnector connector : connectorMap.values()) {
            connector.send(packet);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(53862);
        server.registerHandler(new LogPacketHandler());
        server.start();

        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] str = in.nextLine().split("\\s+");
            if (str.length == 1) {
                server.broadcast(str[0]);
            } else {
                server.send(str[0], str[1]);
            }
        }
    }
}
