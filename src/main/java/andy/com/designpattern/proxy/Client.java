package andy.com.designpattern.proxy;

public class Client {

    public static void main(String [] args){

        Job job = new ProxyJob("job1 ");
        job.doJob();;
    }
}

