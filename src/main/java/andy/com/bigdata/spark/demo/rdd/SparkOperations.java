package andy.com.bigdata.spark.demo.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.Serializable;
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
            rddMap.foreach(t ->System.out.println("RESULT#map:" + t));

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
           System.out.println("RESULT#retMapPartion:" + retMapPartion);

            /**
             *filter
             */
            JavaRDD rddFilter = orgData.filter(t -> t > 3); //[4,5]
            rddFilter.foreach(t ->System.out.println("RESULT#filter:" + t));

            /**
             * flatMap
             * flatMap把list展平，[[1, 2, 3],[4,5]]=>[1, 2, 3,4,5]
             */
            List<List<Integer>> data1 = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5));
            JavaRDD<List<Integer>> orgData1 = sc.parallelize(data1);

            //接受一个返回Iterator的函数
            JavaRDD rddFlatmap = orgData1.flatMap(t -> t.iterator());
           System.out.println("RESULT#flatMap:" + rddFlatmap.collect()); //flatMap:[1, 2, 3, 4, 5]

            //把每个加1
            JavaRDD rddFlatmap1 = orgData1.flatMap(t -> {
                return t.stream().map(a -> a + "_").collect(Collectors.toList()).iterator();
            });
            List<String> ret = rddFlatmap1.collect();
           System.out.println("RESULT#flatMap1:" + ret); //flatMap1:[1_, 2_, 3_, 4_, 5_]


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
           System.out.println("RESULT#rddSample1:" + rddSample1);//rddSample1:[3, 5]
           System.out.println("RESULT#rddSample12" + rddSample2);//rddSample1:[1, 4]


            /**
             * Union
             * 求并集
             */
            JavaRDD<Integer> orgData2 = sc.parallelize(Arrays.asList(2,12,13));
            List<Integer> union = orgData.union(orgData2).collect();
           System.out.println("RESULT#union:" + union);

            /**
             * intersection
             * 交集
             */
            List<Integer> intersection = orgData.intersection(orgData2).collect();
           System.out.println("RESULT#intersection:" + intersection); //intersection:[2]


            /**
             * groupByKey
             * 分组函数
             */

            JavaRDD<Person> prdd1 = sc.parallelize(Arrays.asList(
                    new Person("a","chengdu",1)
                    ,new Person("b","chengdu",3)
                    ,new Person("c","chongqing",4)
                    ,new Person("a","chongqing",1)
                    ,new Person("d","chongqing",2) ));

            JavaPairRDD<String,Iterable<Person>> pairRdd1 = prdd1.groupBy(t->t.city);
            pairRdd1.foreach(t->{
                String city = t._1();
                Iterable<Person> ps = t._2();
               System.out.println("RESULT#groupByKey:  "+city+":"+ ps.toString());
            });


            /**
             * reduceByKey
             * 按key进行聚集
             * 先分组一下，再对每一组内进行reduce。
             */

            JavaPairRDD<String,Person> reduceByKeyRdd = prdd1.mapToPair(t-> new Tuple2<String, Person>(t.city,t)).reduceByKey((o1,o2)->{
                 return new Person(o1.name,o1.city,o1.age+o2.age);
            });
            reduceByKeyRdd.foreach(t->{
                String city = t._1();
                Person p = t._2();
               System.out.println("RESULT#reduceByKey:  "+city+":"+ p);
            });


            /**
             * aggregateByKey
             * aggregateByKey 与 reduceByKey 相似，多了一个 zeroValue和seqFunction
             * seqFunction 接受 tubue的value，可以进行一次变换后再进行reduce操作
             */
            JavaPairRDD<String,String> aggregateByKey = prdd1
                                                        .mapToPair(t-> new Tuple2<String, Person>(t.city,t))
                                                        .aggregateByKey( "start" //zeroValue
                                                                ,(String zeroValue,Person p)-> {System.out.println("**:"+zeroValue+":"+p);return zeroValue+p.age;} //seqFunction
                                                                ,(a,b)->{return a+b;} //reduce的操作
                                                        );
            aggregateByKey.foreach(t->{
               System.out.println("RESULT#aggregateByKey:  "+t.toString());
            });


            /**
             * sortByKey
             * 排序按key进行排序
             */

            JavaPairRDD<String,Person> sortByKeyRdd = prdd1
                    .mapToPair(t-> new Tuple2<String, Person>(t.city,t)).sortByKey();
           System.out.println("RESULT#sortByKeyRdd:"+sortByKeyRdd.collect());


            /**
             * join
             * 两个集合的内积，对应数据库里的inner join
             */

            JavaRDD<Person> prdd2 = sc.parallelize(Arrays.asList(
                    new Person("a","chengdu",11)
                    ,new Person("b","chengdu",13)
                    ,new Person("b","chongqing",14)));

            JavaPairRDD<String,Person> rd1 = prdd1.mapToPair(t-> new Tuple2<String, Person>(t.name,t));
            JavaPairRDD<String,Person> rd2 = prdd2.mapToPair(t-> new Tuple2<String, Person>(t.name,t));
            JavaPairRDD<String,Tuple2<Person,Person>> rdJoin = rd1.join(rd2);
            System.out.println("RESULT#join:"+rdJoin.collect());

        } finally {
            sc.close();
        }
    }


    static class Person implements Serializable {
        public String name;
        public String city;
        public Integer age;

        public Person(String name, String city,Integer age){
            this.name = name;
            this.age = age;
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
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
