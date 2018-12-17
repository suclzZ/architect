package com.basic.io.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  多线程完成处理资源的管理
 *
 *  测试： 可以通过telnet localhost 9000 进行测试
 *
 * @author sucl
 * @since 2018/12/14
 */
public class QualityUnBlockSocketServer {

    private static int PORT = 9000;
    private ExecutorService executors = Executors.newFixedThreadPool(3);

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("socket start ...");
            while (true){
                Socket socket = serverSocket.accept();//阻塞 其他socket的连接
                System.out.println("socket client connect at : "+socket.getPort());
                executors.submit(() -> {
                    new SocketHandler().handle(socket);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new QualityUnBlockSocketServer().start();
    }
}
