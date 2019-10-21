package andy.com.java8;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hello {

	public static void main(String[] args) {
		System.out.println("----s");
		test("a","b");
	}

	public static void test(String ... keys){
		String str = Stream.of(keys).collect(Collectors.joining(","));
		System.out.println(str);
	}
}
