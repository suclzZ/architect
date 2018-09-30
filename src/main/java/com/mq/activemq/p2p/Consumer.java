package com.mq.activemq.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消费者
 */
public class Consumer {
    private String username = "admin";
    private String password = "admin";
    private String url = "tcp://localhost:61616";

    public void receive(){
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Session session = null;
        Connection connection = null;
        try {
//            ((ActiveMQConnectionFactory) connectionFactory).setBrokerURL(url);
            connection = connectionFactory.createConnection(username, password);
            connection.start();
            System.out.println("connect start...");
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("queue.a");
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("接收的消息："+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

//            while (true){
//                Message message = consumer.receive(1000);
//                if(message!=null)
//                    System.out.println("接收的消息："+((TextMessage)message).getText());
//            }
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
//            try {
//                if(session!=null) session.close();
//                if(connection!=null) connection.close();
//            } catch (JMSException e) {
//                e.printStackTrace();
//            }
        }
    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.receive();
    }
}
