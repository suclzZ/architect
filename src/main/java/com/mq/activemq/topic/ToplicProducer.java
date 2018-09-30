package com.mq.activemq.topic;

import com.mq.activemq.Config;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消费者需要先与生产者启动，否则无法接收到消息
 */
public class ToplicProducer {

    public void send(){
        //Topic连接
        TopicConnection connection = null;
        //Topic会话
        TopicSession session = null;
        try {
            // 1、创建链接工厂
            TopicConnectionFactory factory = new ActiveMQConnectionFactory(Config.USERNAME, Config.PASSWORD, Config.URL);
            // 2、通过工厂创建一个连接
            connection = factory.createTopicConnection();
            // 3、启动连接
            connection.start();
            // 4、创建一个session会话
            session = connection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 5、创建一个消息队列
            Topic topic = session.createTopic("queue.topic1");
            // 6、创建消息发送者
            TopicPublisher publisher = session.createPublisher(topic);
            // 设置持久化模式
            publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            sendMessage(session, publisher);
            // 提交会话
            session.commit();


        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();

                if (connection != null) connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(TopicSession session, TopicPublisher publisher) {
        for(int i=1;i<=10;i++){
            String text = "消息 "+i;
            System.out.println("生产消息："+text);
            try {
                publisher.send(session.createTextMessage(text));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ToplicProducer().send();
    }
}
