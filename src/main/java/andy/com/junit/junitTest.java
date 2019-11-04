package andy.com.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class junitTest {

    private String preparedData = null;

    @Before
    public void setUp() throws Exception
    {
        this.preparedData = "i am data";
        System.out.println("初始化对象: preparedData = "+preparedData);
    }

    @Test
    public void testSomething()
    {
        assert preparedData.equals("i am data");
    }

    @After
    public void tearDown() throws Exception {
        preparedData = null;
        System.out.println("对象将被清理！,preparedData = "+preparedData);

    }


    @Test
    public void test() {

        List<String> a = Arrays.asList("bb1","aaa","ccc");
        List<String> b =  a.stream().sorted().collect(Collectors.toList());
        System.out.println(a);
        System.out.println(b);
    }

}
