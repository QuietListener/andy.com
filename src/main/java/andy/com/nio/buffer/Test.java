package andy.com.nio.buffer;

import java.nio.ByteBuffer;

public class Test {

    public static void main(String [] args)
    {
        ByteBuffer buf = ByteBuffer.allocate(1000);

        System.out.println("\r\n===============写==================");
        buf.put("hello".getBytes());
        buf.put("1".getBytes());
        putBuf(buf);


        System.out.println("\r\n===============读==================");
        //讲buf从写模式切换到读模式
        //limit设置为position, 然后position设置为0,limit
        buf.flip();
        putBuf(buf);
        while(buf.hasRemaining()){
            System.out.print((char) buf.get()); // read 1 byte at a TimeTest
        }
        System.out.println();
        putBuf(buf);


        System.out.println("\r\n===============重读(rewind)==================");
        buf.rewind();
        putBuf(buf);
        while(buf.hasRemaining()){
            System.out.print((char) buf.get()); // read 1 byte at a TimeTest
        }
        System.out.println();
        putBuf(buf);

        //rewind()方法
        //Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。




        //如果调用的是clear()方法，position将被设回0，limit被设置成 capacity的值。换句话说，Buffer 被清空了。
        // Buffer中的数据并未清除，只是这些标记告诉我们可以从哪里开始往Buffer里写数据。
        System.out.println("\r\n===============清空clear==================");
        buf.clear();
        putBuf(buf);


        //compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。
        // limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据。
        buf.compact();
    }

    static void putBuf(ByteBuffer buf)
    {
        System.out.println("position="+buf.position()+",limit = "+buf.limit()+", compacity="+buf.capacity());
    }
}


