package andy.com.java8.functional;

/**
 * 自定一个一个funcitional的interface
 */
@FunctionalInterface
public interface MyFuntional {
    default int min(int x, int y)
    {
        return x>y?y:x;
    }

    int add(int x, int y);
}
