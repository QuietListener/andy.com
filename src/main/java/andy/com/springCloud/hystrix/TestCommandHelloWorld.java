package andy.com.springCloud.hystrix;

import org.junit.Test;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.Future;

public class TestCommandHelloWorld {

    @Test
    public void testSyncHelloWord() {
        String name = "World";
        String s = new CommandHelloWorld(name).execute();
        assert s.equals("hello " + name);
    }

    @Test
    public void testAsyncHelloWord() throws Exception {
        String name = "World";
        Future<String> s = new CommandHelloWorld(name).queue();
        assert s.get().equals("hello " + name);
    }

    @Test
    public void testObservable() throws Exception {
        String name1 = "World";
        String name2 = "junjun";


        //阻塞调用
        Observable<String> ho = new CommandHelloWorld(name1).observe();
        Observable<String> co = new CommandHelloWorld(name2).observe();
        assert ho.toBlocking().single().equals("hello " + name1);
        assert co.toBlocking().single().equals("hello " + name2);


        //非阻塞调用
        ho.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext:" + s);
            }
        });


        co.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext:" + s);
            }
        });


        //java 8
        co.subscribe((s) -> {
            System.out.println("java 8 onNext:" + s);
        });
        co.subscribe((s) -> {
                    System.out.println("java 8 onNext:" + s);
                },
                (exception) -> {
                    exception.printStackTrace();
                },
                () -> {
                    System.out.println("java 8 conpleted");
                });
    }

}
