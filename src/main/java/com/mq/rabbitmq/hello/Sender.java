package com.mq.rabbitmq.hello;

import com.mq.rabbitmq.ps.ConnFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    public final static String QUEUE_NAME = "hello";

    public static void send(){
        Channel channel = null;
        Connection connection = null;
        try {
            connection = ConnectionUtil.getConnection();
            channel = connection.createChannel();
            //队列名、 持久化、唯一、自动删除、？
            AMQP.Queue.DeclareOk queue = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String messageText = "Hello , raabitMq!";

            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties();
            basicProperties.builder().appId("0-0");

            System.out.println("send : " + messageText);
            //exchange 、 routingKey 、 properties 、 message
            channel.basicPublish("",QUEUE_NAME,basicProperties,messageText.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
                if(channel!=null) channel.close();
                if(connection!=null) connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Sender.send();
    }
}
