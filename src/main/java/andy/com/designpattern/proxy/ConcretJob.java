package andy.com.designpattern.proxy;

public class ConcretJob implements Job {

    private String name = null;

    public ConcretJob(String name){
        this.name = name;
    }

    @Override
    public void doJob() {
        System.out.println(name+" do job");
    }
}
