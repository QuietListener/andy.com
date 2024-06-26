package andy.com.nio.buffer.protocal;

public class PUtils {
    //byte 数组与 int 的相互转换
    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    public static void main(String [] args){
        int a = 7;
        byte [] bs = intToByteArray(a);
        for(int i = 0; i < bs.length; i++){
            System.out.println(bs[i]);
        }
    }
}
