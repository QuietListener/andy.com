package andy.com.designpattern.facade;

public class Light {
    public void start()
    {
        System.out.println("start "  +this.getClass().getSimpleName());
    }

    public void shutdown()
    {
        System.out.println("shutdown "  +this.getClass().getSimpleName());
    }
}
