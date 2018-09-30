1、幂等性
生产环境各种业务系统出现重复消息是不可避免的，因为不能保证生产者不发送重复消息。

对于读操作而言，重复消息可能无害，但是对于写操作，重复消息容易造成业务灾难，比如相同消息多次扣减库存，多次支付请求扣款等。

有一种情况也会造成重复消息，就是RabbitMQ对设置autoAck=false之后没有被Ack的消息是不会清除掉的，消费者可以多次重复消费。

我个人认为RabbitMQ只是消息传递的载体，要保证幂等性，还是需要在消费者业务逻辑上下功夫。

2、有序消息
我碰到过某厂有一个开发团队通过Kafka来实现有序队列，因为发送的消息有先后依赖关系，需要消费者收到多个消息保存起来最后聚合后一起处理业务逻辑。

但是，其实大部分业务场景下我们都不需要消息有先后依赖关系，因为有序队列产生依赖关系，后续消费很容易造成各种处理难题。

归根结底，我认为需要有序消息的业务系统在设计上就是不合理的，争取在设计上规避才好。当然良好的设计需要丰富的经验和优化，以及妥协。

3、高可用
RabbitMQ支持集群，模式主要可分为三种：单一模式、普通模式和镜像模式。

RabbitMQ支持弹性部署，在业务高峰期间可通过集群弹性部署支撑业务系统。

RabbitMQ支持消息持久化，如果队列服务器出现问题，消息做了持久化，后续恢复正常，消息数据不丢失不会影响正常业务流程。

RabbitMQ还有很多高级特性，比如发布确认和事务等，虽然可能会降低性能，但是增强了可靠性。


声明队列（创建队列）：可以生产者和消费者都声明，也可以消费者声明生产者不声明，也可以生产者声明而消费者不声明。最好是都声明。（生产者未声明，消费者声明这种情况如果生产者先启动，会出现消息丢失的情况，因为队列未创建）

消息、队列、路由持久化的保证：事务、commit

1. mandatory标志位
当mandatory标志位设置为true时，如果exchange根据自身类型和消息routeKey无法找到一个符合条件的queue，那么会调用basic.return方法将消息返还给生产者；当mandatory设为false时，出现上述情形broker会直接将消息扔掉。
2. immediate标志位
当immediate标志位设置为true时，如果exchange在将消息route到queue(s)时发现对应的queue上没有消费者，那么这条消息不会放入队列中。当与消息routeKey关联的所有queue(一个或多个)都没有消费者时，该消息会通过basic.return方法返还给生产者。
现象：比如当recevier没有ack时，会存在对应的unacked消息，如果此时重新启动receiver，则会将所有消息重新进行消费。如果此时sender重启，则所有unacked消息都会变成ready