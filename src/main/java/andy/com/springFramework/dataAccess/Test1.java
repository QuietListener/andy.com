package andy.com.springFramework.dataAccess;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;

public class Test1 {

    static ApplicationContext context = new ClassPathXmlApplicationContext("spring/dataAccess/spring-data-access1.xml");

    public static void main(String args[]) throws  Exception
    {

       DataSource  ds =  (DataSource)context.getBean("dataSource0");
       Connection conn = DataSourceUtils.getConnection(ds);


    }
}
