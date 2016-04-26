package andy.com.db.dbutils;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import andy.com.db.dbutils.bean.Note;

public class Test1 extends DbBase{

	public static void main(String[] args) {
		Test1 db = new Test1();
		db.testSelect();
	}
	
	public void testSelect()
	{
		QueryRunner run = new QueryRunner(ds);
		ResultSetHandler<List<Note>> rsh = new BeanListHandler<Note>(Note.class);
		
		try {
			List<Note> notes = run.query("select * from note where id > ?", 2, rsh);
			System.out.println(notes);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
