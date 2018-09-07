package andy.com.designpattern.command_pattern;

import java.util.Date;

/**
 * 命令的请求者
 */
public class Invoker
{
    private Command command;

    public void setCommand(Command command)
    {
        this.command = command;
    }

    public void runCommand()
    {
        //方便加控制
        Date dstart = new Date();

        this.command.execute();

        Date dend = new Date();

        long comsumed = dend.getTime() - dstart.getTime();
        System.out.println("command used " +  comsumed +"million seconds");


    }
}

