package com.mq.activemq.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 生产者
 * 生产消息，消费后消息不会被清理
 */
public class Producer {

    private String username = "admin";
    private String password = "admin";
    private String url = "tcp://localhost:61616";

    public void send(){
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        try {
//            ((ActiveMQConnectionFactory) connectionFactory).setBrokerURL(url);
            Connection connection = connectionFactory.createConnection(username, password);
            connection.start();
            System.out.println("connect start...");

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Queue destination= session.createQueue("queue.a");

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMessage(session,producer);

            session.commit();

            connection.close();
            System.out.println("connect close...");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Session session,MessageProducer producer) throws JMSException {
        for(int i=0 ; i<10;i++){
            TextMessage message = session.createTextMessage("消息" + i);
            producer.send(message);
        }
    }

    public void init (){
        System.getenv("");
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.send();
    }
}
