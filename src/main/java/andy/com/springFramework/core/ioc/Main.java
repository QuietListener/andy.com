package andy.com.springFramework.core.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String args[]) throws  Exception
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/core/spring-core2.xml");
        PostConstructPreDestroy ts = (PostConstructPreDestroy)ctx.getBean("PostConstructPreDestroy");

        ((ClassPathXmlApplicationContext) ctx).close();;
    }


}
