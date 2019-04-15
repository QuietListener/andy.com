package andy.com.bigdata.spark.demo.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *  ./bin/spark-submit --class andy.com.bigdata.spark.demo.rdd.SparkOperations  --master spark://junjunkaifarnxiaojuyuwang.local:7077  /Users/junjun/Documents/项目/java/java/andy.com/target/mycodebase-WordCountPOJO.jar
 * */
public class SparkOperations {

    static String url = "local";
    public static void main(String[] args) {


        SparkConf conf = new SparkConf().setAppName(Test1.class.getName()).setMaster(url);
        JavaSparkContext sc = new JavaSparkContext(conf);
        try {
            List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
            JavaRDD<Integer> orgData = sc.parallelize(data);

            /**
             * map
             */
            JavaRDD rddMap = orgData.map(t->t+1); //[2,3,4,5,6]
            rddMap.foreach(t->System.out.println("map:"+t));

            /**
             * mapPartitions
             * mapPartitions与map的作用一样，但是mapPartition会一次性把一个partition的数据load进内容，容易出现oom。
             * mapPartitions比较适合需要分批处理数据的情况，比如将数据插入某个表，每批数据只需要开启一次数据库连接，大大减少了连接开支
             */
            List<String> retMapPartion = orgData.mapPartitions(items->{
                List<String> tmp = new ArrayList<>();
                /** 用mapPartition 插入数据每个partition只需要建立一次连接 */
                //打开数据库连接
                items.forEachRemaining(t->{
                  tmp.add(t+"*");
                  //插入数据
                });
                //关闭连接
                return tmp.iterator();
            }).collect();
            System.out.println("retMapPartion:"+retMapPartion);

            /**
             *filter
             */
            JavaRDD rddFilter = orgData.filter(t->t>3); //[4,5]
            rddFilter.foreach(t->System.out.println("filter:"+t));

            /**
             * flatMap
             * flatMap把list展平，[[1, 2, 3],[4,5]]=>[1, 2, 3,4,5]
             */
            List<List<Integer>> data1 = Arrays.asList(Arrays.asList(1, 2, 3),Arrays.asList( 4, 5));
            JavaRDD<List<Integer>> orgData1 = sc.parallelize(data1);

            //接受一个返回Iterator的函数
            JavaRDD rddFlatmap = orgData1.flatMap(t->t.iterator());
            System.out.println("flatMap:"+rddFlatmap.collect()); //flatMap:[1, 2, 3, 4, 5]

            //把每个加1
            JavaRDD rddFlatmap1 = orgData1.flatMap(t->{
                return t.stream().map(a->a+"_").collect(Collectors.toList()).iterator();
            });
            List<String> ret = rddFlatmap1.collect();
            System.out.println("flatMap1:"+ret); //flatMap1:[1_, 2_, 3_, 4_, 5_]





        }finally {
            sc.close();
        }
    }
}
