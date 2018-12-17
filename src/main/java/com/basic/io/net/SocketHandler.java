package com.basic.io.net;

import com.google.common.base.Charsets;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 *  将阻塞的模块放在线程中处理，这样就可以实现多个socket客户端连接了
 * @author sucl
 * @since 2018/12/14
 */
public class SocketHandler {

    public void handle(Socket socket){
        try {
            if(!socket.isClosed()){
                receive3(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void receive(Socket socket) throws IOException {
        Scanner scanner = new Scanner(socket.getInputStream(),Charsets.UTF_8.name());
        String line = null;
        while( (line = scanner.nextLine())!="quit"){//阻塞
            System.out.println("receive : " + line);
        }
        System.out.println("通信结束..");
        scanner.close();
    }

    private void receive2(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = null;
        while( (line = reader.readLine())!=null){//阻塞
            if("quit".equals(line)){
                socket.close();
                break;
            }
            System.out.println("receive : " + line);
        }
        System.out.println("通信结束..");
        reader.close();
    }

    // 控制消息接收节点
    private void receive3(Socket socket) throws IOException {
        DataInputStream is = new DataInputStream(socket.getInputStream());//客户端必须使用相同流类型，不然可能出现乱码问题
        String line = null;
        while( (line = is.readUTF())!=null){//阻塞 然而值读取了一次？
            if("quit".equals(line)){
                socket.close();
                break;
            }
            System.out.println("receive : " + line);
        }
        System.out.println("通信结束..");
        is.close();
    }
}
