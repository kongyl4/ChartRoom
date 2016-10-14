package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by kongyl4 on 2016/10/11.
 */
public class TcpClient {

    public static void  main(String[] args) throws IOException {
        Socket socket=new Socket("localhost",5000);
        OutputStream os=socket.getOutputStream();
        InputStream is=socket.getInputStream();
        String str="hello a!";
        Scanner scanner=new Scanner(System.in);
        os.write(scanner.nextLine().getBytes());

        byte[] b=new byte[100];
        is.read(b);
        System.out.println(new String(b));

        os.close();
        is.close();
        socket.close();

    }

}
