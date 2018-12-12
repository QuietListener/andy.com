package andy.com.basic;

public class TestChinese {

    public static boolean isChinese(char c) {
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }

    public static boolean isChinese(String str) {
        if (str == null) return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) return true;// 有一个中文字符就返回
        }
        return false;
    }
    public static void main(String [] args){

        String a = "你好 12哈哈";
        String b = "hello world";

        System.out.println(isChinese(a)+":"+a);
        System.out.println(isChinese(b)+":"+b);
    }
}
