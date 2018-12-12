package andy.com.basic;

public class EnumTest {
    public enum Source {
        Baidu(0), YouDao(1);

        private final int value;
        private Source(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    public static void main(String [] args){
        Source s1 = Source.Baidu;
        System.out.println(s1);
        System.out.println(s1.getValue());
    }
}
