package andy.com.springFramework.core.basic.wire.autowire.pa;

import andy.com.springFramework.core.basic.wire.autowire.Disk;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Primary
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) //每次都创建一个新的
public class TypeADisk implements Disk {

    private int rand = 0;
    public TypeADisk(){
        this.rand = new Random().nextInt(100000);
    }

    @Override
    public void play() {
        System.out.println(this.getClass().getName()+rand);
    }
}
