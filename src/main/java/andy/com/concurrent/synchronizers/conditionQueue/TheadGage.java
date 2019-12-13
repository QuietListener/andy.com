package andy.com.concurrent.synchronizers.conditionQueue;

/**
 * 条件队列例子:可以关闭的闸门。
 *
 * 条件队列 条件谓词 锁
 *
 * 为什么要加一个 generation？
 * 试想如果不加 generation，当砸门打开再快速关闭，await只检查isOpen,某些线程可能永远都只会在
 *  while (isOpen == false)
 *     await()
 * 中，不会被唤醒了
 */
public class TheadGage {

    //条件谓词就是: (isopen || generation > n)
    private boolean isOpen = false;
    private int generation;


    public synchronized void close() {
        isOpen = false;
    }

    public synchronized void open() {
        ++generation;
        isOpen = true;
        notifyAll();
    }

    /**
     * 放开闸门的条件 : isOpen == true || arrivalGeneration != generation
     * @throws InterruptedException
     */
    public synchronized void await() throws InterruptedException {
        int arrivalGeneration = generation;
        while (isOpen == false && arrivalGeneration == generation) {
            wait();
        }
    }


    public static void main(String [] args) throws InterruptedException{

    }
}




