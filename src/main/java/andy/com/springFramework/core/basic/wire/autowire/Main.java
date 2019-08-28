package andy.com.springFramework.core.basic.wire.autowire;

import andy.com.springFramework.core.basic.wire.autowire.pa.AutoConfig;
import andy.com.springFramework.core.basic.wire.autowire.pa.TypeADisk;
import andy.com.springFramework.core.basic.wire.autowire.pb.TypeBDisk;
import andy.com.springFramework.core.basic.wire.autowire.pc.Player;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    @Test
    public void tets(){
        ApplicationContext context = new AnnotationConfigApplicationContext(AutoConfigAll.class);

        TypeADisk aDisk = context.getBean(TypeADisk.class);
        TypeBDisk bDisk = context.getBean(TypeBDisk.class);

        aDisk.play();
        bDisk.play();

        System.out.println("####");
        Player player = context.getBean(Player.class);
        player.playb();


        //两次应该不一样
        player = context.getBean(Player.class);
        player.playa();
        player = context.getBean(Player.class);
        player.playa();


    }
}
