package andy.com.nio.socket;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 使用非阻塞模式的SocketChannel,ServerSocketChannel.
 */
public class Server {
    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel = null;
    private int port = Common.PORT;
    private Charset charset = Charset.forName("GBK");

    public Server() throws IOException {
        // 创建一个selector对象
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().setReuseAddress(true);
        // 使serverSocketChannel工作于非阻塞模式
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("服务器启动...");
    }

    public void service() throws IOException
    {
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true)
        {
            int i = selector.select(Common.TIMEOUT);
            if(i == 0)
            {
                continue;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = readyKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = null;
                try {
                    key = it.next();
                    it.remove();

                    if (key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key
                                .channel();
                        SocketChannel socketChannel = (SocketChannel) ssc
                                .accept();
                        System.out.println("接收到客户连接，来自："
                                + socketChannel.socket().getInetAddress() + ":"
                                + socketChannel.socket().getPort());
                        socketChannel.configureBlocking(false);
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.register(selector, SelectionKey.OP_READ
                                | SelectionKey.OP_WRITE, buffer);
                    }

                    if (key.isReadable()) {
                        System.out.println("is readable");
                        receive(key);

                        //防止cpu100%
                        key.interestOps(SelectionKey.OP_WRITE);
                    }

                    if (key.isWritable()) {
                        System.out.println("is writable");
                        send(key);

                        //防止cpu100%
                        key.interestOps(SelectionKey.OP_READ);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        if (key != null) {
                            key.cancel();
                            key.channel().close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }


    public void send(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        buffer.flip();// 把极限设为位置,把位置设为0, 准备读

        String data = decode(buffer);

        if (data.indexOf("\r\n") == -1) {
            return;
        }

        String outputData = data.substring(0, data.indexOf("\n") + 1);
        System.out.print(outputData);
        ByteBuffer outputBuffer = encode("echo:" + outputData);
        while (outputBuffer.hasRemaining()) {
            int i = socketChannel.write(outputBuffer);
            System.out.println("write"+i);
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
        System.out.println("recieve");
        ByteBuffer buffer = (ByteBuffer) key.attachment();
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

    public static void main(String[] args) throws IOException {
        new Server().service();
    }
}
