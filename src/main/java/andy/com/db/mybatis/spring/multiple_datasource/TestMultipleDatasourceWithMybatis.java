package andy.com.db.mybatis.spring.multiple_datasource;

import andy.com.db.mybatis.mappers.UserMapper;
import andy.com.db.mybatis.domains.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class TestMultipleDatasourceWithMybatis {

    private final static String createTableSql = "CREATE TABLE IF NOT EXISTS `user`  ("+
              "`id` int(11) NOT NULL AUTO_INCREMENT,"+
              "`name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
              "`city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,"+
              "`age` int(11) DEFAULT NULL,"+
              "  PRIMARY KEY (`id`)"+
                ") ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";

    static ApplicationContext context = new ClassPathXmlApplicationContext("mybatis/spring/spring-multiple-datasources-mybatis.xml");

    public static void main(String [] args) throws Exception {
        AbstractRoutingDataSource ds = (AbstractRoutingDataSource) context.getBean("mysqlDynamicDataSource");

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
            testUser(i);
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

    public static void testUser(int i)
    {

        SqlSessionFactory ssf = (SqlSessionFactory)context.getBean("sqlSessionFactory");
        SqlSession session = ssf.openSession(true); //false 表示 autocommit为false
        try{

            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = new User();
            user.setName(i+": abc " + new Date());
            mapper.insert(user);

            int a = 1/0;
            user.setName(i+":cde"+new Date());
            mapper.insert(user);

            session.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            session.rollback();
        }
        finally {
            session.close();
        }

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
