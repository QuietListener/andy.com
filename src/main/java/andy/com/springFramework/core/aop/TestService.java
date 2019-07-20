package andy.com.springFramework.core.aop;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Service("TestService")
public class TestService {

    static Gson gson = new Gson();
    static private String gsonStr = null;

    public String doJob(String jobName, String content) {
        System.out.println(jobName + "," + content);
        return jobName + " done";
    }

    @AopAnnotationAround
    public String doOtherJob1(String jobName) {
        System.out.println(jobName + ",");
        //int a = 1/0;
        return jobName + " done(other job)";
    }


    public TestRet doOtherJob2(String jobName, String count) {
        System.out.println(jobName + "," + count);
        //int a = 1/0;
        return getTR();
    }


    public  <T> T getObj(String key, Supplier<T> func,Type type) throws  Exception{
        T t = null;
        if(gsonStr!=null){
            t = gson.fromJson(gsonStr,type);
            return t;
        }

        t = func.get();
        if(t!=null){
            gsonStr = gson.toJson(t);
        }
        return t;
    }


    public static void main(String [] args) throws Exception{
        TestService ts = new TestService();
        TestRet ret = ts.getObj("key",()->ts.getTR(),new TypeToken<TestRet>(){}.getType());
        System.out.println(ret);

        TestRet ret1 = ts.getObj("key",()->ts.getTR(),new TypeToken<TestRet>(){}.getType());
        System.out.println(ret1);
    }

    public TestRet getTR(){
        TestRet tr =  new TestRet();
        TestRet1 tr1 = new TestRet1();
        tr1.setName("good");
        List<TestRet1> tr1List = new ArrayList<>();
        tr1List.add(tr1);
        tr.setList(tr1List);
        tr.setRet(11);
        return tr;
    }
}

class TestRet1 {
    private String name = "";

    @Override
    public String toString() {
        return "TestRet1{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class TestRet {
    List<TestRet1> list;
    Integer ret;

    @Override
    public String toString() {
        return "TestRet{" +
                "list=" + list +
                ", ret=" + ret +
                '}';
    }

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public List<TestRet1> getList() {
        return list;
    }

    public void setList(List<TestRet1> list) {
        this.list = list;
    }
}
