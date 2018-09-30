package andy.com.db.mybatis.mappers;

import andy.com.db.mybatis.domains.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectUser(int id);

    //@Results 跟 resultMap对应
    @Results({
            @Result(column="name",property="name"),
            @Result(column="name",property="tname"),
    })
    @Select("SELECT * FROM user")
    List<User> selectUsers();

    @Insert("insert into user values (NULL,#{name},#{city},#{age})")
    public int insert(User user);


    //使用script
    @Insert("<script>"
            +"insert into user values "
            + "<foreach  item='user' index='index' collection='list' open=' ' separator=',' cloase=' '>" +
            " (NULL,#{user.name},#{user.city}, #{user.age}) "
            +"</foreach>"
            +"</script>")
    public int insertUsers(@Param("list") List<User> users);

    @Delete("delete from user where id=#{id}")
    public int delete(int id);
}
