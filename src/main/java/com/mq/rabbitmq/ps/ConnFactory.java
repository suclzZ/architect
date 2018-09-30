package com.mq.rabbitmq.ps;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 获取与rabbitmq的连接
 */
public class ConnFactory {

    public static Connection getConnection(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = null;//地址 端口 用户 密码 取默认值，参考ConnectionFactory静态变量
        try {
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeAll(Connection connection){
        if(connection!=null){

        }
    }
}
