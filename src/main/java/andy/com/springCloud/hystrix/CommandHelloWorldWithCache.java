package andy.com.springCloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * CommandName:一个command的名字
 * CommandGroup:用来组织Command，比如根据团队，库，报告等来自组织,名字是CommandGroupKey
 * Command Thread-Pool:如果不指定ThreadPoolKey，threadpool如果不指定，就是groupKey的名字。
 * 可以指定不同的ThreadPoolKey让command跑在不同的线程池
 */
public class CommandHelloWorldWithCache {

    static public class CommandHelloWorld1 extends HystrixCommand<String> {
        private String name;

        //同一个group但是在不同线程池
        private static Setter setter = Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandHelloWorld1"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool1"));

        public CommandHelloWorld1(String name) {
            super(setter);
            this.name = name;
        }

        @Override
        protected String run() throws Exception {
            return "CommandHelloWorld1 hello " + name;
        }
    }

    static public class CommandHelloWorld2 extends HystrixCommand<String> {
        private String name;

        //同一个group但是在不同线程池
        private static Setter setter = Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandHelloWorld2"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool2"));

        public CommandHelloWorld2(String name) {
            super(setter);
            this.name = name;
        }

        @Override
        protected String run() throws Exception {
            return "CommandHelloWorld2 hello " + name;
        }
    }

    public static void main(String args[]) {
        String name1 = "world";
        String name2 = "junjun";

        //下面两个在不同线程池中执行，起到隔离作用
        String s1 = new CommandHelloWorld1(name1).execute();
        String s2 = new CommandHelloWorld2(name2).execute();

        System.out.println(s1);
        System.out.println(s2);

    }
}


