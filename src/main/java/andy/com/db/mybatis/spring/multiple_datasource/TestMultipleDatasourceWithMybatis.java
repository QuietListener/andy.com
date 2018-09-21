package andy.com.db.mybatis.spring.multiple_datasource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestMultipleDatasourceWithMybatis {

    private final static String createTableSql = "CREATE TABLE `test2` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `name` varchar(255) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;";

    public static void main(String [] args) throws Exception
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("mybatis/spring/spring-multiple-datasources-mybatis.xml");
        AbstractRoutingDataSource ds = (AbstractRoutingDataSource)context.getBean("mysqlDynamicDataSource");
        DataSourceHolder.setDataSource("db0");
        createTable(ds);
        DataSourceHolder.clearDataSource();

        DataSourceHolder.setDataSource("db1");
        createTable(ds);
        DataSourceHolder.clearDataSource();

        DataSourceHolder.setDataSource("db2");
        createTable(ds);
        DataSourceHolder.clearDataSource();
    }


    public static void createTable(DataSource ds) throws Exception
    {
        Connection connection = ds.getConnection();
        PreparedStatement ps = null;
        try
        {
            ps = connection.prepareStatement(createTableSql);
            ps.executeUpdate();
        }
        finally
        {
            ps.close();
            connection.close();
        }

    }


}
