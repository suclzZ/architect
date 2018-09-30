package com.mq.rabbitmq.task;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 当启动多个Worker后，在启动Task，可以发现Worker是均匀分配消息进行处理的
 *
 * 同时可以设置Qos来实现消息接收均衡
 *
 * 通过queueDeclare设置是否持久化
 *
 * 通过basicConsume设置是否自动确认
 *
 */
public class Worker {

    public final static String QUEUE_NAME = "task";

    public static void work() throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.basicQos(1);//一次发送消息的条数，保证每个消费者均匀接收消息
        /**
         * 四个参数：队列名、是否持久化、是否唯一、是否自动删除
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    doWork(new String(body));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("Done.");
                }
            }
        };
        System.out.println("ready ..");
        channel.basicConsume(QUEUE_NAME,true,consumer);

//        channel.close();
//        connection.close();
    }

    private static void doWork(String body) throws InterruptedException {
        System.out.println("receive : "+body);
        Thread.sleep(1000);
//        for(char c : body.toCharArray()){
//            System.out.print(c+".");
//            Thread.sleep(1000);
//        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Worker.work();
    }
}
