package com.mq.rabbitmq.tx;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * 通过事务控制消息的持久化，解决消息丢失问题
 * 当然即使如此，也不是绝对的保证，因为在真正持久化时也可能出现各种因素
 */
public class Receiver {

    public static void send() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //开启消息持久化
        channel.queueDeclare("q.tx",true,false,false,null);

        channel.basicConsume("q.tx",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("接收消息.."+new Date());
                System.out.println("r : " +new String(body));
            }
        });

//        channel.close();
//        connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Receiver.send();
    }
}
