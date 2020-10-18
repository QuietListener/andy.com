package andy.com.nio.buffer.protocal;

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

    public byte [] getBytes(){
    }

    public static Frame encode(String s){
        return new Frame(s);
    }

    public String decode(byte [] bs){
        Frame f = new Frame();
        ns
    }

    public static void main(String[] args) {
        String a = "abcdefghiaaaaaaadddddddddddddddddddddddddsssssssssssssssssssssssslalallalaaaaaaaaaaaaaaalallalalalldddddddddj";


    }
}
