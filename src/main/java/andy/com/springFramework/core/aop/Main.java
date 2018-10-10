package andy.com.springFramework.core.aop;

import andy.com.springFramework.dataAccess.annotationTransactionImplementation.Foo;
import andy.com.springFramework.dataAccess.annotationTransactionImplementation.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String args[]) throws  Exception
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/core/spring-core1.xml");

        TestService ts = (TestService)ctx.getBean("TestService");
        ts.doJob("job1"," 挖沙沙");

        ts.doOtherJob1("job2");

        ts.doOtherJob2("job23","dd");
    }


}
