package andy.com.bigdata.spark.demo.rdd;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

/**
 *  ./bin/spark-submit --class andy.com.bigdata.spark.demo.rdd.TestMysql  --master spark://junjunkaifarnxiaojuyuwang.local:7077  --driver-class-path /Users/junjun/.m2/repository/mysql/mysql-connector-java/5.1.38/mysql-connector-java-5.1.38.jar --jars /Users/junjun/.m2/repository/mysql/mysql-connector-java/5.1.38/mysql-connector-java-5.1.38.jar /Users/junjun/Documents/项目/java/java/andy.com/target/mycodebase-WordCountPOJO.jar
 * */
public class TestMysql {


    public static void main(String[] args) {

        Properties prop = new Properties();
        prop.put("user", "root");
        prop.put("password", "");
        String jdbcUrl = "jdbc:mysql://localhost:3306/test_01";

        SparkSession ss = null;
        try {
            ss = SparkSession.builder().appName(TestMysql.class.getName()).getOrCreate();
            Dataset<Row> ds = ss.read().jdbc(jdbcUrl, "user", prop);
            ds.printSchema();
            ds.filter("age > 1000").select("name").show(); //age大于1000的记录
        } finally {
            ss.close();
        }

    }
}
