package andy.com.springCloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

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

    @Override
    public String  getFallback(){
        return "hello failure " + name;
    }

}


