package andy.com.springCloud.hystrix;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * request collpsing 请求折叠，将多个请求
 * HystrixCollapser<BatchReturnType, ResponseType, RequestArgumentType>
 */
public class CommandCollapserGetValueForKey extends HystrixCollapser<List<String>, String, Integer> {

    private Integer key;

    public CommandCollapserGetValueForKey(Integer key) {
        this.key = key;
    }

    @Override
    public Integer getRequestArgument() {
        return key;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> collection) {
        List<Integer> keys = collection.stream().map(t -> t.getArgument()).collect(Collectors.toList());
        return new BatchCommand(keys);
    }

    /**
     * 将批量执行获取的结果映射到单个请求
     *
     * @param strings
     * @param collection
     */
    @Override
    protected void mapResponseToRequests(List<String> strings, Collection<CollapsedRequest<String, Integer>> collection) {

        int i = 0;
        for (CollapsedRequest<String, Integer> req : collection) {
            req.setResponse(strings.get(i));
            i++;
        }
    }

    private static class BatchCommand extends HystrixCommand<List<String>> {

        private List<Integer> keys;


        public BatchCommand(List<Integer> keys) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")));
            this.keys = keys;
        }

        @Override
        protected List<String> run() throws Exception {
            List<String> response = new ArrayList<>();

            for (Integer key : keys) {
                response.add("ValueForKey:" + key);
            }

            return response;
        }
    }


    public static class Test {

        @org.junit.Test
        public void test1() throws Exception {
            HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
            try {

                Future<String> f1 = new CommandCollapserGetValueForKey(1).queue();
                Future<String> f2 = new CommandCollapserGetValueForKey(2).queue();
                Future<String> f3 = new CommandCollapserGetValueForKey(3).queue();

                //TimeUnit.SECONDS.sleep(5);
                Future<String> f4 = new CommandCollapserGetValueForKey(4).queue();

                String prefix = "ValueForKey:";
                assert f1.get().equals(prefix + 1);
                assert f2.get().equals(prefix + 2);
                assert f3.get().equals(prefix + 3);
                assert f4.get().equals(prefix + 4);

                Collection<HystrixInvokableInfo<?>> infos = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands();
                for (HystrixInvokableInfo info : infos) {
                    System.out.println(info.getCommandKey() + ":" + info.getExecutionEvents());
                }
            } finally {
                ctx.shutdown();
            }
        }
    }

}


