package com.mq.activemq.topic;

import com.mq.activemq.Config;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ToplicConsumer {

    public void receive(){
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
            TopicSubscriber subscriber = session.createSubscriber(topic);

            receiveMessage(session, subscriber);

            Thread.sleep(1000*10);

            // 提交会话
            session.commit();

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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

    private void receiveMessage(TopicSession session, TopicSubscriber subscriber) {
        try {
            subscriber.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if(message !=null && message instanceof TextMessage){
                        try {
                            System.out.println("消费消息："+((TextMessage) message).getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ToplicConsumer().receive();
    }
}
