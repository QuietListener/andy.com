package andy.com.db.dbutils.bean;

import java.util.Date;

public class Note {
	
    public int id;
    public int user_id;
    public String msg;
    public Date create_date;
    public User user;
    public boolean active;
  
    @Override
    public String toString() {
    	return id+":"+user_id+":"+msg+":"+create_date+":"+active + "\n user:"+user;
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	} 
	  
	  
}
