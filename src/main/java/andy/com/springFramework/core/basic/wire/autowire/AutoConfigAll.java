package andy.com.springFramework.core.basic.wire.autowire;

import andy.com.springFramework.core.basic.wire.autowire.pa.AutoConfig;
import andy.com.springFramework.core.basic.wire.autowire.pb.AutoConfig1;
import andy.com.springFramework.core.basic.wire.autowire.pc.AutoConfig2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AutoConfig.class, AutoConfig1.class, AutoConfig2.class})
public class AutoConfigAll {
}
