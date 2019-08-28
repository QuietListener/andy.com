package andy.com.springFramework.core.basic.wire.autowire.pc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
//扫描带有@Component的类，并将其创建一个Bean
@ComponentScan(basePackages = { "andy.com.springFramework.core.basic.wire.autowire.pc"})
public class AutoConfig2 {
}
