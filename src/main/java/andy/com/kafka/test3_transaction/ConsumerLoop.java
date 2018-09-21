package andy.com.kafka.test3_transaction;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.*;

public class ConsumerLoop  implements  Runnable{

    private  KafkaConsumer<String, String> consumer;

    private List<String> topics;

    private int id;
    private int isAsync = -1;

    public static int SYNC_SINGLE = 0;
    public static int SYNC_BATCH = 1;
    public static int ASYNC_SINGLE = 2;
    public static int ASYNC_BATCH = 3;

    public ConsumerLoop(int id,
                        String groupId,
                        List<String> topics)
    {
        this.id = id;
        this.topics = topics;

        Properties props = new Properties();
        props.put("bootstrap.servers","localhost:9093,localhost:9094");
        props.put("group.id",groupId);
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        //手动commit
        props.put("enable.auto.commit", "false");

        /**
         * Here are the possible values (default is read_uncommitted):
         * read_uncommitted: consume both committed and uncommitted messages in offset ordering.
         *
         * read_committed: only consume non-transactional messages or committed transactional messages in offset order. In order to maintain offset ordering,
         * this setting means that we will have to buffer messages in the consumer until we see all messages in a given transaction.
         */
        props.put("isolation.level", "read_committed");

        this.consumer = new KafkaConsumer<String, String>(props);
    }

    @Override
    public void run() {

        int total = 0;

        try{
            consumer.subscribe(this.topics);
            long start = new Date().getTime();

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (ConsumerRecord<String, String> record : records) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("partition", record.partition());
                    data.put("offset", record.offset());
                    data.put("value", record.value());
                    System.out.println(this.id + ": " + data);

                    total+=1;


                    //每处理一个commit一个。 同步commit，会阻塞
                    Map<TopicPartition,OffsetAndMetadata> map = Collections.singletonMap(new TopicPartition(record.topic(),record.partition()), new OffsetAndMetadata(record.offset() + 1));

                    consumer.commitSync(map);

                }

//                long end = new Date().getTime();
//                System.out.println(this.id+":consume " + total + " records; used "+ (end-start) + " miniseconds");
            }

        }
        catch(WakeupException e) {

        }
        finally {
            consumer.close();
        }


    }

    public void shutdown()
    {
        this.consumer.wakeup();
    }
}
