package andy.com.nio.buffer;

import org.junit.Test;

public class ByteTest {

    //byte 数组与 int 的相互转换
    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    public void binaryToDecimal(int n) {
        for (int i = 0; i<7; i++)
            System.out.print(n >>> i & 1);
    }

    @Test
    public void test() {
        int a = 5;
        System.out.println("a = " + a);
        byte b1 = 5;
        System.out.println(b1);
        binaryToDecimal(5);

        byte []bs = intToByteArray(1024);


        System.out.println();

        for(int i = 0; i < bs.length;i++){
            System.out.println("i="+i+":"+bs[i]);
        }

        byte [] bs2 = new byte []{0,0,8,0};

        System.out.println("byteArrayToInt="+byteArrayToInt(bs2));
    }
}
