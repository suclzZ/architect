package com.basic.io.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *  这个示例一次只能接受一个客户端连接，虽然使用了while(true) 但是serverSocket.accept()时阻塞的，当有一个连接进来后会阻止其他的socket进入
 *
 *  测试： 可以通过telnet localhost 9000 进行测试
 *
 * @author sucl
 * @since 2018/12/14
 */
public class SocketServer {

    private static String HOST = "localhost";
    private static int PORT = 9000;

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("socket start ...");
            while (true){
                Socket socket = serverSocket.accept();//阻塞 其他socket的连接
                System.out.println("socket client connect at : "+socket.getPort());
                new SocketHandler().handle(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SocketServer().start();
    }
}
