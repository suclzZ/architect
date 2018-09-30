package com.mq.rabbitmq.hello;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者需要优先启动
 * 先启动生产者，再启动消费者，消息虽然会被消费，但是没有触发监听方法
 */
public class Receiver {

    public static void receive(){
        Connection connection = null;
        Channel channel = null;
        try {
            connection = ConnectionUtil.getConnection();

            channel = connection.createChannel();
            //持久化、唯一、自动删除
            AMQP.Queue.DeclareOk queue = channel.queueDeclare(Sender.QUEUE_NAME, false, false, false, null);

            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("receive : " + new String(body));
                }
            };
            System.out.println("waiting fro receive ...");
            // 自动应答
            channel.basicConsume(Sender.QUEUE_NAME, true,consumer);

            //先启动消费者，进行阻塞，不然finally直接关闭了
            Thread.sleep(1000*5);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if(channel!=null) channel.close();
                if(connection!=null) connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Receiver.receive();
    }
}
