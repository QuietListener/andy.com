package andy.com.books.javaPersistenceWithMybatis3.common;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

public class MybatisSqlSessionFactory {

    private static String configPath_ =null;
    private static ConcurrentHashMap<String,SqlSessionFactory> sqlSessionMap = new ConcurrentHashMap<>();

    public static void init(String configPath){
        configPath_ = configPath;
    }
    public static SqlSessionFactory getSqlSessionFactory(String envId) {
        SqlSessionFactory factory  = sqlSessionMap.get(envId);

        if (factory == null) {
            synchronized (MybatisSqlSessionFactory.class) {
                if (factory == null) {
                    InputStream inputStream;
                    try {
                        inputStream = Resources.getResourceAsStream(configPath_);
                        factory = new SqlSessionFactoryBuilder().build(inputStream,envId);
                        sqlSessionMap.put(envId,factory);
                    } catch (Exception e) {
                        throw new RuntimeException(e.getCause());
                    }
                }
            }
        }
        return factory;
    }

    public static SqlSession openSession(String envId) {
        return getSqlSessionFactory(envId).openSession();
    }

}
