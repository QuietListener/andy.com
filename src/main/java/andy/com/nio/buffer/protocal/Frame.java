package andy.com.nio.buffer.protocal;

import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;

/**
 * 消息头:
 * 1个byte做为type
 * 1个byte做为version
 * 1个byte作为压缩算法
 * 1个byte作为保留字
 * 2个byte做为length
 */
public class Frame {

    public static final int MaxLength = 65535;
    byte[] head = new byte[6];
    byte[] data = null;

    private Frame() {}

    private Frame(String s) {

        head[0] = 1; //type1 data;
        head[1] = 1; //version 1
        head[2] = 0; //无压缩
        head[3] = 0; //保留

        data = s.getBytes();
        int length = data.length;
        byte[] lbytes = PUtils.intToByteArray(length);

        //长度2个字节
        head[4] = lbytes[0];
        head[5] = lbytes[1];

    }

    public byte[] getHead() {
        return head;
    }


    public byte[] getData() {
        return data;
    }


    public static Frame encode(String s){
        return new Frame(s);
    }

    public static Frame decode(byte [] bs){
        Frame f = new Frame();
        f.head = Arrays.copyOfRange(bs,0,6);
        f.data = Arrays.copyOfRange(bs,6,bs.length);
        return f;
    }

    public static void main(String[] args) {
        String a = "abcdefghiaaaaaaadddddddddddddddddddddddddsssssssssssssssssssssssslalallalaaaaaaaaaaaaaaalallalalalldddddddddj";

        Frame f = Frame.encode(a);

        byte [] h = f.getHead();
        byte [] d = f.getData();

        byte [] both = ArrayUtils.addAll(h,d);

        Frame f1 = Frame.decode(both);
        String a1 = new String(f1.getData());

        System.out.println(a.equals(a1));

    }
}
