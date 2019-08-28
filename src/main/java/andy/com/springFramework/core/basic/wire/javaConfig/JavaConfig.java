package andy.com.springFramework.core.basic.wire.javaConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 装配类
 */
@Configuration
public class JavaConfig {

    @Bean(name="disc")
    public Disc getDisc(){
        return new Disc();
    }

    @Bean(name="player1")
    public CdPlayer newCdPlayer(){
        CdPlayer player = new CdPlayer();
        //当调用getDisc时候，spring会拦截返回同一个Disc对象
        player.setDisk(getDisc());
        return player;
    }

    /**
     * 结合@AutoWire在参数上来装配
     * @param disc
     * @return
     */
    @Bean(name="player2")
    public CdPlayer new1CdPlayer(@Autowired Disc disc){
        CdPlayer player = new CdPlayer();
        player.setDisk(disc);
        return player;
    }
}
