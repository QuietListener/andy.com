package andy.com.serialize;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

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

    @Test
    public void test12321(){
        Map<String,Object> data = new HashMap<>();

        Map<String,Object> data1 = new HashMap<>();
        data1.put("aaa",1);
        data1.put("bbb",1.111);

        data.put("attrs",data1);
        Data d = new Data();
        d.setData(data);

        Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<Map>(){}.getType(),new DataTypeAdapter()).create();

        String str = gson.toJson(d);
        System.out.println("str:"+str);

       
        Object obj = gson.fromJson(str,new com.google.common.reflect.TypeToken<Data>(){}.getType());
        System.out.println(obj);

    }




    private static class Data{
        private Map data;

        public Map getData() {
            return data;
        }

        public void setData(Map data) {
            this.data = data;
        }
    }

    private static class DataTypeAdapter extends TypeAdapter<Object> {

        private final TypeAdapter<Object> delegate = new Gson().getAdapter(Object.class);

        @Override
        public Object read(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            switch (token) {
                case BEGIN_ARRAY:
                    List<Object> list = new ArrayList<>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(read(in));
                    }
                    in.endArray();
                    return list;

                case BEGIN_OBJECT:
                    Map<String, Object> map = new LinkedTreeMap<>();
                    in.beginObject();
                    while (in.hasNext()) {
                        map.put(in.nextName(), read(in));
                    }
                    in.endObject();
                    return map;

                case STRING:
                    return in.nextString();

                case NUMBER:
                    /**
                     * 改写数字的处理逻辑，将数字值分为整型与浮点型。
                     */
                    double dbNum = in.nextDouble();

                    // 数字超过long的最大值，返回浮点类型
                    if (dbNum > Long.MAX_VALUE) {
                        return dbNum;
                    }
                    // 判断数字是否为整数值
                    long lngNum = (long) dbNum;
                    if (dbNum == lngNum) {
                        try {
                            return (int) lngNum;
                        } catch (Exception e) {
                            return lngNum;
                        }
                    } else {
                        return dbNum;
                    }

                case BOOLEAN:
                    return in.nextBoolean();

                case NULL:
                    in.nextNull();
                    return null;

                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            delegate.write(out, value);
        }
    }
    
}





