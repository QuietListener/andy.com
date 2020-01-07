package andy.com.jsengine;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.io.File;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


//get output of script
@SuppressWarnings("restriction")
public class Example5CallJsFunc {


    public static void main(String[] args) throws Exception {
        testCallJsFunc();
        testImplInterface();
    }

    //java调用js方法
    public static void testCallJsFunc() throws Exception {
        ScriptEngine engine = JSEngineUtils.getJsEngineInstance();

        File jsFile = JSEngineUtils.getFiles("example4_calljsfunc.js");
        Reader reader = Files.newBufferedReader(Paths.get(jsFile.getAbsolutePath()), Charset.forName("UTF8"));
        engine.eval(reader);
        Invocable inv = (Invocable) engine;
        Object caculator = engine.get("caculator");

        int x = 1;
        int y = 2;
        Object result = inv.invokeMethod(caculator, "add", x, y);
        System.out.println(result);
    }

    //js实现java的interface
    public static void testImplInterface() throws Exception {
        ScriptEngine engine = JSEngineUtils.getJsEngineInstance();

        File jsFile = JSEngineUtils.getFiles("example4_impl_interface.js");
        Reader reader = Files.newBufferedReader(Paths.get(jsFile.getAbsolutePath()), Charset.forName("UTF8"));
        engine.eval(reader);
        Invocable inv = (Invocable) engine;
        Math math = inv.getInterface(Math.class);

        int x = 3;
        int y = 2;
        double result = math.add(x, y);
        System.out.println(result);
    }

}