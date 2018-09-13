package andy.com.db.mybatis.configfile;

import andy.com.db.mybatis.code.UserMapper;
import andy.com.db.mybatis.domains.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.*;

public class MybatisFromConfigFile {

    private static SqlSessionFactory factory = getSqlSesionFactory();
    public static void main(String [] args)
    {
        User user = query(17);
        System.out.println(user.getId());

    }

    public static  User query(int id)
    {
        //从SqlSessionFactory获取一个SqlSession
        SqlSession session = factory.openSession();

        try{
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectUser(id);
            return user;
        }
        finally
        {
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
            Class clazz = User.class;
            // 开头的'/'表示classpath的根目录，这个是表示从classpath的根目录中开始查找资源，如果开头没有'/'，表示从当前这个class所在的包中开始查找
            InputStream is = clazz.getResourceAsStream("mybatis.xml");
            //Reader reader = new InputStreamReader(is);

            Reader reader = new FileReader("/Users/junjun/Documents/项目/java/java/andy.com/target/classes/mybatis/UserMapper.xml");
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