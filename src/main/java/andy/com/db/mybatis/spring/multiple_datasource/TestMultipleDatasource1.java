package andy.com.db.mybatis.spring.multiple_datasource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestMultipleDatasource1 {

    private final static String createTableSql = "CREATE TABLE IF NOT EXISTS `test2` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `name` varchar(255) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;";

    private final static String insertSql = "insert into test2 values (NULL,?)";


    static ApplicationContext context = new ClassPathXmlApplicationContext("mybatis/spring/spring-multiple-datasources.xml");

    public static void main(String [] args) throws Exception
    {

        for(int i = 0 ;i <3; i++)
        {
            String dbname = "db"+(i%3);
            DataSourceHolder.setDataSource(dbname);
            createTable();
            DataSourceHolder.clearDataSource();
        }


        for(int i = 0 ;i <100; i++)
        {
            String dbname = "db"+(i%3);
            DataSourceHolder.setDataSource(dbname);
            insert(i);
            DataSourceHolder.clearDataSource();
        }

    }


    public static void createTable() throws Exception
    {
        DataSource ds = (AbstractRoutingDataSource)context.getBean("mysqlDynamicDataSource");
        Connection connection = ds.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(createTableSql);
            ps.executeUpdate();
        }finally {
            ps.close();
            connection.close();
        }

    }

    public static void insert(int i) throws Exception
    {
        DataSource ds = (AbstractRoutingDataSource)context.getBean("mysqlDynamicDataSource");
        Connection connection = ds.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSql);
            ps.setInt(1,i);
            ps.executeUpdate();
        }finally {
            ps.close();
            connection.close();
        }

    }


}
