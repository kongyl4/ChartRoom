package chartroo1.main;

import chartroo1.connector.SocketConnector;
import chartroo1.message.BroadcastMessage;
import chartroo1.message.LoginMessage;
import chartroo1.message.Message;
import chartroo1.message.SendMessage;

import java.net.Socket;
import java.util.Scanner;

/**
 * Created by kongyl4 on 2016/10/20.
 */
public class Client {
    private static String host=null;
    private static int port=0;
    private static String username=null;
    SocketConnector socketConnector=null;
    public Client(String host,int port,String username) {
        this.host=host;
        this.port=port;
        this.username=username;
    }

    public void login(){
        socketConnector=new SocketConnector(host,port,username);
        socketConnector.start();
        Message message=new LoginMessage();//??为什么可以用Message声明
        message.setUsername(username);
        socketConnector.send(message);

    }
    public void send(String to,String msg){
        SendMessage sendMessage=new SendMessage();//??这个地方为啥不能用Message声明
        sendMessage.setTo(to);
        sendMessage.setUsername(username);
        sendMessage.setMsg(msg);
        socketConnector.send(sendMessage);
    }
    public void broadcast(String msg){
        BroadcastMessage broadcastMessage=new BroadcastMessage();
        broadcastMessage.setMsg(msg);
        //socketConnector

    }
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String username=scanner.nextLine();
        Client client=new Client("localhost",50001,username);
        client.login();

    }
}
