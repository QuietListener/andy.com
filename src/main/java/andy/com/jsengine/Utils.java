package andy.com.jsengine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@SuppressWarnings("restriction")
public class Utils {

	/**
	 * get one Js Engine instance;
	 * @return
	 */
	static public ScriptEngine getJsEngineInstance()
	{
		ScriptEngineManager m = new ScriptEngineManager();
		
		//default engine is rhino
		ScriptEngine engine = m.getEngineByName("JavaScript");
		
		return engine;
	}

}
