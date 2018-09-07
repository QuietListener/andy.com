package andy.com.basic;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class TestFileUtils {

	public static File createNoEmptyDir() throws IOException
	{
		File f = new File("/tmp/testFileUtils");
		f.mkdirs();
		File f1 = new File(f.getAbsolutePath(),"test.txt");
		f1.createNewFile();
		return f;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		
		File file = createNoEmptyDir();
		
		boolean deleted = file.delete();
		System.out.println(file.getAbsolutePath() + " deleted = " + deleted);
		
		System.out.println(file.getAbsolutePath() + " exist = " + file.exists());
		FileUtils.deleteDirectory(file);		
		System.out.println(file.getAbsolutePath() + " exist = " + file.exists());
		
	}

}
