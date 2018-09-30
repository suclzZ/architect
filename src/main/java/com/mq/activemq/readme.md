生产/消费模式、发布/订阅模式

point-to-point\
1、创建ConnectionFactory
2、获取connection
3、打开连接connection.start
4、创建Session
5、通过Session创建Destination
6、通过Session建立Producer/Consumer，与Destination关联
7、Producer/Consumer进行send/receive操作
8、session.commit
9、connection.close

publish/subscriber
1、