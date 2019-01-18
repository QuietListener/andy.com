package andy.com.serialize;


import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GsonTest1 {
    private static Gson gson = new Gson();

    static class User{
        private String name = "22";
        private int    age = 2;
    }

    @Test
    public void test1(){
        List<User> us = Arrays.asList(new User(),new User());
        String json = gson.toJson(us);
        System.out.println(json);

        List<User> us1 = gson.fromJson(json, new ArrayList<User>().getClass());
        System.out.println(us1);
    }
}
