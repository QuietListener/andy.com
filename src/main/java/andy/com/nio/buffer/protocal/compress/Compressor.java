package andy.com.nio.buffer.protocal.compress;

import java.io.IOException;

public interface Compressor {


    /**
     * 压缩指定的字符串
     *
     * @param str
     * @return
     * @throws IOException
     */
    public byte[] compress(byte[] str) throws IOException;

    /**
     * 解压缩字节数组
     *
     * @param b
     * @return
     * @throws IOException
     */
    public byte[] uncompress(byte[] b) throws IOException;


}
