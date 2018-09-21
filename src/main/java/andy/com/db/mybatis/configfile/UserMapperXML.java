package andy.com.db.mybatis.configfile;

import andy.com.db.mybatis.domains.User;

/**
 * 使用配置文件的方式映射
 */
public interface UserMapperXML {
    User selectUser(int id);
    public int insert(User user);
    public int delete(int id);
}
