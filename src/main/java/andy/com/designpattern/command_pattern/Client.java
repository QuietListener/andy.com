package andy.com.designpattern.command_pattern;

public class Client {
    public static void main(String [] args)
    {
        Invoker invoker = new Invoker();

        Receiver receiver = new Receiver();
        Command ca = new CommandA(receiver);
        Command cb = new CommandB(receiver);

        invoker.setCommand(ca);
        invoker.runCommand();

        invoker.setCommand(cb);
        invoker.runCommand();
    }

}
