package andy.com.designpattern.command_pattern;

import java.util.concurrent.TimeUnit;

/**
 * 命令的实际执行者。具体的逻辑
 */
public class Receiver
{
    public void doSomeLogic1()
    {
        System.out.println("Receiver doSomeLogic 1");
        try
        {
            TimeUnit.SECONDS.sleep(1);
        }
        catch(Exception e)
        {

        }
    }

    public void doSomeLogic2()
    {
        System.out.println("Receiver doSomeLogic 2");
        try
        {
            TimeUnit.SECONDS.sleep(2);
        }
        catch(Exception e)
        {

        }
    }
}
