package andy.com.concurrent.rxjava;

import rx.Observable;
import rx.functions.Action1;


public class Rxjava1Hello {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		hello("junjun","xiaolan");

	}

	public static void hello(String... names) {
	    Observable.from(names).subscribe(new Action1<String>() {

	        @Override
	        public void call(String s) {
	            System.out.println("Hello " + s + "!");
	        }

	    });
	}

}
