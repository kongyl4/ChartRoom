package chartroom0.client;

import chartroom0.common.Config;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ClientMain {

    private static final ExecutorService inputThreadPool = Executors.newSingleThreadExecutor();
    private static final ExecutorService outputThreadPool = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {

        Socket socket = null;
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入您的用户名：");
        String name=scanner.nextLine();
        try {
            socket = new Socket(Config.HOST, Config.PORT);
            DataOutputStream os= new DataOutputStream(socket.getOutputStream());
            os.writeUTF(name+" "+"login");
            ClientOutputThread command = new ClientOutputThread();
            command.putSocket(name,socket);
            outputThreadPool.execute(command);
            inputThreadPool.execute(new ClientInputThread(socket));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputThreadPool.shutdown();
            outputThreadPool.shutdown();
        }



    }
}
