package andy.com.springFramework.core.basic.wire.autowire.pb;

import andy.com.springFramework.core.basic.wire.autowire.Disk;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

//􏲨􏱙􏰞􏰪􏰇􏰞􏰊􏰍􏰊􏰐􏲞􏲟􏰮􏱒􏰧􏲠􏲡􏱂􏲞􏲟􏱡􏲒􏵢􏶩􏰒􏴔􏰕􏲷􏸇􏶩􏰅􏷈 􏻄􏶦􏰆􏰇􏰈􏰉􏰊􏰋􏱔􏰕􏱒􏰧􏶩􏴵􏴶􏱘􏰍􏰚􏰊􏲨􏱙􏰞􏰪􏰇􏰞􏰊􏰍􏰊􏰐􏲞􏲟􏰮􏱒􏰧􏲠􏲡􏱂􏲞􏲟􏱡􏲒􏵢􏶩􏰒􏴔􏰕􏲷􏸇􏶩􏰅􏷈这个注解表明该类会作为组件，Spring会为这个类创建bean􏻄􏶦􏰆􏰇􏰈􏰉􏰊􏰋􏱔􏰕􏱒􏰧􏶩􏴵􏴶􏱘􏰍􏰚􏰊
@Component(value = "BDisk")
public class TypeBDisk implements Disk {
    private int rand = 0;
    public TypeBDisk(){
        this.rand = new Random().nextInt(100000);
    }

    @Override
    public void play() {
        System.out.println(this.getClass().getName()+this.rand);
    }
}
