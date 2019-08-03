package andy.com.springFramework.dataAccess.annotationTransactionImplementation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String [] args){
        ApplicationContext  ctx = new ClassPathXmlApplicationContext("spring.dataAccess/spring-data-access3.xml");
        FooService  fooService = (FooService) ctx.getBean("fooService");
        Foo1Service foo1Service = (Foo1Service) ctx.getBean("foo1Service");
        foo1Service.getFoo1("");
    }
}
