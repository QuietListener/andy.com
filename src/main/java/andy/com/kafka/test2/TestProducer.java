package andy.com.kafka.test2;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class TestProducer
{
    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers","localhost:9093,localhost:9094");

        /* acks=all This means the leader will wait for the full set of in-sync replicas to acknowledge the record. This guarantees that the record will not be lost as long as at least one in-sync replica remains alive. This is the strongest available guarantee. This is equivalent to the acks=-1 setting.
         * 生产者需要server端在接收到消息后，进行反馈确认的尺度，主要用于消息的可靠性传输；acks=0表示生产者不需要来自server的确认；acks=1表示server端将消息保存后即可发送ack，而不必等到其他follower角色的都收到了该消息；acks=all(or acks=-1)意味着server端将等待所有的副本都被接收后才发送确认。
        */
        props.put("acks","all");

        //retries:生产者发送失败后，重试的次数.
        // 0表示不让他重传，自己控制重传
        // 如果大于1 不能保证record在partition中的顺序:比如record1 发送了，然后record2也发送了，record2成功，record1失败，然后record1重试成功。顺序就变成了 record2，record1
        props.put("retries",1);

        props.put("batch.size", 16384);

        //在正常负载的情况下, 要想减少请求的数量. 加上一个认为的延迟: 不是立即发送消息, 而是延迟等待更多的消息一起批量发送
        props.put("linger.ms", 1);

        //压缩整个batch的数据,batch数据越多压缩率越好
        //props.put("compression.type","gzip");

        //32M
        props.put("buffer.memory", 33554432);

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //生产者发送消息
        String topic = "consumer-tutorial";

        Producer<String, String> procuder = new KafkaProducer<String,String>(props);

        long start = new Date().getTime();

        int total = 20000;
        for (int i = 1; i <= total; i++) {
            String value = "value_" + i + "     asdfasdfa;sdlfjal;sdfj;alsdfjla;sjflajflajsfa;sd"+new Random().nextInt(1000)+"fhjakldfhashdfahsdfkhadksah";
            ProducerRecord<String, String> msg = new ProducerRecord<String, String>(topic, value);
            procuder.send(msg);
        }

        //列出topic的相关信息
        List<PartitionInfo> partitions = new ArrayList<PartitionInfo>() ;
        partitions = procuder.partitionsFor(topic);

        for(PartitionInfo p:partitions)
        {
            System.out.println(p);
        }

        System.out.println("send message over.");
        procuder.close();

        long end = new Date().getTime();
        System.out.println("send " + total + " records; used "+ (end-start) + " miniseconds");
    }
}
