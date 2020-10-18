package andy.com.nio.socketMultiThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用非阻塞模式的SocketChannel,ServerSocketChannel.
 */
public class Server {
    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel = null;
    private int port = Common.PORT;

    private static final ExecutorService es = Executors.newFixedThreadPool(3);

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

    public void service() throws IOException {
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int i = selector.select(Common.TIMEOUT);
            if (i == 0) {
                continue;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = readyKeys.iterator();
            while (it.hasNext()) {
                final SelectionKey key = it.next();
                it.remove();
                try {

                    if (key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key
                                .channel();
                        SocketChannel socketChannel = (SocketChannel) ssc
                                .accept();
                        System.out.println("接收到客户连接，来自："
                                + socketChannel.socket().getInetAddress() + ":"
                                + socketChannel.socket().getPort());
                        socketChannel.configureBlocking(false);
                        Handler h = new Handler(key, -1);

                        socketChannel.register(selector, SelectionKey.OP_READ
                                | SelectionKey.OP_WRITE, h);
                    }

                    if (key.isReadable()) {
                        Handler h = (Handler) key.attachment();
                        es.submit(new Runnable() {
                            @Override
                            public void run() {
                                synchronized (h) {
                                    try {
                                        h.receive(key);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }

                    if (key.isWritable()) {

                        final Handler h = (Handler) key.attachment();
                        es.submit(new Runnable() {
                            @Override
                            public void run() {
                                synchronized (h) {
                                    try {
                                        h.send(key);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                    }

                    System.out.println("select ");
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


    public static void main(String[] args) throws IOException {
        new Server().service();
    }

}
