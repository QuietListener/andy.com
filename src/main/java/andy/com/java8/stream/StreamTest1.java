package andy.com.java8.stream;

import com.google.common.collect.Lists;

import javax.annotation.Resource;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 新增的Stream API（java.util.stream）将生成环境的函数式编程引入了Java库中
 */

enum Status {
    OPEN, CLOSED
}

class Task
{
    private Status status;
    private Integer points;
    private Integer value;

    Task(Status status,Integer points,Integer value)
    {
        this.status = status;
        this.points = points;
        this.value = value;
    }

    public Integer getPoints() {
        return points;
    }

    public Status getStatus() {
        return status;
    }
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format( "[%s, %d, %d]", status, points ,value);
    }


}
public class StreamTest1 {

    public static void main(String [] args) throws  Exception
    {
        List<Task> ts = Arrays.asList(
                new Task(Status.CLOSED,1,1),
                new Task(Status.OPEN,2,2),
                new Task(Status.OPEN,2,3),
                new Task(Status.OPEN,2,4),
                new Task(Status.CLOSED,1,5)
        );

        /**
         * 判断一个操作是惰性求值还是及早求值很简单:只需看它的返回值。
         * 如果返回值是 Stream， 那么是惰性求值;
         * 如果返回值是另一个值或为空，那么就是及早求值
         */

        ts.stream()
                .filter(t->{
                    System.out.println("print " + t); //不会打印，因为是惰性求值
                    return t.getStatus() == Status.OPEN;
                }) ;

        Long count =  ts.stream()
                .filter(t->t.getStatus() == Status.OPEN) //这行代码并未做什么实际性的工作，filter 只刻画出了 Stream，但没有产生新的集合。像 filter 这样只描述 Stream，最终不产生新集合的方法叫作惰性求值方法
                .count();//而像 count 这样 最终会从 Stream 产生值的方法叫作及早求值方法。
        System.out.println("opened tasks count:"+count);

        /**
         * 惰性求值结束
         */

        int totalPointsOfOpenTasks = ts.stream()
                .filter(task->task.getStatus() == Status.CLOSED) //在steam上的filter操作会过滤掉所有CLOSED的task
                .mapToInt(Task::getPoints) //第三，mapToInt操作基于每个task实例的Task::getPoints方法将task流转换成Integer集合
                .sum();//通过sum方法计算总和，得出最后的结果。

        System.out.println("totalPointsOfOpenTasks = "+totalPointsOfOpenTasks);


        /**
         * 常用流操作 map,filter,collect
         */

        //1.collect(toList()) 方法由 Stream 里的值生成一个列表，是一个及早求值操作。
        List<String> l1 = Arrays.asList("a","b","c").stream()
            .map(t->t+t) //map
            .filter(t->!t.equals("bb")) //不等于bb的
            .collect(Collectors.toList()); //生成一个列表
        System.out.println(l1 + "\r\n");

        //2.flatMap 多个流合成一个流
        List<String> list1 = Arrays.asList("c","d","e");
        List<String>  list2 =  Arrays.asList("a","b","c");

        List<String> flatMapList = Stream.of(list1,list2)
                .flatMap(t->t.stream())
                .collect(Collectors.toList());

        System.out.println(flatMapList);


        //分类
        Map<Status,List<Task>> map = ts.stream().collect(Collectors.groupingBy(Task::getStatus));
        System.out.println( map );
        Map<Status,List<Task>> map2 = ts.stream().collect(Collectors.toMap(Task::getStatus,
//                    (Task y) -> {System.out.println("y:"+y);  List<Task> ret =  new ArrayList<Task>(); ret.add(y); return ret;},
//                    (exists, newOne) -> {
//                        System.out.println("exists:"+exists+"   newOne");
//                        if(newOne != null)
//                            exists.addAll(newOne);
//                        return exists;
//                }
                y -> Lists.newArrayList(Arrays.asList(y)),
                (p,q) -> {
                    p.addAll(q);
                    return p;
                }
                ));
        System.out.println("map2： "+map2);

        /**
         * (a,b)->{return a.getValue() > b.getValue() ? a : b;}) 冲突函数 取大的
         */
        Map<Status,Task> map3 = ts.stream().collect(Collectors.toMap(Task::getStatus, t->t,
                (a,b)->{return a.getValue() > b.getValue() ? a : b;}));//解决冲突

        System.out.println("map3： "+map3);


        //分隔
        String tmp1 = Arrays.asList(1,2,3,4).stream().map(t->t+"").collect(Collectors.joining("|"));
        System.out.println(tmp1);


        List<String> ret = Arrays.asList(new String []{"1","2"});
        System.out.println(ret.get(0));

    }
}

