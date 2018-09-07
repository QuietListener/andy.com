package andy.com.designpattern.command_pattern;

/**
 * 把逻辑封装到命令中
 */
public class CommandA implements Command {

    private Receiver receiver = null;

    public CommandA(Receiver r)
    {
        this.receiver = r;
    }

    public void execute()
    {
        this.receiver.doSomeLogic1();
    }
}



