package com.mq.activemq.queue;

import com.mq.activemq.Config;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 生产的消息并非直接移除
 * 比如生产者生产10条消息，同时存在3个消费者，其中某一个消费者将某条消息消费掉，其它消费者就不再获取该条消息
 * 但是如果取消所有消费者，重新打开新的消费者，则新消费者可以一次性消费所有原有的消息
 * 所以并不是消息消费就被彻底清除
 * 如果session.commit();
 * 那么消息队列将被清空
 */
public class QueueProducer {

    public void send(){
        QueueConnectionFactory queueConnectionFactory = new ActiveMQConnectionFactory(Config.USERNAME,Config.PASSWORD,Config.URL);
        QueueConnection connection = null;
        QueueSession session = null;
        try {
            connection = queueConnectionFactory.createQueueConnection();
            connection.start();
            /**
             * 是否交易：true/false
             * 认证模式：Session.AUTO_ACKNOWLEDGE
             *          Session.CLIENT_ACKNOWLEDGE
             *          Session.DUPS_OK_ACKNOWLEDGE
             */
            session = connection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("queue.d1");
            QueueSender sender = session.createSender(queue);
            sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            sendMessage(session,sender);

        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            try {
                if(connection!=null) connection.close();
                if(session!=null) session.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(QueueSession session, QueueSender sender) {
        try {
            String text = "消息"+Math.random();
            System.out.println("发送消息：" + text);
            Message message = session.createTextMessage(text);
            sender.send(message);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        QueueProducer producer = new QueueProducer();
        producer.send();
    }
}
