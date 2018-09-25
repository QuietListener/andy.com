package andy.com.db.mybatis.spring.multiple_datasource;

public class DataSourceHolder {
    /**
     * 数据源操作
     */

    //线程本地环境
    //存储的Datasource的名字 db0，db1 db2(在spring-multiple-datasources.xml中配置的)
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

    //设置数据源
    public static void setDataSource(String dbName) {
        dataSources.set(dbName);
    }
    //获取数据源
    public static String getDataSourceName() {
        return (String) dataSources.get();
    }
    //清除数据源
    public static void clearDataSource() {
        dataSources.remove();
    }

}
