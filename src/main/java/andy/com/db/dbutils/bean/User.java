package andy.com.db.dbutils.bean;

public class User {
	public int id;
	public String name;
	public String password;
	
	@Override
	public String toString() {
		return id+":"+name+":"+password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
