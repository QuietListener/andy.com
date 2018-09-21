package andy.com.db.mybatis.code;

import andy.com.db.mybatis.domains.User;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.Date;

public class MybatisFromCode
{

    private static SqlSessionFactory factory = getSqlSesionFactory();
    public static void main(String [] args)
    {
        User user = query(29);
        System.out.println(user.getId());

    }

    public static  User query(int id)
    {
        //从SqlSessionFactory获取一个SqlSession
        SqlSession session = factory.openSession();

        try{

            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectUser(id);

            user.setName(" abc " + new Date());
            mapper.insert(user);

            //int a = 1/0;

            user.setName("cde"+new Date());

            mapper.insert(user);

            session.commit();
            return user;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            session.rollback();
        }
        finally {
            session.close();
        }

        return null;
    }

    static final DataSource getDs()
    {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass("com.mysql.jdbc.Driver");
            ds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/javatest");
            ds.setUser("root");
            ds.setPassword("");
            ds.setInitialPoolSize(50);
            ds.setMaxPoolSize(100);
            ds.setMaxIdleTime(10000);
            ds.setAutoCommitOnClose(false);
            return ds;
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
        return null;
    }

    /**
     * SqlSessionFactory是一个全局的工厂，需要提供DataSource和TransactionFactory这两个东西
     * @return
     */
    static final SqlSessionFactory getSqlSesionFactory()
    {
        DataSource dataSource = getDs();//数据源用来获取数据库连接
        TransactionFactory transactionFactory = new JdbcTransactionFactory(); //TransactionFactory 用作事务

        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return  sqlSessionFactory;

    }
}
