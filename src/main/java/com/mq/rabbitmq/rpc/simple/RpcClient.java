package com.mq.rabbitmq.rpc.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RpcClient {

    public static void start(){
        try {
            Connection connection = new ConnectionFactory().newConnection();

            Channel channel = connection.createChannel();

//            channel.exchangeDeclare(RpcServer.EXCHANGE_NAME,BuiltinExchangeType.TOPIC);

            call(channel);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static void call(Channel channel) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1);
        try {
            String correlationId = UUID.randomUUID().toString();
            String queueName = channel.queueDeclare().getQueue();

//            AMQP.BasicProperties props = new AMQP.BasicProperties();
//            props.builder().correlationId(correlationId).replyTo(RpcServer.RPC_QUEUE_NAME);

            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                    .correlationId(correlationId).replyTo(queueName).build();
            String message = "hello server";

            System.out.println("c# send message : "+message);
            channel.basicPublish("",RpcServer.RPC_QUEUE_NAME,props,message.getBytes());

            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    if(correlationId.equals(properties.getCorrelationId())){
                        System.out.println("c# receive message :" + new String(body));
                        queue.offer(new String(body));
                    }
                }
            };
            channel.basicConsume(queueName,true,consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(queue.iterator().hasNext()){
            System.out.println(queue.iterator().next());
        }
    }

    public static void main(String[] args) {
        RpcClient.start();
    }
}
