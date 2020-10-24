package andy.com.nio.socketProtocal;

import andy.com.nio.buffer.protocal.Frame;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Client {
    private SocketChannel socketChannel = null;
    private ByteBuffer sendBuffer = ByteBuffer.allocate(10240);
    private ByteBuffer receiveBuffer = ByteBuffer.allocate(10240);
    private Charset charset = Charset.forName("GBK");
    private Selector selector;

    public Client() throws IOException {
        socketChannel = SocketChannel.open();
        InetAddress ia = InetAddress.getLocalHost();
        InetSocketAddress isa = new InetSocketAddress(ia, Common.PORT);
        socketChannel.connect(isa);
        socketChannel.configureBlocking(false);
        System.out.println("与服务器的连接建立成功");
        selector = Selector.open();
    }

    public static void main(String[] args) throws IOException {
        final Client client = new Client();
        client.talk();


    }

    private class AddDataThread extends Thread {

        @Override
        public void run() {

            int seq = 0;
            while (true) {
                try {
                    String str = String.format("%s data", seq);

                    Frame f = Frame.encode(str);
                    Client.this.sendBuffer.put(f.getData());

                    Date t = new Date();
                    System.out.println(t + " :" + str);
                    TimeUnit.SECONDS.sleep(new Random().nextInt(3) + 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void talk() throws IOException {
        try {
            socketChannel.register(selector, SelectionKey.OP_READ
                    | SelectionKey.OP_WRITE);
            while (selector.select() > 0) {
                Set<SelectionKey> readyKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = readyKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = null;
                    try {
                        key = (SelectionKey) it.next();
                        it.remove();
                        if (key.isReadable()) {
                            receive(key);
                        }
                        if (key.isWritable()) {
                            send(key);
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

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        synchronized (sendBuffer) {
            sendBuffer.flip();
            socketChannel.write(sendBuffer);
            sendBuffer.compact();
        }
    }

    public void receive(SelectionKey key) throws IOException {
        // 接收EchoServer发送的数据，把它放到receiveBuffer中
        // 如果receiverBuffer中有一行数据，就打印这行数据，然后把它从receiverBuffer中删除
        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.read(receiveBuffer);


        //receiveBuffer.position(temp.limit());
        receiveBuffer.compact();
    }

    public String decode(ByteBuffer buffer) {
        CharBuffer charBuffer = charset.decode(buffer);
        return charBuffer.toString();
    }

    public ByteBuffer encode(String str) {
        return charset.encode(str);
    }
}