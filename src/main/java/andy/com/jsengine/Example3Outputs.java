package andy.com.jsengine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

//get output of script
@SuppressWarnings("restriction")
public class Example3Outputs {

	public static void main(String[] args) throws IOException, ScriptException {
		
		ScriptEngine engine = JSEngineUtils.getJsEngineInstance();
		
		//the outputs will be written to example3_output；
		File file = new File(JSEngineUtils.getBaseDir(),"example3_output.txt");
		FileWriter writer = new FileWriter(file);
		
		engine.getContext().setWriter(writer);
		String script = "var a = 1; var b = 3; var c = a+b; "
					+"print('a='+a); print('b='+b);print('a+b='+c);";
					
		engine.eval(script);
		writer.close();
		
 	}

}
