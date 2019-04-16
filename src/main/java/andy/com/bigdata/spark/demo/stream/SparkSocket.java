package andy.com.bigdata.spark.demo.stream;

import andy.com.bigdata.spark.demo.rdd.TestMysql;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;

import java.util.Arrays;

/*
 * ./bin/spark-submit --class andy.com.bigdata.spark.demo.stream.SparkSocket  --master spark://junjunkaifarnxiaojuyuwang.local:7077  /Users/junjun/Documents/项目/java/java/andy.com/target/mycodebase-WordCountPOJO.jar
 */
public class SparkSocket {
    public static void main(String[] args) throws Exception {

        SparkSession ss = SparkSession.builder().appName(TestMysql.class.getName()).getOrCreate();
        Dataset<Row> lines = ss.readStream()
                .format("socket")
                .option("host", "localhost")
                .option("port", 9999).load();

        Dataset<String> words = lines.as(Encoders.STRING()).flatMap(x -> Arrays.asList(x.split(" ")).iterator(), Encoders.STRING());
        Dataset<Row> wordCounts = words.groupBy("value").count();

        StreamingQuery query = wordCounts.writeStream()
                .outputMode("complete")
                .format("console")
                .start();
        query.awaitTermination();
    }
}
