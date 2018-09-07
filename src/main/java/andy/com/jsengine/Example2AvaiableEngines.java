package andy.com.jsengine;

import java.util.List;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

//print all available engines
@SuppressWarnings("restriction")
public class Example2AvaiableEngines {

	public static void main(String[] args) {
		
		ScriptEngineManager manager = new ScriptEngineManager();
		List<ScriptEngineFactory> list = manager.getEngineFactories();
		for(ScriptEngineFactory f : list)
		{
			System.out.println("engine name:"+f.getEngineName()+" , versoin "+f.getEngineVersion()+" ; language name:"+f.getLanguageName() +" version:"+f.getLanguageVersion());
		}
 	}

}
