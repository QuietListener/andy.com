package andy.com.springFramework.dataAccess.annotationTransactionImplementation;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test1 {

    private static ApplicationContext ctx = null;
    static FooServiceInterface fooService = null;
    static Foo1Service foo1Service = null;

    @BeforeClass
    public static void setUp() {
        ctx = new ClassPathXmlApplicationContext("spring.dataAccess/spring-data-access3.xml");
        fooService = (FooServiceInterface) ctx.getBean("fooService");
        foo1Service = (Foo1Service) ctx.getBean("foo1Service");
    }


    @Test
    public  void testRequired(){
        foo1Service.getFoo1("");
    }

    @Test
    public  void testCallTransactionInNonTransaction(){
        fooService.getFoo(null);
    }
}
