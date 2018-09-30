package com.mq.rabbitmq.tx;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Sender {

    public static void send(){
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.newConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare("q.tx",true,false,false,null);

            int i = 0;
            while (i++<100){
                int rand = (int)(Math.random()*100);
                System.out.println("发送消息..." + new Date());
                try {
                    channel.txSelect();//开启事务
                    channel.basicPublish("","q.tx",MessageProperties.PERSISTENT_TEXT_PLAIN,"hello rabbitMq tx".getBytes());
                    if(rand%3==0){//制造异常
                        throw new RuntimeException("error");
                    }
                    TimeUnit.SECONDS.sleep(1);
                    channel.txCommit();
                } catch (Exception e) {
                    channel.txRollback();
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
