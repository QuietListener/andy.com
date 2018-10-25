package andy.com.serialize;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;

/**
 *最近在研究网络请求数据解析的问题，发现json数据被强制转换为map结构的时候，会出现int变成double的问题
 *在stackoverflow上看到了一个这个How can I prevent gson from converting integers to doubles 的问题，采用了这个答案
 *https://stackoverflow.com/a/36529534/5279354答案
 */

import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class MapDeserializerDoubleAsIntFix implements JsonDeserializer<Map<String, Object>> {


    @Override
    public Map<String, Object> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return (Map<String, Object>) read(jsonElement);
    }

    public Object read(JsonElement in) {
        if(in.isJsonArray()){
            List<Object> list = new ArrayList<>();
            JsonArray arr = in.getAsJsonArray();
            for (JsonElement anArr : arr) {
                list.add(read(anArr));
            }
            return list;
        }else if(in.isJsonObject()){
            Map<String, Object> map = new LinkedTreeMap<String, Object>();
            JsonObject obj = in.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entitySet = obj.entrySet();
            for(Map.Entry<String, JsonElement> entry: entitySet){
                map.put(entry.getKey(), read(entry.getValue()));
            }
            return map;
        }else if( in.isJsonPrimitive()){
            JsonPrimitive prim = in.getAsJsonPrimitive();
            if(prim.isBoolean()){
                return prim.getAsBoolean();
            }else if(prim.isString()){
                return prim.getAsString();
            }else if(prim.isNumber()){
                Number num = prim.getAsNumber();
                // here you can handle double int/long values
                // and return any type you want
                // this solution will transform 3.0 float to long values
                if(Math.ceil(num.doubleValue())  == num.longValue())
                    return num.longValue();
                else{
                    return num.doubleValue();
                }
            }
        }
        return null;
    }

}

/**
 * Gson把数字全部转换为Double的问题
 */
public class GsonTest {

    private String json;
    @Before
    public void setUp()
    {
        this.json = "{\"data\":[{\"id\":1,\"quantity\":2,\"name\":\"apple\"}, {\"id\":3,\"quantity\":4,\"name\":\"orange\"}]}";
        System.out.println("json == " + json);
    }
    /**
     * 有问题的
     */
    @Test
    public void wrong()
    {
        Map<String, Object> map = new LinkedTreeMap<>();
        map = new Gson().fromJson(json, map.getClass());
        System.out.println(map);
        //{data=[{id=1.0, quantity=2.0, name=apple}, {id=3.0, quantity=4.0, name=orange}]}

    }

    /**
     * 重写JsonDeserializer解决这个问题
     */
    @Test
    public void ok() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<Map <String, Object>>(){}.getType(),  new MapDeserializerDoubleAsIntFix());
        Gson gson = gsonBuilder.create();
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
        System.out.println(map);
        //{data=[{id=1, quantity=2, name=apple}, {id=3, quantity=4, name=orange}]}
    }
}
