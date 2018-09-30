package com.mq.rabbitmq.ps;

import com.rabbitmq.client.*;
import org.apache.activemq.leveldb.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 过程：
 * 1、获取连接
 * 2、创建channel
 * 3、获取路由器
 * 4、将路由器与队列绑定,同时可设定bindingKey
 * 5、从队列中消费消息
 * 启动多个消费者，均可以获取消息
 * 在发布者绑定给路由加上指定的routingKey,在消费时从指定的routingKey获取消息,当然需要修改BuiltinExchangeType
 */
public class Subscriber {

    private final static int TIMEOUT = 1000*10;

    public static void receive() throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnFactory.getConnection();
        Channel channel = connection.createChannel();
        /**
         * 参数1：名称；参数2：类型（Fanout、Direct、Topic、Header）
         * 类型：
         *  Fanout：所有发送到该Exchange 的消息路由到所有与它绑定 的Queue
         *  Direct：Routing Key 与 Binding Key 完全匹配
         *  Topic： 通配符规则的 routing-key，# *
         *  Header：自定义匹配规则。在队列与交换器绑定时, 会设定一组键值对（Key-Value）规则, 消息中也包括一组键值对( Headers 属性)，当这些键值对有一对,，或全部匹配时， 消息被投送到对应队列
         */
        channel.exchangeDeclare(Publisher.exchangeName,"direct");

        AMQP.Queue.DeclareOk queue = channel.queueDeclare();
        String queueName = queue.getQueue();
        String routingKey = Publisher.routingKeys[(int)((Math.random()*10)%2)];
        /**
         * 第三个参数 routingKey(bindingKey)
         * 可以将一个queue同时绑定到多个routingKey上，则可以监听多个routingKey对应路由上的消息
         */
        channel.queueBind(queueName,Publisher.exchangeName,routingKey);

        Mointor mointor = new Mointor();
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                mointor.received = true;
                envelope.getRoutingKey();
                //消息监听
                System.out.println("receive : "+new String(body));
            }
        };

        long start = System.currentTimeMillis();
        while (true){
            long now = System.currentTimeMillis();
            if(mointor.received){
                start = now;
            }
            if(now-start>=TIMEOUT){
                System.out.println("time out");
                break;
            }
            System.out.println("receive ready..." + " [routingKey:"+routingKey+"]");
            channel.basicConsume(queueName,true,consumer);
            mointor.received = false;
            Thread.sleep(1000);
        }

        channel.close();
        ConnFactory.close(connection);

 /*       Thread thread = new Thread(() -> {
            while (true) {
                try {
                    channel.basicConsume(queueName, true, consumer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(thread.isAlive()){
                    thread.stop();
                }
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                ConnFactory.close(connection);
                timer.cancel();
            }
        },TIMEOUT);*/

    }

    private static class Mointor{
        private boolean received;
    }

    public static void main(String[] args) throws InterruptedException, TimeoutException, IOException {
        Subscriber.receive();
    }
}
