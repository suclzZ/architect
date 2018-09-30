package com.mq.rabbitmq.task;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Task {

    public final static String QUEUE_NAME = "task";

    public static void start() throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String text = "Hello world";

        int count = 1000,i=0;

        while ( i++<count ){
            String message = text + " "+i;
            System.out.println("send : " +message);
            channel.basicPublish("",QUEUE_NAME,null, message.getBytes());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Task.start();
    }
}
