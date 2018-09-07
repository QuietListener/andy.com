package andy.com.designpattern.command_pattern;

public class CommandB implements Command {

    private Receiver receiver = null;

    public CommandB(Receiver r)
    {
        this.receiver = r;
    }

    public void execute()
    {
        this.receiver.doSomeLogic2();
    }
}



