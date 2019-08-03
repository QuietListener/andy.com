package andy.com.springFramework.dataAccess.codeTransactionImplementation.declarativeTransactionImplementation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String args[]) throws  Exception
    {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.dataAccess/spring-data-access1.xml");

        FooService fooService = (FooService) ctx.getBean("fooService");

        /**
         *     public void insertFoo(Foo foo) {
         *         throw new NoProductInStockException();
         *     }
         */
        fooService.insertFoo (new Foo());

        fooService.updateFoo (new Foo());

        fooService.getFoo("") ;
    }
}
