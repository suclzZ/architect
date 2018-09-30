package com.mq.rabbitmq.hello;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    public final static String URL = ConnectionFactory.DEFAULT_HOST;
    public final static int PORT = ConnectionFactory.DEFAULT_AMQP_PORT;
    public final static String USERNAME = ConnectionFactory.DEFAULT_USER;
    public final static String PASSWORD = ConnectionFactory.DEFAULT_PASS;

    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(URL);
        connectionFactory.setPort(PORT);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        return connectionFactory.newConnection();
    }


}
