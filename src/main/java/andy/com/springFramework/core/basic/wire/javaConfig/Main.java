package andy.com.springFramework.core.basic.wire.javaConfig;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    @Test
    public void tets(){
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        Disc d1 = context.getBean(Disc.class);
        Disc d2 = context.getBean(Disc.class);

        assert d1 == d2;

        CdPlayer player1 = (CdPlayer)context.getBean("player1");
        CdPlayer player2 = (CdPlayer)context.getBean("player2");

        assert player1.getDisk() == player2.getDisk();
        assert d1 == player1.getDisk();
    }
}
