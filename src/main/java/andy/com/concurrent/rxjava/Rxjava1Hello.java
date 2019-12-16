package andy.com.concurrent.rxjava;

import io.reactivex.Observable;


public class Rxjava1Hello {

    /**
     * @param args
     */
    public static void main(String[] args) {
        hello("junjun", "xiaolan");
    }

    public static void hello(String... names) {
        Observable.fromArray(names).subscribe(s -> {
            System.out.println(Thread.currentThread().getName() + ":" + s);
        });
    }

}
