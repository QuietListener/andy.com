package andy.com.bigdata.spark.demo.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

/**
 * ./bin/spark-submit --class andy.com.bigdata.spark.demo.rdd.Test1  --master spark://junjunkaifarnxiaojuyuwang.local:7077  /Users/junjun/Documents/项目/java/java/andy.com/target/mycodebase-WordCountPOJO.jar
 */
public class Test1 {

    static String url = "local";//;"spark://junjunkaifarnxiaojuyuwang.local:7077";

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(Test1.class.getName()).setMaster(url);
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> distData = sc.parallelize(data);

        distData.collect().stream().forEach(t->System.out.println("***:"+t));
        sc.close();
    }
}
