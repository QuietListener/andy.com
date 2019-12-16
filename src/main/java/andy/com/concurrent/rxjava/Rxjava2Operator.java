package andy.com.concurrent.rxjava;

import io.reactivex.Observable;
import org.junit.Test;


public class Rxjava2Operator {

    static public void printWithThread(Object obj) {
        System.out.println(Thread.currentThread().getName() + ":" + obj.toString());

    }

    @Test
    public void test1() {
        Integer[] names = new Integer[]{1, 2, 3, 4, 5, 6, 6, 7};
        Observable.fromArray(names)
                .skip(2)  //跳过前两个
                .take(5)  //去前五个
                .map(s -> "onNext:" + (s + 10)) //映射
                .subscribe(r -> printWithThread(r));//消费


    }


}
