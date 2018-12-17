package com.basic.io.net;

import com.google.common.base.Charsets;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang.CharSet;
import org.apache.commons.lang3.CharSetUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.CharsetDecoder;
import java.util.Scanner;

/**
 * @author sucl
 * @since 2018/12/17
 */
public class SocketClient {
    private static String HOST = "localhost";
    private static int PORT = 9000;

    public void connect(){
        try {
            Socket socket = new Socket(HOST,PORT);
            System.out.println("连接服务器成功...");
            send3(socket);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(Socket socket) throws IOException {
        Scanner scanner = new Scanner(System.in, Charsets.UTF_8.name());
        OutputStream os = socket.getOutputStream();
        String inStr = null;
        while ((inStr = scanner.next())!=null){
            os.write(inStr.getBytes());
            os.write('\n');// 注意服务端
            os.flush();
        }
        scanner.close();
        os.close();
    }

    private void send2(Socket socket) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Scanner scanner = new Scanner(System.in);
        String msg = null;
        while ((msg = scanner.nextLine())!=null){
            if("quit".equals(msg)){
                socket.close();
                break;
            }
            writer.write(msg);
            writer.flush();
        }
    }

    private void send3(Socket socket) throws IOException {
        Scanner scanner = new Scanner(System.in);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        String inStr = null;
        while ((inStr = scanner.next())!=null){
            out.writeUTF(inStr);
            out.write('\n');// 注意服务端
            out.flush();
        }
        out.close();
        scanner.close();
    }

    public static void main(String[] args) {
        new SocketClient().connect();
    }
}
