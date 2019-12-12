package andy.com.concurrent.synchronizers.aqs;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

class OnShotLatch {
    private final Sync sync = new Sync();


    public void await() throws InterruptedException{
        //sync.acquireSharedInterruptibly(0);
        sync.acquire(0);
    }

    public void signal(){
        sync.releaseShared(0);
    }

    private class Sync extends AbstractQueuedSynchronizer{
        protected int tryAcquireShared(int ignored){
            return(getState() == 1) ?1 : -1;
        }

        @Override
        protected boolean tryAcquire(int ignored){
            return(getState() == 1) ?true : false;
        }

        protected  boolean tryReleaseShared(int ignored){
            setState(1);
            return true;
        }

        protected  boolean tryRelease(int ignored){
            setState(1);
            return true;
        }
    }

    public static void main (String [] args){

        final OnShotLatch latch = new OnShotLatch();

        for(int i = 0; i<5;i++){

            Thread t = new Thread(){

                @Override
                public void run(){

                    try {
                        System.out.println(getName()+":"+new Date().getTime()+": wait for job");
                        latch.await();

                        System.out.println(getName()+":"+new Date().getTime()+": doing a job");
                        TimeUnit.SECONDS.sleep(5);

                        System.out.println(getName()+":"+new Date().getTime()+": finished a job");
                        latch.signal();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            t.setName("t-"+i);
            t.start();
        }


        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (Exception e){
            e.printStackTrace();
        }
        latch.signal();
    }
}
