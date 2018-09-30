package com.mq.rabbitmq.ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 过程：
 * 1、获取连接
 * 2、创建channel
 * 3、获取路由器
 * 4、发布消息到指定路由器
 * 注：如果消息发布到对应的路由上，但是该路由还没绑定任何的队列，那么该消息会丢失，所以需要优先启动消费端，因为那边会优先做路由器与队列的绑定工作
 * 在pub中
 *
 */
public class Publisher {

    public static final String exchangeName = "x.pc2";
    public static final String[] routingKeys = {"rk0","rk1"};

    public static void send() throws IOException, TimeoutException {
        Connection connection = ConnFactory.getConnection();
        Channel channel = connection.createChannel();
        /**
         * 参数1：名称；参数2：类型（Fanout、Direct、Topic、Header）
         * 类型：
         *  Fanout：所有发送到该Exchange的消息路由到所有与它绑定的Queue
         *  Direct：Routing Key 与 Binding Key 完全匹配
         *  Topic： 通配符规则的 routing-key，#(more) *(one)[消费者绑定队列时]。eg.生产者routingKey abc.a.com,消费者 bindingKey abc.*.com、#.a.com都可以匹配。
         *          当队列绑定到#时，与Fanout等意，当没有通配符时，与Direct等意
         *  Header：自定义匹配规则。在队列与交换器绑定时, 会设定一组键值对（Key-Value）规则, 消息中也包括一组键值对( Headers 属性)，当这些键值对有一对,，或全部匹配时， 消息被投送到对应队列
         */
        channel.exchangeDeclare(exchangeName,"direct");


        byte[] message = buildMessage();
        /**
         * 将消息发布到路由器中，根据路由器类型FANOUT，会自动将消息分发到与其绑定的queue中
         * 同时可以指路由id（routingKey）
         */

        while (condition()){
            String routingKey = routingKeys[(int)((Math.random()*10)%2)];
            String msg = new String(message) + " - [" +routingKey + "] "+new Date();
            System.out.println("send message :" +msg);
            //publish时为routingKey，subscribe时为bindingKey
            channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        channel.close();
        ConnFactory.close(connection);
    }

    private static boolean condition() {
        return true;
    }

    private static byte[] buildMessage() {
        return "pc message".getBytes();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Publisher.send();
    }
}
