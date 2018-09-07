package andy.com.file;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
public class FileWatcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = Paths.get("/User/junjun/software/hadoop-2.7.3");
			dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
			
			System.out.println("Watch Service registered for dir: " + dir.getFileName());
			
			while (true) 
			{
				WatchKey key;
				
				try 
				{
					key = watcher.take();
				} 
				catch (InterruptedException ex) 
				{
					return;
				}
				
				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();
					
					@SuppressWarnings("unchecked")
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path fileName = ev.context();
					
					System.out.println(kind.name() + ": " + fileName);
					
//					if (kind == StandardWatchEventKinds.ENTRY_MODIFY ) {
//						System.out.println("My source file has changed!!!");
//					}
				}
				
				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}
			
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}


