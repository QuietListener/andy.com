package andy.com.db.mybatis.configfile;

import andy.com.db.mybatis.configfile.UserMapperXML;
import andy.com.db.mybatis.domains.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 使用配置文件的方式
 */
public class MybatisFromConfigFile {

    private static SqlSessionFactory factory = getSqlSesionFactory();
    public static void main(String [] args)
    {
        User user = query(29);
        System.out.println(user.getId());

        //testCache1();


    }


    public static  User query(int id)
    {
        //从SqlSessionFactory获取一个SqlSession
        SqlSession session = factory.openSession();

        try{
            UserMapperXML mapper = session.getMapper(UserMapperXML.class);
            User user = mapper.selectUser(id);
            return user;
        }
        finally
        {
            session.close();
        }
    }

    public static void testCache1()
    {
        //从SqlSessionFactory获取一个SqlSession
        SqlSession session = factory.openSession();
        try {
            UserMapperXML mapper = session.getMapper(UserMapperXML.class);
            List<User> users = mapper.selectUsers();

            System.out.println("当前个数:"+users.size());
            System.out.println("睡20秒，手动修改数据库");
            TimeUnit.SECONDS.sleep(20);

            List<User> users1 = mapper.selectUsers();
            System.out.println("当前个数:"+users1.size());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }


    /**
     * SqlSessionFactory是一个全局的工厂，需要提供DataSource和TransactionFactory这两个东西
     * 从配置文件中取
     * @return
     */
    static final SqlSessionFactory getSqlSesionFactory()
    {
        try {

            // 开头的'/'表示classpath的根目录，这个是表示从classpath的根目录中开始查找资源，如果开头没有'/'，表示从当前这个class所在的包中开始查找

            InputStream is = Resources.getResourceAsStream("mybatis/mybatis.xml");
            //Reader reader = new InputStreamReader(is);

            //Reader reader = new FileReader("/Users/junjun/Documents/项目/java/java/andy.com/src/main/resources/mybatis/mybatis.xml");

            Reader reader = new InputStreamReader(is);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            return factory;
        }
        catch(Exception e)
        {
            System.err.println(e);
        }

        return null;
    }

}
