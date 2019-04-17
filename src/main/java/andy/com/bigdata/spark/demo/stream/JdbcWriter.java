package andy.com.bigdata.spark.demo.stream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JdbcWriter extends org.apache.spark.sql.ForeachWriter {

    private static String driver = "com.mysql.jdbc.Driver";
    private Connection conn = null;

    private String jdbcUrl;
    private String user;
    private String password;

    public JdbcWriter(String jdbcUrl, String user, String password) {

        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean open(long partitionId, long version) {
        try {
            System.out.println("partitionId = " + partitionId + ", version = " + version);
            conn = DriverManager.getConnection(jdbcUrl, user, password);
            conn.setAutoCommit(true);
            System.out.println("###### open conn "+conn.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void process(Object o) {
        System.out.println("######process:"+o.toString());
        try{
            PreparedStatement state = conn.prepareStatement("insert into test2 values (NULL, ?)");
            state.setString(1,o.toString());
            state.executeUpdate();
            conn.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void close(Throwable throwable) {
        if (throwable != null)
            throwable.printStackTrace();

        try {
            if (conn != null) {
                conn.close();
            }
            System.out.println("###### closed conn");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
