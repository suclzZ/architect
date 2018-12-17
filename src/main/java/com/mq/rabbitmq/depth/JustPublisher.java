package com.mq.rabbitmq.depth;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *消息确认：（handleAck）
 *  broker 发现当前消息无法被路由到指定的 queues 中（如果设置了 mandatory 属性，则 broker 会先发送 basic.return）
 *  非持久属性的消息到达了其所应该到达的所有 queue 中（和镜像 queue 中）
 *  持久消息到达了其所应该到达的所有 queue 中（和镜像 queue 中），并被持久化到了磁盘（被 fsync）
 *  持久消息从其所在的所有 queue 中被 consume 了（如果必要则会被 acknowledge）
 *
 *
 */
public class JustPublisher {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        String[] messages = {"ack message","nack message","reject message","recover message","simple message"};

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //启动confirm模式
        channel.confirmSelect();

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                //生产者消息发送成功，具体是发送到路由成功，还是其他，待考究
                System.out.println("[p] handleAck");
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                //生产者消息发送失败，具体是发送到路由成功，还是其他，待考究
                System.out.println("[p] handleNack");
            }
        });
        /**
         * mandatory=ture
         * 如果exchange根据自身类型和消息routeKey无法找到一个符合条件的queue，那么会调用basic.return方法将消息返还给生产者；
         * 否则消息被丢弃
         * immediate=ture
         * 如果exchange在将消息route到queue(s)时发现对应的queue上没有消费者，那么这条消息不会放入队列中。
         * 当与消息routeKey关联的所有queue(一个或多个)都没有消费者时，该消息会通过basic.return方法返还给生产者
         */
        channel.addReturnListener(new ReturnCallback() {
            @Override
            public void handle(Return aReturn) {
                System.out.println("[p] return handller");
            }
        });

        //args: exchange, type, durable, autoDelete, internal, arguments
        channel.exchangeDeclare(JustConfig.EXCHANGE_PC, BuiltinExchangeType.TOPIC,false,false,false,null);

        for (int i=0;i<20;i++) {
            int rand = (int) (Math.random() * 100 % 3);//随机去0-4
            String message = messages[rand];
            System.out.println("[p] 发送消息："+message);
            //args: exchange, routingKey, mandatory, immediate,BasicProperties,message
            channel.basicPublish(JustConfig.EXCHANGE_PC, JustConfig.ROUTING_KEY_PC, false, false, null, message.getBytes());
            TimeUnit.SECONDS.sleep(1);
        }

        channel.close();
        connection.close();

    }
}
