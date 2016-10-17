package transfer.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import transfer.common.Config;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class ClientMain {

    private static final ExecutorService inputThreadPool = Executors.newSingleThreadExecutor();
    private static final ExecutorService outputThreadPool = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {

        Socket socket = null;
       /* Scanner scanner=null;
        System.out.println("请输入您的用户名：");
        String name=scanner.nextLine();*/
        try {
            socket = new Socket(Config.HOST, Config.PORT);
            outputThreadPool.execute(new ClientOutputThread(socket));
            inputThreadPool.execute(new ClientInputThread(socket));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputThreadPool.shutdown();
            outputThreadPool.shutdown();
        }



    }
}
