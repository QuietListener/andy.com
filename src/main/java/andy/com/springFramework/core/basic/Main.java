package andy.com.springFramework.core.basic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String args[]) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/core/spring-core-lifecircle.xml");
        Person person = ctx.getBean("person", Person.class);
        System.out.println(person);
        person.setAddress("简阳");

        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
