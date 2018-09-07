package andy.com.db.mybatis.code;

import andy.com.db.mybatis.domains.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectUser(int id);


    @Insert("insert into user values (NULL,#{name},#{city},#{age})")
    public int insert(User user);

    @Delete("delete from user where id=#{id}")
    public int delete(int id);
}
