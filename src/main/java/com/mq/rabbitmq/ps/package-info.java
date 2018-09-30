/**

 示例主要是描述了Publish/Subscribe模式的使用

 关键词：
 服务（Broker）：RabbitMQ server
 连接（Connection）：调用RabbitMQ服务前，必选先与服务建立连接socket
 信道（Channel）：所有的对象（客户端）都是有channel建立
 生产者（Producer）：产生消息
 消费者（Consumer）：消费消息
 队列 （Queue）：消息的载体
 路由 （Exchange）：将生产的消息通过路由到指定的队列上（通过路由的类型，可以定义灵活的routingKey）,消费者将队列绑定到路由上，
 获取匹配（bindingKey）的 消息

 */
package com.mq.rabbitmq.ps;
