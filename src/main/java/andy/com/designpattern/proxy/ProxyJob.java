package andy.com.designpattern.proxy;

public class ProxyJob implements Job {

    private Job job;

    public ProxyJob(String name){
        job = new ConcretJob(name);
    }

    @Override
    public void doJob() {
        System.out.println("before doJob");
        this.job.doJob();
        System.out.println("after doJob");
    }
}
