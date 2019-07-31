package andy.com.books.java并发编程实践.charpter3;

public class Novisibility {
    private static boolean ready;
    private static int number;
    //有可能0 有可能不会停下
    private static class  ReaderThread extends Thread{
        public void run(){
            while(!ready){
                Thread.yield();
            }

            System.out.println(number);
        }
    }

    public static void main(String [] args){
        new ReaderThread().start();

        number = 42;
        ready = true;
    }
}
