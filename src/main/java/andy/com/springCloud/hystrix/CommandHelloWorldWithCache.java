package andy.com.springCloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * Hystrix 支持 cache, 需要实现 getCacheKey方法
 * cache 依赖于 HystrixRequestContext
 * Typically this context will be initialized and shut down via a ServletFilter that wraps a user request
 * or some other lifecycle hook.
 */
public class CommandHelloWorldWithCache extends HystrixCommand<Boolean> {

    private int value;

    public CommandHelloWorldWithCache(int value) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandHelloWorld1"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool1")));
        this.value = value;
    }

    @Override
    protected Boolean run() throws Exception {
        return value == 0 || value % 2 == 0;
    }

    /**
     * cache key
     * @return
     */
    @Override
    public String getCacheKey(){
        return String.valueOf(value);
    }


    public static class Test {

        @org.junit.Test
        public void test1() {
            HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
            try {

                CommandHelloWorldWithCache cmd1 = new CommandHelloWorldWithCache(2);
                CommandHelloWorldWithCache cmd2 = new CommandHelloWorldWithCache(2);

                assert cmd1.execute() == true;
                assert cmd1.isResponseFromCache() == false; //不是从cache中取的

                assert cmd2.execute() == true;
                assert cmd2.isResponseFromCache() == true; //是从cache中取的

            } finally {
                ctx.shutdown();
            }
        }
    }
}


