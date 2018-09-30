package com.mq.rabbitmq.rpc.multiple;

import com.rabbitmq.client.*;

import javax.jms.DeliveryMode;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 服务的是消费者...
 */
public class RpcServer {

    public final static String RPC_REQUEST_QUEUE = "q.request.rpc";
    public final static String RPC_RESPONCE_QUEUE = "q.responce.rpc";
    public final static String EXCHANGE_REQUEST = "x.request.rpc";
    public final static String EXCHANGE_RESPONCE = "x.responce.rpc";
    public final static String ROUTINGKEY_REQUEST = "#.request.rpc";
    public final static String ROUTINGKEY_RESPONCE = "#.responce.rpc";

    public static void start(){
        try {
            //建立连接
            Connection connection = new ConnectionFactory().newConnection();
            //创建channel
            Channel channel = connection.createChannel();
            //定义请求队列 是否持久化、独有、自动删除
            AMQP.Queue.DeclareOk queue = channel.queueDeclare(RPC_REQUEST_QUEUE, false, false, false, null);
            //定义请求路由路由 是否持久化
            channel.exchangeDeclare(EXCHANGE_REQUEST,"topic", false);
            //定义相应路由路由
            channel.exchangeDeclare(EXCHANGE_RESPONCE,"topic");

            read(channel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void read(Channel channel){
        try {
            //将请求队列与请求路由绑定（ROUTINGKEY 定义规则）
            channel.queueBind(RPC_REQUEST_QUEUE, EXCHANGE_REQUEST, ROUTINGKEY_REQUEST);//ROUTINGKEY_REQUEST、RPC_REQUEST_QUEUE
            //流量控制
            channel.basicQos(1);

            System.out.println("s# waiting for request");

            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body,"UTF-8");
                    System.out.println("s# receive message : " +message);
                    String response = process(message);
                    System.out.println("s# response message : " +response);

                    AMQP.BasicProperties replayProperties = new AMQP.BasicProperties.Builder().correlationId(properties.getCorrelationId()).build();
                    //replayProperties.builder().deliveryMode(DeliveryMode.PERSISTENT);//是否持久化
                    //作为生产者 将消息绑定到响应路由上
                    channel.basicPublish(EXCHANGE_RESPONCE,properties.getReplyTo(),replayProperties,response.getBytes("UTF-8"));
                    //服务端消费提交
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };
            //服务端作为消费者，从请求队列消费消息
            channel.basicConsume(RPC_REQUEST_QUEUE,false,consumer);
        } catch (IOException e) {
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
        return "hi client";
    }

    public static void main(String[] args) {
        RpcServer.start();
    }
}
