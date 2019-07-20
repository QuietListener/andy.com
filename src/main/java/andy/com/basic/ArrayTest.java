package andy.com.basic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class ArrayTest {

    AtomicInteger a = null;
    @Test
    public void TestArray(){
        List<Integer> a  = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        List<Integer> b =  new ArrayList<>(Arrays.asList(3,4,5,6,7,8));
        a.retainAll(b);
        System.out.println(a);

        List<Integer> ids  = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        int limit = 3;
        int totalCount = ids.size();
        for (int i = 0; i < totalCount; i += limit) {
            int from = i;
            int to = i + limit > totalCount ? totalCount : i + limit;

            List<Integer> ids_ = ids.subList(from, to);
            System.out.println(ids_);
        }
    }

}
