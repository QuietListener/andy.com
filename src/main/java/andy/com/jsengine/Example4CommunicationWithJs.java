package andy.com.jsengine;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;


//get output of script
@SuppressWarnings("restriction")
public class Example4CommunicationWithJs {

    public static void main(String[] args) throws IOException, ScriptException {

        ScriptEngine engine = JSEngineUtils.getJsEngineInstance();
        Result result = new Result("blank", 0);

        System.out.println("1:" + result);
        engine.put("result", result);

        //The eval() method from ScriptEngine returns the last value in the script as an Object.
        Object ret = engine.eval(" print('hello:'+result);var obj = {'p1':1,'p2':'haha',result:result}; obj");
        System.out.println("\nret = " + ret);
        System.out.println("2:" + result);
    }

    static class Result {
        private String name;
        private int age;

        public Result(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return name + "," + age;
        }
    }

}
