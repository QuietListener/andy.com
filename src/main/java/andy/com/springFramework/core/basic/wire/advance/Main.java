package andy.com.springFramework.core.basic.wire.advance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        ProjectConf projectConf = context.getBean(ProjectConf.class);
        System.out.println(projectConf.getName());
    }
}
