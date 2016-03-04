package andy.com.jsengine;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

@SuppressWarnings("restriction")
public class Example1HelloWordl {

	public static void main(String[] args) {
		
		ScriptEngine engine = JSEngineUtils.getJsEngineInstance();
		
		// Js script
		String script = "print(' hello world !')";
		
		//run javascript
		try
		{
			engine.eval(script);
		}
		catch(ScriptException e)
		{
			e.printStackTrace();
		}
	}

}
