package andy.com.jsengine;

import java.io.File;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@SuppressWarnings("restriction")
public class JSEngineUtils {

	static public File getBaseDir()
	{
		File dir =  new File("others/js");
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		
		return dir;
	}
	
	static public File getFiles(String name)
	{
		File dir = getBaseDir();
		File f = new File(dir,name);
		return f;
	}
	
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
