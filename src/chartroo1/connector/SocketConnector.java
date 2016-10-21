package chartroo1.connector;

import chartroo1.message.Message;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Created by kongyl4 on 2016/10/19.
 */
public class SocketConnector {
    private String host=null;
    private int port=0;
    private String username=null;
    private Socket socket=null;
    SocketChannel socketChannel=null;
    ExecutorService threadPool=null;
   // List<>
    public SocketConnector(String host,int port,String username) {
        this.host=host;
        this.port=port;
        this.username=username;
    }
    //
    public void start(){
        try {
            socket=new Socket(host,port);

            socketChannel=socket.getChannel();
            /*ByteBuffer byteBuffer=ByteBuffer.allocate(100);//这个size怎么设置
            socketChannel.read(byteBuffer);*/
            threadPool.execute(new socketThread());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void  close(){

    }
    public void send(Message message){
    }
    private class socketThread implements Runnable{
        @Override
        public void run() {


        }
    }
}
