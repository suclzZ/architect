package com.mq.rabbitmq.depth;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class JustConsumer2 {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //args: queue, durable, exclusive, autoDelete,  1` arguments
        channel.queueDeclare(JustConfig.QUEUE_PC,false,false,false,null);
        //args: exchange, type, durable, autoDelete, internal, arguments
        channel.exchangeDeclare(JustConfig.EXCHANGE_PC, BuiltinExchangeType.TOPIC,false,false,false,null);

        //args: queue, exchange, arguments
        channel.queueBind(JustConfig.QUEUE_PC, JustConfig.EXCHANGE_PC, JustConfig.BINDKING_KEY_PC, null);

        /**
         * 如果没有进行ACK，那么该consumer会一直处于忙的状态，不会再处理新的消息
         *
         */
        channel.basicQos(1);//一次处理一条消息

        System.out.println("[c2] 等待接收消息...");
        //queue, autoAck, consumerTag, noLocal, exclusive ,arguments, callback
        channel.basicConsume(JustConfig.QUEUE_PC,false,"ct",false,false,null,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("[c2] 接收消息："+ message + " t:"+new Date());
//                SerializationUtils.deserialize(body);
                channel.basicAck(envelope.getDeliveryTag(),false);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //consume处理阻塞状态，如果将channel关闭，则不再监听队列中的消息
//        channel.close();
//        connection.close();

    }
}
