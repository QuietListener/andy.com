package andy.com.java8.functional;

import java.util.Arrays;
import java.util.List;

public class FunctionalTest1 {

	public static void main(String[] args)
	{
		/**
		 * 可以将函数，或者代码块 当成一个参数传进去方法
		 */

		System.out.println("\r\n=================");
		Arrays.asList( "a", "b", "d" ).forEach( e -> System.out.print( e ) );	//e的类型系统来推断
		System.out.println();

		Arrays.asList( "a", "b", "d" ).forEach( ( String e ) -> { //指定e的类型
			System.out.print( e );
		});
		System.out.println();

		//Lambda表达式可以引用类成员和局部变量（会将这些变量隐式得转换成final的
		String seperator = ",";
		Arrays.asList( "a", "b", "d" ).forEach( ( String e ) -> { //指定e的类型
			System.out.print( seperator+e );
		});
		System.out.println();

		//Lambda表达式有返回值，返回值的类型也由编译器推理得出
		List<String> ret = Arrays.asList( "a", "e", "d" );
		ret.sort( (e1, e2 ) -> {
			int result = e1.compareTo( e2 );
			return result;
		} );
		System.out.println(ret);

		System.out.println("\r\n=================");





	}
}



