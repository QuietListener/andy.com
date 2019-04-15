package andy.com.bigdata.spark.demo.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ./bin/spark-submit --class andy.com.bigdata.spark.demo.rdd.SparkOperations  --master spark://junjunkaifarnxiaojuyuwang.local:7077  /Users/junjun/Documents/项目/java/java/andy.com/target/mycodebase-WordCountPOJO.jar
 */
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
            JavaRDD rddMap = orgData.map(t -> t + 1); //[2,3,4,5,6]
            rddMap.foreach(t -> System.out.println("map:" + t));

            /**
             * mapPartitions
             * mapPartitions与map的作用一样，但是mapPartition会一次性把一个partition的数据load进内容，容易出现oom。
             * mapPartitions比较适合需要分批处理数据的情况，比如将数据插入某个表，每批数据只需要开启一次数据库连接，大大减少了连接开支
             */
            List<String> retMapPartion = orgData.mapPartitions(items -> {
                List<String> tmp = new ArrayList<>();
                /** 用mapPartition 插入数据每个partition只需要建立一次连接 */
                //打开数据库连接
                items.forEachRemaining(t -> {
                    tmp.add(t + "*");
                    //插入数据
                });
                //关闭连接
                return tmp.iterator();
            }).collect();
            System.out.println("retMapPartion:" + retMapPartion);

            /**
             *filter
             */
            JavaRDD rddFilter = orgData.filter(t -> t > 3); //[4,5]
            rddFilter.foreach(t -> System.out.println("filter:" + t));

            /**
             * flatMap
             * flatMap把list展平，[[1, 2, 3],[4,5]]=>[1, 2, 3,4,5]
             */
            List<List<Integer>> data1 = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5));
            JavaRDD<List<Integer>> orgData1 = sc.parallelize(data1);

            //接受一个返回Iterator的函数
            JavaRDD rddFlatmap = orgData1.flatMap(t -> t.iterator());
            System.out.println("flatMap:" + rddFlatmap.collect()); //flatMap:[1, 2, 3, 4, 5]

            //把每个加1
            JavaRDD rddFlatmap1 = orgData1.flatMap(t -> {
                return t.stream().map(a -> a + "_").collect(Collectors.toList()).iterator();
            });
            List<String> ret = rddFlatmap1.collect();
            System.out.println("flatMap1:" + ret); //flatMap1:[1_, 2_, 3_, 4_, 5_]


            /**
             * sample抽样
             * https://stackoverflow.com/questions/32837530/how-to-get-a-sample-with-an-exact-sample-size-in-spark-rdd
             *  withReplacement:第一个参数决定在采样完成后是否将样本再放回去，类似于抽签完成后再把签放回去留给后面的人抽。
             *  withReplacement为true可能产生重复原生
             *
             *  fraction:第二个参数也就是说每个元素以fraction的概率被抽到
             *
             *  每次结果可能都不一样
             */
            List<Integer> rddSample1 = orgData.sample(false, 0.5).collect();
            List<Integer> rddSample2 = orgData.sample(false, 0.5).collect();
            System.out.println("rddSample1:" + rddSample1);//rddSample1:[3, 5]
            System.out.println("rddSample12" + rddSample2);//rddSample1:[1, 4]


            /**
             * Union
             * 求并集
             */
            JavaRDD<Integer> orgData2 = sc.parallelize(Arrays.asList(2,12,13));
            List<Integer> union = orgData.union(orgData2).collect();
            System.out.println("union:" + union);

            /**
             * intersection
             * 交集
             */
            List<Integer> intersection = orgData.intersection(orgData2).collect();
            System.out.println("intersection:" + intersection); //intersection:[2]


            /**
             * groupByKey
             * 分组函数
             */

            JavaRDD<Person> prdd1 = sc.parallelize(Arrays.asList(
                    new Person("a","chengdu",1)
                    ,new Person("b","chengdu",1)
                    ,new Person("c","chongqing",1)
                    ,new Person("a","chongqing",1) ));

            JavaPairRDD<String,Iterable<Person>> pairRdd1 = prdd1.groupBy(t->t.city);
            pairRdd1.foreach(t->{
                String city = t._1();
                Iterable<Person> ps = t._2();
                System.out.println("groupByKey:  "+city+":"+ ps.toString());
            });

        } finally {
            sc.close();
        }
    }


    static class Person{
        public String name;
        public String city;
        public Integer age;

        public Person(String name, String city,Integer age){
            this.name = name;
            this.age = age;
            this.city = city;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null){
                return false;
            }

            if(!this.getClass().isInstance(obj)){
                return false;
            }

            Person p = (Person)obj;
            return (this.name == p.name &&  this.city == p.city)
                    || (this.name!=null && this.name.equals(p.name) && this.city.equals(p.city));
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", city='" + city + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
