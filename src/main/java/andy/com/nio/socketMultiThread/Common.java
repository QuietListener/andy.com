package andy.com.nio.socketMultiThread;

import java.nio.ByteBuffer;

public class Common
{
    public static final int PORT = 10000;
    public static final int TIMEOUT = 5000;

    static void putBuf(ByteBuffer buf)
    {
        System.out.println("position="+buf.position()+",limit = "+buf.limit()+", compacity="+buf.capacity());
    }

}
