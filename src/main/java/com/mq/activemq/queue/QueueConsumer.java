package com.mq.activemq.queue;

import com.mq.activemq.Config;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueConsumer {

    public void receive(){
        QueueConnectionFactory queueConnectionFactory = new ActiveMQConnectionFactory(Config.USERNAME,Config.PASSWORD,Config.URL);
        QueueConnection connection = null;
        QueueSession session = null;
        try {
            connection = queueConnectionFactory.createQueueConnection();
            connection.start();
            session = connection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("queue.d1");
            QueueReceiver receiver = session.createReceiver(queue);

            receiveMessage(session,receiver);

            Thread.sleep(1000 * 10);

            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection!=null) connection.close();
                if(session!=null) session.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveMessage(QueueSession session, QueueReceiver sender) {
        try {
            System.out.println("准备接收消息...");
            sender.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if(message!=null && message instanceof TextMessage){
                        try {
                            System.out.println("接收消息 ："+ ((TextMessage) message).getText());
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
        QueueConsumer consumer = new QueueConsumer();
        consumer.receive();
    }
}
