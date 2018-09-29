package andy.com.db.mybatis.spring.multiple_datasource;

import andy.com.db.mybatis.domains.User;
import andy.com.db.mybatis.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("userService")
public class UserService
{
    @Autowired
    UserMapper um=null;

    @Transactional
    public void testUser(int i)
    {
        User user = new User();
        user.setName(i+": abc " + new Date());
        um.insert(user);

        //int a = 1/0;
        user.setName(i+":cde"+new Date());
        um.insert(user);
    }
}