package andy.com.java8.functional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

public class TestFunctional3 {

    CacheHelper cacheHelper;

    public static void main(String [] args){


    }

    void test(){
        int x = 1;String y = "2";
    }

    Test2 getTest2(int i, String j){
        return new Test2();
    }

}

class CacheHelper {

    public <T, R> R getAndSetCache(String key, R defaultValue, Function<T, R> f) {

        R ret = null;
        try {

        }finally {

        }

        if (ret != null) {
            return ret;
        }

        ret = f.apply(null);
//
//        if (ret == null) {
//            cacheHelper.setNullFlag(key);
//        } else {
//            cacheHelper.setObj(key, ret);
//        }

        return ret;
    }

    public <T, R> T getObj(String key, Function<T, R> funciton) throws Exception {
        Type type = this.getClass().getDeclaredMethod("getObj").getGenericReturnType();


        return null;
    }
}

class Test1 {
    private String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

class Test2 {
    Test1 test1 = null;
    List<Test1> test1List;

    public Test1 getTest1() {
        return test1;
    }

    public void setTest1(Test1 test1) {
        this.test1 = test1;
    }

    public List<Test1> getTest1List() {
        return test1List;
    }

    public void setTest1List(List<Test1> test1List) {
        this.test1List = test1List;
    }
}