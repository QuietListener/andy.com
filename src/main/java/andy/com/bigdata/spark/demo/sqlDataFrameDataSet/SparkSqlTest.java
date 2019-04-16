package andy.com.bigdata.spark.demo.sqlDataFrameDataSet;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * ./bin/spark-submit --class andy.com.bigdata.spark.demo.sqlDataFrameDataSet.SparkSqlTest  --master spark://junjunkaifarnxiaojuyuwang.local:7077  /Users/junjun/Documents/项目/java/java/andy.com/target/mycodebase-WordCountPOJO.jar
 */
public class SparkSqlTest {

    static private String FilePath = "/Users/junjun/Documents/项目/java/java/andy.com/res/spark/people.json";
    public static void main(String[] args) throws Exception {
        SparkSession spark = null;

        try{
             spark = SparkSession
                    .builder()
                    .appName("Java Spark SQL basic example")
                    .getOrCreate();

            Dataset<Row> dataFrame = spark.read().json("file://"+FilePath);

            dataFrame.printSchema();
            dataFrame.show();
            /**
             * +----+-------+-------+
             * | age|   city|   name|
             * +----+-------+-------+
             * |null|   null|Michael|
             * |  30|chengdu|   Andy|
             * |  19|   null| Justin|
             * |null|   null|    Ted|
             * +----+-------+-------+
             */

        }
        finally{
            spark.close();
        }

    }
}
