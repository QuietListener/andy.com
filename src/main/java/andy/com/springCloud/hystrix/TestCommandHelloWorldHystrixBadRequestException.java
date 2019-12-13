package andy.com.springCloud.hystrix;

import org.junit.Test;

import java.util.concurrent.Future;

public class TestCommandHelloWorldHystrixBadRequestException {


    @Test
    public void testAsyncHelloWord() throws Exception {
        String name = "World";
        Future<String> s = new CommandHelloWorldHystrixBadRequestException(name).queue();
        System.out.println(s.get());
        assert s.get().equals("hello failure " + name);
    }

}
