package andy.com.java8.interfacetest;


import java.util.function.Supplier;

interface TestInterface
{
    default String notNeedImplement()
    {
        return "default implementation";
    }
}

/**
 *  默认方法和静态方法。默认方法使得接口有点类似traits，不过要实现的目标不一样。默认方法使得开发者可以在
 *  不破坏二进制兼容性的前提下，往现存接口中添加新的方法，即不强制那些实现了该接口的类也同时实现这个新加的方法
 *  默认方法允许在不打破现有继承体系的基础上改进接口。
 *  该特性在官方库中的应用是：
 *  给java.util.Collection接口添加新方法，如stream()、parallelStream()、forEach()和removeIf()等等。
 */
class TestInterfaceImpl1 implements TestInterface
{

}

class TestInterfaceImpl2 implements TestInterface
{

    @Override
    public String notNeedImplement()
    {
        return "Overrided implementation";
    }
}


/**
 * 接口中可以定义静态方法
 */
interface TestInterfaceFactory {
    // Interfaces now allow static methods
    static TestInterface create( Supplier< TestInterface > supplier ) {
        return supplier.get();
    }
}
public class Test1 {

    public static void main(String [] args)
    {
        TestInterface t1 = new TestInterfaceImpl1();
        TestInterface t2 = new TestInterfaceImpl2();

        System.out.println(t1.notNeedImplement());
        System.out.println(t2.notNeedImplement());


        TestInterface t11 = TestInterfaceFactory.create(TestInterfaceImpl1::new);
        TestInterface t21 = TestInterfaceFactory.create(TestInterfaceImpl2::new);


        System.out.println(t11.notNeedImplement());
        System.out.println(t21.notNeedImplement());

    }

}
