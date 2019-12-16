package andy.com.concurrent.rxjava;

import io.reactivex.Observable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Rxjava3 {

    static public void printWithThread(Object obj) {
        System.out.println(Thread.currentThread().getName() + ":" + obj.toString());

    }

    @Test
    public void test1() {
        Observable<Integer> observable = Observable.create(observableEmitter -> {
            int i = 0;
            while(true){
                observableEmitter.onNext(i);
                i++;
                TimeUnit.SECONDS.sleep(2);
            }
        });


       List<Integer> list = new ArrayList<>();
       observable.subscribe(i->{
            list.add(i);
           printWithThread(list.stream().map(String::valueOf).collect(Collectors.joining(",")));
       });
    }


}
