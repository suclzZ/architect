package com.mq.rabbitmq.rpc.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 服务的是消费者...
 */
public class RpcServer {

    public final static String RPC_QUEUE_NAME = "queue.rpc";
//    public final static String EXCHANGE_NAME = "x.rpc";
//    public final static String ROUTINGKEY = "key.rpc";

    public static void start(){
        try {
            Connection connection = new ConnectionFactory().newConnection();

            Channel channel = connection.createChannel();

            AMQP.Queue.DeclareOk queue = channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

//            channel.exchangeDeclare(EXCHANGE_NAME,BuiltinExchangeType.TOPIC);
//
//            channel.queueBind(RPC_QUEUE_NAME, EXCHANGE_NAME, ROUTINGKEY);

            channel.basicQos(1);

            System.out.println("s# waiting for request");

            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    AMQP.BasicProperties replayProperties = new AMQP.BasicProperties.Builder().correlationId(properties.getCorrelationId()).build();

                    String message = new String(body,"UTF-8");
                    System.out.println("s# receive message : " +message);

                    String response = process(message);
                    System.out.println("s# response message : " +response);

                    channel.basicPublish("",properties.getReplyTo(),replayProperties,response.getBytes("UTF-8"));

                    channel.basicAck(envelope.getDeliveryTag(),false);

                }
            };

            channel.basicConsume(RPC_QUEUE_NAME,false,consumer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static String process(String message){
        System.out.println("-------processing---------");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------processed---------");
        return "hi client";
    }

    public static void main(String[] args) {
        RpcServer.start();
    }
}
