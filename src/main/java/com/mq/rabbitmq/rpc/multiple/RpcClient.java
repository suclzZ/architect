package com.mq.rabbitmq.rpc.multiple;

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
            //定义相应路由路由
            channel.exchangeDeclare(RpcServer.EXCHANGE_RESPONCE,"topic");
            //定义请求路由路由
            channel.exchangeDeclare(RpcServer.EXCHANGE_REQUEST,"topic");

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
            //新建关联id
            String correlationId = UUID.randomUUID().toString();
            //定义响应队列
            String queueName = channel.queueDeclare().getQueue();
            //将响应队列与响应路由绑定
            channel.queueBind(queueName,RpcServer.EXCHANGE_RESPONCE, RpcServer.ROUTINGKEY_RESPONCE);

            String message = "hello server";
            System.out.println("c# send message : "+message);

            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(correlationId).replyTo(RpcServer.RPC_RESPONCE_QUEUE).build();
            //客户端向服务器发送消息，即向请求路由上发布
            channel.basicPublish(RpcServer.EXCHANGE_REQUEST,RpcServer.RPC_REQUEST_QUEUE,props,message.getBytes());

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
            System.out.println(">>>>>>>> "+queue.iterator().next());
        }
    }

    public static void main(String[] args) {
        RpcClient.start();
    }
}
