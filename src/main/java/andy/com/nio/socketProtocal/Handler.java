package andy.com.nio.socketProtocal;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Handler
{
    public static final int TYPE_READ = 0;
    public static final int TYPE_WRITE = 1;

    private SelectionKey key = null;
    private Charset charset = Charset.forName("GBK");
    private int type = -1;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    public Handler(SelectionKey key,int type)
    {
       // this.type = type;
       // this.key = key;
    }

    public void send(SelectionKey key) throws IOException {
       // ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        buffer.flip();// 把极限设为位置,把位置设为0, 准备读

        String data = decode(buffer);

        if (data.indexOf("\r\n") == -1) {
            return;
        }


        String outputData = data.substring(0, data.indexOf("\n") + 1);
        System.out.print(Thread.currentThread().getName()+":"+outputData);
        ByteBuffer outputBuffer = encode("echo:" + outputData);
        while (outputBuffer.hasRemaining()) {
            int i = socketChannel.write(outputBuffer);
        }

        ByteBuffer temp = encode(outputData);
        buffer.position(temp.limit());
        buffer.compact();// 删除已经处理的字符串
        if (outputData.equals("bye\r\n")) {
            key.cancel();
            socketChannel.close();
            System.out.println("关闭与客户端的连接");
        }
    }

    public void receive(SelectionKey key) throws IOException {
       // ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer readBuff = ByteBuffer.allocate(32);
        socketChannel.read(readBuff);
        readBuff.flip();
        buffer.limit(buffer.capacity());
        buffer.put(readBuff);
    }

    public String decode(ByteBuffer buffer) {
        CharBuffer charBuffer = charset.decode(buffer);
        return charBuffer.toString();
    }

    public ByteBuffer encode(String str) {
        return charset.encode(str);
    }

}
