package andy.com.springCloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Test;

import java.util.concurrent.Future;

/**
 * 使用 fallback实现降级
 * 在执行 run的时候，除了抛出HystrixBadRequestException ，其他异常都会被看做是failure并触发getFallback方法，
 * 如果想要上层知道出了了异常，而不计入failure，可以将异常包装成HystrixBadRequestException再抛出。
 */
public class CommandHelloWorldWithFallback extends HystrixCommand<String> {

    private String name;

    public CommandHelloWorldWithFallback(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        throw new RuntimeException("failed ");
    }

    /**
     * 如果抛出异常会默认返回这个数据
     * @return
     */
    @Override
    public String  getFallback(){
        return "hello failure " + name;
    }


    static public class TestCommandHelloWorldFallback {

        @Test
        public void testSyncHelloWord() {
            String name = "World";
            String s = new CommandHelloWorldWithFallback(name).execute();
            System.out.println(s);
            assert s.equals("hello failure " + name);
        }

        @Test
        public void testAsyncHelloWord() throws Exception {
            String name = "World";
            Future<String> s = new CommandHelloWorldWithFallback(name).queue();
            System.out.println(s.get());
            assert s.get().equals("hello failure " + name);
        }



    }


}


