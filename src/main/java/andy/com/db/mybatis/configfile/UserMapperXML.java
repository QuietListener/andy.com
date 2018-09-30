package andy.com.db.mybatis.configfile;

import andy.com.db.mybatis.domains.User;

import java.util.List;

/**
 * 使用配置文件的方式映射
 */
public interface UserMapperXML {
    User selectUser(int id);
    List<User> selectUsers();
    public int insert(User user);
    public int insert(List<User> users);
    public int delete(int id);
}
