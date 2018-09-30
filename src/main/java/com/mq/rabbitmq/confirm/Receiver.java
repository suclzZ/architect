package com.mq.rabbitmq.confirm;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 通过confirm模式保证消息
 */
public class Receiver {

    public static void send() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        channel.confirmSelect();

        //开启消息持久化
        channel.queueDeclare("q.confirm",true,false,false,null);
        channel.exchangeDeclare("exchange.confirm","topic");
        channel.queueBind("q.confirm","exchange.confirm","q.confirm");

        System.out.println("等待接收消息... ");
        channel.basicConsume("q.confirm",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("接收消息.."+new String(body));
                long status = envelope.getDeliveryTag()%3;//模拟多状态
                System.out.println("ack状态："+status);
                if(status == 0){
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }else if(status == 1){
                    channel.basicNack(envelope.getDeliveryTag(),false,true);
                }else if(status ==2){
                   // unacked
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

//        channel.close();
//        connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Receiver.send();
    }
}
