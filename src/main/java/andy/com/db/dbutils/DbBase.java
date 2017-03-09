package andy.com.db.dbutils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DbBase {
	
	protected static  DataSource ds = null;
	
	public DbBase() {
		synchronized (this.getClass()) 
		{
			if (ds == null)	
			{	try {
					ds = getDS();
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
			}
		}
	}
  
  static private DataSource getDS() throws PropertyVetoException
  {
	  ComboPooledDataSource ds = new ComboPooledDataSource();
	  ds.setDriverClass("com.mysql.jdbc.Driver");
	  ds.setJdbcUrl("jdbc:mysql://127.0.0.1:3366/wordstv");
	  ds.setUser("root");
	  ds.setPassword("");
	  ds.setInitialPoolSize(50);
	  ds.setMaxPoolSize(100);
	  ds.setMaxIdleTime(10000);
	  return ds;
  }
  
  public Connection getConn()
  {
	 try {
		return  ds.getConnection();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	 return null;
  }
  
}
