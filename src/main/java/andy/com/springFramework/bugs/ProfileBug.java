package andy.com.springFramework.bugs;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileBug {

    @Bean("name")
    @Profile("test")
    public String nameTest() {
        return "name_test";
    }

    @Bean("name")
    @Profile("prod")
    public String nameProd() {
        return "name_prod";
    }

    //默认数据
    @Bean("name")
    public String name() {
        return "name";
    }



    public String testProfile(String profile) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ProfileBug.class);
        context.getEnvironment().setActiveProfiles(profile);
        context.refresh();

        String name = (String) context.getBean("name");
        System.out.println(String.format("%s:%s", profile, name));
        return name;
    }

    @Test
    public void test1() {
        assert "name_test".equals(testProfile("test")); // passed return "name_test"
        assert "name".equals(testProfile("default")); // passed return default value "name"
        assert "name_prod".equals(testProfile("prod")); //passed return "name_prod"
    }
}
