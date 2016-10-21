package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
/**
 * Created by kongyl4 on 2016/10/11.
 */
public class TcpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(5000);//0-65535 前10000
        Socket socket=serverSocket.accept();
        System.out.println("ServerSocketConnector Successfully!");
        InputStream is=socket.getInputStream();
        OutputStream os=socket.getOutputStream();
        Scanner scanner=new Scanner(System.in);
        byte[] b=new byte[100];
        is.read(b);
        System.out.println(new String(b));

        os.write(scanner.nextLine().getBytes());
        is.close();
        os.close();
        socket.close();

    }
}
