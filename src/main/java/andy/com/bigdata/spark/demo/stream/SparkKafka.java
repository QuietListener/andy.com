package andy.com.bigdata.spark.demo.stream;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;

import java.util.Arrays;

/*
 * ./bin/spark-submit  --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.1  --master spark://junjunkaifarnxiaojuyuwang.local:7077  --class andy.com.bigdata.spark.demo.stream.SparkKafka  /Users/junjun/Documents/项目/java/java/andy.com/target/mycodebase-WordCountPOJO.jar
 */
public class SparkKafka {


    public static void main(String[] args) throws Exception {

        SparkSession spark = SparkSession.builder().appName(SparkKafka.class.getName()).getOrCreate();
        // Create DataSet representing the stream of input lines from kafka
        Dataset<String> lines = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "test")
                .load()
                .selectExpr("CAST(value AS STRING)")
                .as(Encoders.STRING());

//        // Generate running word count
//        Dataset<Row> wordCounts = lines.flatMap(
//                (FlatMapFunction<String, String>) x -> Arrays.asList(x.split(" ")).iterator(),
//                Encoders.STRING()).groupBy("value").count();

        // Start running the query that prints the running counts to the console
        StreamingQuery query = lines.writeStream()
                .outputMode("update")
                .format("console")
                .start();

        query.awaitTermination();
    }
}
