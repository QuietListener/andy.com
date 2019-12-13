package andy.com.springCloud.hystrix;

import org.junit.Test;

import java.util.concurrent.Future;

public class TestCommandHelloWorldFallback {

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
