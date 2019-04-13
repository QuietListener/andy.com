package andy.com.bigdata.spark.demo.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 *  ./bin/spark-submit --class andy.com.bigdata.spark.demo.rdd.TestHadoop  --master spark://junjunkaifarnxiaojuyuwang.local:7077  /Users/junjun/Documents/项目/java/java/andy.com/target/mycodebase-WordCountPOJO.jar hdfs://localhost:9000/user/junjun/wordcount.txt
 * */
public class TestHadoop {


    public static void main(String[] args) {
        JavaSparkContext sc = null;
        try {
            String hdfUrl = args[0];
            SparkConf conf = new SparkConf().setAppName(TestHadoop.class.getName());
            sc = new JavaSparkContext(conf);

            JavaRDD<String> textFile = sc.textFile(hdfUrl);
            JavaPairRDD<String, Integer> counts = textFile
                    .flatMap(t -> Arrays.asList(t.split(" ")).iterator())
                    .mapToPair(word -> new Tuple2<>(word, 1))
                    .reduceByKey((a, b) -> a + b);

            counts.saveAsTextFile("hdfs://localhost:9000/user/junjun/wordCountResult.txt");
        }finally {
            sc.close();
        }

    }
}
