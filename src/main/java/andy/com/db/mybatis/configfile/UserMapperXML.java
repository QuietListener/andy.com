package andy.com.db.mybatis.configfile;

import andy.com.db.mybatis.domains.User;

public interface UserMapperXML {
    User selectUser(int id);
    public int insert(User user);
    public int delete(int id);
}
