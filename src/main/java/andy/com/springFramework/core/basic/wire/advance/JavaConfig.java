package andy.com.springFramework.core.basic.wire.advance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class JavaConfig {

    @Bean
    @Profile("dev")
    public ProjectConf devConfig() {
        return new ProjectConf("dev");
    }

    @Bean
    @Profile("prod")
    public ProjectConf prodConfig() {
        return new ProjectConf("prod");
    }
}
