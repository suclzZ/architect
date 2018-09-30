package com.mq.activemq;

import org.apache.activemq.ActiveMQConnection;

/**
 * jms服务器相关参数
 */
public class Config {

    public final static String URL = ActiveMQConnection.DEFAULT_BROKER_URL;// "tcp://localhost:61616";
    public final static String USERNAME = ActiveMQConnection.DEFAULT_USER;//"admin";
    public final static String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//"admin";

}
