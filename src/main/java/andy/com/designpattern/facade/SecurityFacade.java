package andy.com.designpattern.facade;

public class SecurityFacade {

    private Camera c1,c2;
    private Alarm alarm;
    private Light l1,l2;

    public SecurityFacade()
    {
        c1 = new Camera();
        c2 = new Camera();
        alarm = new Alarm();
        l1 = new Light();
        l2 = new Light();
    }

    public void start()
    {
        c1.start();
        c2.start();
        alarm.start();
        l1.start();
        l2.start();
    }

    public void end()
    {
        c1.shutdown();
        c2.shutdown();
        alarm.shutdown();
        l1.shutdown();
        l2.shutdown();
    }
}
