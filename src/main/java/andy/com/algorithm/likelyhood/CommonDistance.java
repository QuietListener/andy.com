package andy.com.algorithm.likelyhood;

import com.google.common.primitives.Chars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommonDistance {

    public static double stringLikelyhood(String s1, String s2){

        long start =  System.currentTimeMillis();
        s1 = s1.replaceAll("[,|，|。| |\\s]","");
        s2 = s2.replaceAll("[,|，|。| |\\s]","");
        long span = System.currentTimeMillis() - start;
        System.out.println("used1 "+ span);

        List<Object> sc1 =  new ArrayList(Chars.asList(s1.toCharArray()));
        List<Object> sc2 =  new ArrayList(Chars.asList(s2.toCharArray()));
        List<Object> sc3 =  new ArrayList(sc1);

        span = System.currentTimeMillis() - start;
        System.out.println("used2 "+ span);

        sc3.addAll(sc2);
        sc1.retainAll(sc2);

        span = System.currentTimeMillis() - start;
        System.out.println("used3 "+ span);

        sc1 = sc1.stream().distinct().collect(Collectors.toList());
        sc3 = sc3.stream().distinct().collect(Collectors.toList());

        span = System.currentTimeMillis() - start;
        System.out.println("used4 "+ span);
        return sc1.size()*1.0/sc3.size();
    }

    public static void main(String [] args){
        List<String> s1s = Arrays.asList("你好，优秀生是范德萨发生好腻.asdfayesye就是","好    的。","你好，优秀生是范德萨发生好腻.asdfayesye就是");
        List<String> s2s = Arrays.asList("好腻 但事实上电风扇你好","好的呀asfasfds对对对","好腻 但事实上电风扇你好");

        long start =  System.currentTimeMillis();
        for(int i = 0;i < s1s.size(); i++){
                String s1 = s1s.get(i);
                String s2 = s2s.get(i);
                double score = stringLikelyhood(s1,s2);
                System.out.println(String.format("%s  |  %s  = %s ",s1,s2,score));
        }

        long span = System.currentTimeMillis() - start;
        System.out.println("used "+ span);


    }
}
