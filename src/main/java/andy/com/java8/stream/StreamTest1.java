package andy.com.java8.stream;

import javax.annotation.Resource;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    Task(Status status,Integer points)
    {
        this.status = status;
        this.points = points;
    }

    public Integer getPoints() {
        return points;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format( "[%s, %d]", status, points );
    }


}
public class StreamTest1 {

    public static void main(String [] args) throws  Exception
    {
        List<Task> ts = Arrays.asList(
                new Task(Status.CLOSED,1),
                new Task(Status.OPEN,2),
                new Task(Status.OPEN,2),
                new Task(Status.OPEN,2),
                new Task(Status.CLOSED,1)
        );

        int totalPointsOfOpenTasks = ts.stream()
                .filter(task->task.getStatus() == Status.CLOSED) //在steam上的filter操作会过滤掉所有CLOSED的task
                .mapToInt(Task::getPoints) //第三，mapToInt操作基于每个task实例的Task::getPoints方法将task流转换成Integer集合
                .sum();//通过sum方法计算总和，得出最后的结果。

        System.out.println("totalPointsOfOpenTasks = "+totalPointsOfOpenTasks);



        //分类
        Map<Status,List<Task>> map = ts.stream().collect(Collectors.groupingBy(Task::getStatus));
        System.out.println( map );

    }
}

