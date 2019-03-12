参考：https://www.confluent.io/blog/exactly-once-semantics-are-possible-heres-how-apache-kafka-does-it/

####消息队列中很常见的一个话题就是 "消息重复消费","精确一次投递"

###一、消息重复消费原因有两大类
####1.producer这端
如果producer发送给broker，borker持久化后，返回producer ack由于网络或者其他原因失败了,producer重发
会导致统一消息发送了2次。
####2.consumer端
正常流程是consumer 消费消息，消费后发送ack(kafka是offset)给broker。broker，消费成功，发送ack失败。就会出现重复消费问题
由consumer引起的重复消费比较好解决，也是MQ的一个基础问题----幂等。确保消费是幂等的就好。

###二、kafka的producer处理精确一次投递

