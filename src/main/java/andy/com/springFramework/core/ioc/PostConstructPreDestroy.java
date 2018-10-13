package andy.com.springFramework.core.ioc;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 通过@PostConstruct 和 @PreDestroy 方法 实现初始化后和销毁bean之前进行的操作
 */


@Component("PostConstructPreDestroy")
public class PostConstructPreDestroy {

    public PostConstructPreDestroy()
    {
        System.out.println("init");
    }

    @PostConstruct
    public void testPostConstruct()
    {
        System.out.println("testPostConstruct");
    }

    @PreDestroy
    public void  testPreDestroy()
    {
        System.out.println("testPreDestroy");
    }


}
