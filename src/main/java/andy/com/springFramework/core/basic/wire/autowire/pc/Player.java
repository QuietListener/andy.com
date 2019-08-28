package andy.com.springFramework.core.basic.wire.autowire.pc;

import andy.com.springFramework.core.basic.wire.autowire.Disk;
import andy.com.springFramework.core.basic.wire.autowire.pa.TypeADisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class Player {

    @Autowired(required = true)
    @Qualifier("BDisk")
    private Disk disk;

    @Autowired
    private TypeADisk adisk;

    public void playb(){
        disk.play();
    }

    public void playa(){
        getAdisk().play();
    }

    @Lookup
    public TypeADisk getAdisk(){
        return null;
    }

}
