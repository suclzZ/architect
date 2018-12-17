package com.mq.rabbitmq.depth;

import com.rabbitmq.client.*;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * ack,nack消息都会从queue中消费掉
 * 但是nack是一种机制，比如出现异常，或对某些特定的消息有其他的方式处理，比如重新放入队列中由其他consumer去处理
 * 所以对于publisher，没有可见性
 *
 * 在consumer中，只有对没有处理的消息有可见性，比如状态会变成unacked
 * 但是下次启动后，会恢复到ready，一样由消费者处理
 *
 */
public class JustConsumer {

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

        System.out.println("[c] 等待接收消息...");
        //queue, autoAck, consumerTag, noLocal, exclusive ,arguments, callback
        channel.basicConsume(JustConfig.QUEUE_PC,false,"ct",false,false,null,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("[c] 接收消息："+ message + " t:"+new Date());
//                SerializationUtils.deserialize(body);
//                channel.basicAck(envelope.getDeliveryTag(),false);
//                channel.basicReject(envelope.getDeliveryTag(), false);
                boolean dispect = true;
                if(dispect) {
                    if (message.indexOf("ack") == 0) {
                        System.out.println("[c] ack");
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } else if (message.indexOf("uack") == 0) {
                        System.out.println("[c] nack");
                        channel.basicNack(envelope.getDeliveryTag(), false, false);
                    } else if (message.indexOf("reject") == 0) {
                        System.out.println("[c] reject");
                        channel.basicReject(envelope.getDeliveryTag(), false);
                    } else if (message.indexOf("recover") == 0) {
                        //为该消费者重发消息，true：全部消费者
//                    channel.basicRecover(false);
                    } else {

                    }
                }
//                System.out.println("[c] 是否重发 : "+envelope.isRedeliver());
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
