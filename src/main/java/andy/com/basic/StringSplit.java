package andy.com.basic;

import java.util.Arrays;

public class StringSplit {

    public static void main(String[] args) {
        String a = "a|b|c";
        String b = "a,b,c";
        System.out.println(Arrays.asList(a.split("|")));
        System.out.println(Arrays.asList(a.split("\\|")));
        System.out.println(Arrays.asList(a.split(",")));
    }
}
