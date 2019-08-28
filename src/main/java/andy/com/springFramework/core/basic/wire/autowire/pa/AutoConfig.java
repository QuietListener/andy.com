package andy.com.springFramework.core.basic.wire.autowire.pa;

import andy.com.springFramework.core.basic.wire.autowire.pb.AutoConfig1;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
//扫描带有@Component的类，并将其创建一个Bean
@ComponentScan(basePackages = { "andy.com.springFramework.core.basic.wire.autowire.pa"})
public class AutoConfig {
}
