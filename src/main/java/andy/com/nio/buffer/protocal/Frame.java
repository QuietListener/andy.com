package andy.com.nio.buffer.protocal;

import andy.com.nio.buffer.protocal.compress.Compressor;
import andy.com.nio.buffer.protocal.compress.GzipCompressor;
import org.apache.commons.lang.ArrayUtils;

import java.io.IOException;
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

    public static final int HEAD_LENGTH = 6;
    public static final byte COMPRESS_GZIP = 0x1;
    public static final byte COMPRESS_NONE = 0x0;

    byte[] head = new byte[6];
    byte[] data = null;

    private Compressor cgzip = new GzipCompressor();

    private Frame() {}

    private Frame(String s,byte compressType ) throws IOException {

        head[0] = 1; //type1 data;
        head[1] = 1; //version 1
        head[2] = compressType; //压缩算法
        head[3] = 0; //保留

        data = s.getBytes();
        System.out.println("data.length = "+data.length);
        if(head[2] != COMPRESS_NONE){
            if(head[2] == COMPRESS_GZIP){
                data = cgzip.compress(data);
                System.out.println("cdata.length = "+data.length);
            }
        }

        int length = data.length;
        byte[] lbytes = PUtils.intToByteArray(length);

        //长度2个字节
        head[4] = lbytes[2];
        head[5] = lbytes[3];

    }

    public byte[] getHead() {
        return head;
    }


    public byte[] getData() {
        return data;
    }


    public static Frame encode(String s) throws IOException{
        return new Frame(s,COMPRESS_GZIP);
    }

    public static Frame decode(byte [] bs) throws IOException{
        Frame f = new Frame();
        f.head = Arrays.copyOfRange(bs,0,6);
        byte[] lengthBytes = new byte[]{0,0,f.head[4],f.head[5]};
        int length = PUtils.byteArrayToInt(lengthBytes);
        f.data = Arrays.copyOfRange(bs,HEAD_LENGTH,length+HEAD_LENGTH);

        if(f.head[2] != COMPRESS_NONE){
            if(f.head[2] == COMPRESS_GZIP){
                f.data = f.cgzip.uncompress(f.data);
            }
        }
        return f;
    }

    public static void main(String[] args) throws  Exception {
        String a = "abcdefghiaaaaaaadddddddddddddddddddddddddsssssssssssssssssssssssslalallalaaaaaaaaaaaaaaalallalalalldddddddddj1123";

        Frame f = Frame.encode(a);

        byte [] h = f.getHead();
        byte [] d = f.getData();

        byte[] interupt = new byte[]{1,2,2,4,5};

        byte [] both = ArrayUtils.addAll(h,d);
        both = ArrayUtils.addAll(both,interupt);


        Frame f1 = Frame.decode(both);
        String a1 = new String(f1.getData());

        System.out.println(a.equals(a1));

    }
}
