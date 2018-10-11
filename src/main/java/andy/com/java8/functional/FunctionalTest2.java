package andy.com.java8.functional;

public class FunctionalTest2
{
}


/**
 * 函数接口指的是只有一个函数的接口，这样的接口可以隐式转换为Lambda表达式。
 * java.lang.Runnable和java.util.concurrent.Callable是函数式接口的最佳例子,
 * 在实践中，函数式接口非常脆弱：只要某个开发者在该接口中添加一个函数，则该接口就不再是函数式接口进而导致编译失败。
 * 为了克服这种代码层面的脆弱性，并显式说明某个接口是函数式接口，Java 8 提供了一个特殊的注解@FunctionalInterface
 */

/**
 * 不过有一点需要注意，默认方法和静态方法不会破坏函数式接口的定义，因此如下的代码是合法的。
 */
