package andy.com.springFramework.dataAccess.annotationTransactionImplementation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String args[]) throws  Exception
    {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.dataAccess/spring-data-access3.xml");

        FooService fooService = (FooService) ctx.getBean("fooService");


        try {
            fooService.updateFoo(new Foo());

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        try {
            fooService.getFoo("");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
