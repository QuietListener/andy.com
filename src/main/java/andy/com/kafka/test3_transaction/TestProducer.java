package andy.com.kafka.test3_transaction;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestProducer
{
    static final int SYNC = 1;
    static final int ASYNC = 2;

    static  int type = SYNC;

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9093,localhost:9094,localhost:9094");

        /* acks=all This means the leader will wait for the full set of in-sync replicas to acknowledge the record. This guarantees that the record will not be lost as long as at least one in-sync replica remains alive. This is the strongest available guarantee. This is equivalent to the acks=-1 setting.
         * 生产者需要server端在接收到消息后，进行反馈确认的尺度，主要用于消息的可靠性传输；acks=0表示生产者不需要来自server的确认；acks=1表示server端将消息保存后即可发送ack，而不必等到其他follower角色的都收到了该消息；acks=all(or acks=-1)意味着server端将等待所有的副本都被接收后才发送确认。
         */
        props.put("acks", "all");

        //retries:生产者发送失败后，重试的次数.
        // 0表示不让他重传，自己控制重传
        // 如果大于1 不能保证record在partition中的顺序:比如record1 发送了，然后record2也发送了，record2成功，record1失败，然后record1重试成功。顺序就变成了 record2，record1
        props.put("retries", 1);

        //16k
        props.put("batch.size", 16384);

        //在正常负载的情况下, 要想减少请求的数量. 加上一个认为的延迟: 不是立即发送消息, 而是延迟等待更多的消息一起批量发送
        props.put("linger.ms", 1);

        //压缩整个batch的数据,batch数据越多压缩率越好
        //props.put("compression.type","gzip");

        //32M
        props.put("buffer.memory", 33554432);

        /*==========下面是关于事务的配置==========*/

        //幂等
        props.put("enable.idempotence", true);

        // 设置事务id
        props.put("transactional.id", "first-transactional");

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //生产者发送消息
        String topic = "consumer-tutorial";

        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        long start = new Date().getTime();

        producer.initTransactions();
        int total = 2;
        for (int i = 1; i <= total; i++) {

            producer.beginTransaction();

            String value = "value_" + i + "     asdfasdfa;sdlfjal;sdfj;alsdfjla;sjflajflajsfa;sd" + new Random().nextInt(1000) + "fhjakldfhashdfahsdfkhadksah";

            ProducerRecord<String, String> msg = new ProducerRecord<String, String>(topic, value);

            try {

                RecordMetadata rm = producer.send(msg).get(1000, TimeUnit.MILLISECONDS);
                System.out.println(rm.topic()+","+rm.partition()+","+rm.offset()+","+value);
                int interval = 10000;
                if(i == 1) {
                    throw new Exception("exception");
                }

                System.out.println("sleep " + interval +" miliseconds");
                Utils.sleep(interval);

                //commitTransaction只后consumer才能收到消息
                System.out.println("commited");
                producer.commitTransaction();

            } catch (TimeoutException e) {
                e.printStackTrace();
                producer.abortTransaction();
            } catch (Exception e) {
                e.printStackTrace();
                producer.abortTransaction();
            }

        }



        //列出topic的相关信息
        List<PartitionInfo> partitions = new ArrayList<PartitionInfo>() ;
        partitions = producer.partitionsFor(topic);

        for(PartitionInfo p:partitions)
        {
            System.out.println(p);
        }

        System.out.println("send message over.");
        producer.close();

        long end = new Date().getTime();
        System.out.println("send " + total + " records; used "+ (end-start) + " miniseconds");
    }
}


class ProducerCallback implements  Callback
{
    private String value = null;
    public ProducerCallback(String value)
    {
        this.value = value;
    }

    @Override
    public void onCompletion(RecordMetadata m, Exception exception) {

        if(null != exception)
        {
            exception.printStackTrace();
            //do some logic
        }
        else
        {
            System.out.println(m.topic() +"："+ m.partition() + ":" + m.hasOffset()+" ："+value+" success");
        }
    }
}

