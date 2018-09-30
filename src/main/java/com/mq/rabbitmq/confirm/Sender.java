package com.mq.rabbitmq.confirm;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
/**
 * 通过confirm模式保证消息
 * 没有ack的消息，会留在队列中，之后会继续消费，具体是什么时机，看对应机制
 */
public class Sender {

    public static void send(){
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare("q.confirm",true,false,false,null);
            channel.exchangeDeclare("exchange.confirm","topic");

            channel.confirmSelect();//confirm模式

            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("confirm");
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("reject");
                }
            });

            int i = 0;
            while (i++<10){
                try {
                    String message = "hello rabbitMq confirm  id:" + UUID.randomUUID().toString();
                    System.out.println("发送消息..." + message);
                    channel.basicPublish("exchange.confirm","q.confirm",false,MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
//                    if(!channel.waitForConfirms(1000*3)){//指定时间内没有应答
//                        System.out.println("失败..." + new Date());
//                    }
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Sender.send();
    }
}
