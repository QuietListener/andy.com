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

        //acks=all This means the leader will wait for the full set of in-sync replicas to acknowledge the record. This guarantees that the record will not be lost as long as at least one in-sync replica remains alive. This is the strongest available guarantee. This is equivalent to the acks=-1 setting.
        //保证leader上的数据都同步到follower上面，推荐的配置
        props.put("acks","all");

        //不让他重传，自己控制重传
        props.put("retries",0);

        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
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
