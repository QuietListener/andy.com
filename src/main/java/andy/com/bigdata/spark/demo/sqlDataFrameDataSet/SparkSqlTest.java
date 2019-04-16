package andy.com.bigdata.spark.demo.sqlDataFrameDataSet;

import org.apache.spark.sql.*;
import org.apache.spark.sql.catalyst.expressions.aggregate.Sum;

import java.io.Serializable;

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


            //讲DF注册为一个临时View，就可以使用sql进行查询
            dataFrame.createOrReplaceTempView("people");
            Dataset<Row> sqlDf = spark.sql("select city,name from people where age is not null");
            sqlDf.show();
            /**
             * +-------+------+
             * |   city|  name|
             * +-------+------+
             * |chengdu|  Andy|
             * |   null|Justin|
             * +-------+------+
             */


            /**
             *
             * 使用Datasets的好处:使用特殊的Encoder,将obj转化为bytes,在进行filter，sort,hash的时候都不用将byte转回Obj，加快速度
             * Datasets are similar to RDDs, however, instead of using Java serialization or Kryo they use a specialized Encoder
             * to serialize the objects for processing or transmitting over the network. While both encoders and standard serialization
             * are responsible for turning an object into bytes, encoders are code generated dynamically and use a format that
             * allows Spark to perform many operations like filtering, sorting and hashing without deserializing the bytes back into an object.
             */

            //使用encoder转为具体的类型
            Encoder<Person> personEncoder = Encoders.bean(Person.class);
            Dataset<Person> personDataset = spark.read().json("file://"+FilePath).as(personEncoder);
            personDataset.show();


            /**
             * DataFrame的group和聚合
             */
            Dataset<Row> group = personDataset.groupBy("city")
                    .agg(functions.expr("sum(age) as total_age"),functions.max("age").as("maxage"))
                    .select(functions.expr("*"),functions.expr("concat(city,'__') as city_"));
            group.show();
            /**
             * +---------+---------+------+-----------+
             * |     city|total_age|maxage|      city_|
             * +---------+---------+------+-----------+
             * |     null|     null|  null|       null|
             * |chongqing|       20|    20|chongqing__|
             * |  chengdu|       49|    30|  chengdu__|
             * +---------+---------+------+-----------+
             */






        }
        finally{
            spark.close();
        }

    }
}

class Person implements Serializable {
    private String name;
    private int age;
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
