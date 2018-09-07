package andy.com.designpattern.facade;

import andy.com.designpattern.command_pattern.*;

public class Client {
    public static void main(String [] args)
    {
        SecurityFacade sf = new SecurityFacade();
        sf.start();

    }

}
