/**
 * Remote Procedure Call
 * 大致过程如下：
 *  服务端：
 *      1、建立与RabbitMq的连接(connection)
 *      2、通过连接创建信道(channel)
 *      3、声明请求路由(exchange)，指定请求路由类型(BuiltinExchangeType)，主要用户客户端生产者发送消息
 *      4、声明队列(queue)，将队列绑定到请求路由上，指定对应的bindingKey(与客户端生产者匹配)，主要是服务的获取客户端消息使用
 *      5、服务端作为消费者，接收队列上的消息
 *      6、建立消费监听，通过BasicProperties接收客户端发送过来的信息，
 *          主要是通过ReplyTo(对应routingKey),correlationId(随机数，从客户端来，原样返回客户端)
 *      7、服务端消费者提交，接收消息完成
 *      8、消息返回，声明响应路由(exchange)，指定响应路由类型(BuiltinExchangeType)，主要用于服务端绑定响应消息
 *      9、根据响应路由，上步的ReplyTo，将消息绑定到路由上，同时由correlationId构建BasicProperties返回客户端，作为识别用
 *
 *  客户端：
 *      1、建立与RabbitMq的连接(connection)
 *      2、通过连接创建信道(channel)
 *      3、声明请求路由(exchange)，指定请求路由类型(BuiltinExchangeType)
 *      4、向服务器发送消息(携带correlationId)，消息绑定到对应的请求路由上，加上routingKey(可以为队列名)，只要能与服务端队列bindingKey匹配即可
 *      5、发送消息完成
 *      6、声明响应路由(exchange)，指定响应路由类型(BuiltinExchangeType)，获取从服务端返回的消息
 *      7、声明队列，绑定到响应队列上，定义bindingKey，该队列将作为客户端消费服务端返回的信息载体
 *          队列名作为ReplyTo，bindingKey(其实保证bindingKey、ReplyTo匹配即可，因为ReplyTo会作为服务端生产者routingKey)
 *      8、客户端作为消费者，接收响应对应的消息
 *      9、客户端消费服务器返回消息，通过correlationId验证该次应答与请求一致
 *
 *  可以分别在申明queue、exchange是设置是否持久化，同时在消息发布是设置BasicProperties(Delivary_mode)满足消息持久化
 *
 */
package com.mq.rabbitmq.rpc;