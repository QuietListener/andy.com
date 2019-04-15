package andy.com.bigdata.spark.demo.aliyun;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

/**
 * ./bin/spark-submit --class andy.com.bigdata.spark.demo.rdd.TestHadoop  --master spark://junjunkaifarnxiaojuyuwang.local:7077  /Users/junjun/Documents/项目/java/java/andy.com/target/mycodebase-WordCountPOJO.jar hdfs://localhost:9000/user/junjun/wordcount.txt
 */
public class OSSTest {


    public static void main(String[] args) throws Exception {

//        String dir = "oss://bcz-app-bigdata/test、";
//        Path path = new Path(dir);
//        Configuration conf = new Configuration();
//        conf.set("fs.oss.impl", "com.aliyun.fs.oss.nat.NativeOssFileSystem");
//        FileSystem fs = FileSystem.get(path.toUri(), conf);
//        FileStatus[] fileList = fs.listStatus(path);

        SparkConf conf1 = new SparkConf().setAppName(OSSTest.class.getName());
        conf1.set("spark.hadoop.fs.oss.impl", "com.aliyun.fs.oss.nat.NativeOssFileSystem");
        //conf1.set("spark.hadoop.mapreduce.job.run-local", "true");
        String fileUrl = "oss://bcz-ireading/wordcount.txt";

        JavaSparkContext sc = null;
        try {

            sc = new JavaSparkContext(conf1);
            JavaRDD<String> textFile = sc.textFile(fileUrl);
            JavaPairRDD<String, Integer> counts = textFile
                    .flatMap(t -> Arrays.asList(t.split(" ")).iterator())
                    .mapToPair(word -> new Tuple2<>(word, 1))
                    .reduceByKey((a, b) -> a + b);

            counts.saveAsTextFile("oss://bcz-ireading/wordcountResult.txt");
        } catch (Exception e) {
            sc.close();
        }

    }
}
