package andy.com.springFramework.dataAccess.declarativeTransactionImplementation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.sql.Connection;

public class Main {

    public static void main(String args[]) throws  Exception
    {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.dataAccess/spring-data-access2.xml");

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
