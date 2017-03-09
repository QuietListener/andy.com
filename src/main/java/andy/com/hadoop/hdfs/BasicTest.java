package andy.com.hadoop.hdfs;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class BasicTest {

	static FileSystem fs = null;
	static {
		String root = "hdfs://localhost:9000/";
		Configuration conf = new Configuration();
		try {
			fs = FileSystem.get(URI.create(root), conf);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, IOException {
		String from = args[0];
		String to = args[1];
		save_file(from, to);
		
		to = "hdfs://localhost:9000/"+to;
		status(to);
		//echo_file(to);
		delete(to);
	}
	
	/**
	 * 
	 * @param file_name
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void save_file(String from, String to) throws IllegalArgumentException, IOException
	{
		System.out.println("save file :" + to );
		long t1 = System.currentTimeMillis();
		InputStream in = null;
        try {
        	Path fromp = new Path(from);
        	in = new FileInputStream(from);
        	FSDataOutputStream out = fs.create(new Path(to));
        	IOUtils.copyBytes(in, out, 1024, true);
        }
        finally 
        {
            IOUtils.closeStream(in);
        }
        System.out.println("use:" + (System.currentTimeMillis() - t1) +" \n\n");
	}
	
	/**
	 * 读取一个文件
	 * @param path
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static void echo_file(String path) throws IllegalArgumentException, IOException
	{
		System.out.println("echo file :" + path );
		long t1 = System.currentTimeMillis();
		InputStream in = null;
        try {
        	Path p = new Path(path);
            in = fs.open(p);
            //IOUtils.copyBytes(in, System.out, 4096, false);
        }
        finally 
        {
            IOUtils.closeStream(in);
        }
        System.out.println("use:" + (System.currentTimeMillis() - t1) +" \n\n");
	}
	
	/**
	 * 删除
	 * @param path
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static void delete(String path) throws IllegalArgumentException, IOException
	{
		System.out.println("delete file :" + path );
		long t1 = System.currentTimeMillis();
	    try {
        	Path p = new Path(path);
            fs.delete(p);
        }
        finally 
        {          
        }
	    System.out.println("use:" + (System.currentTimeMillis() - t1) +" \n\n");
	}
	
	
	/**
	 * 删除
	 * @param path
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static void status(String path) throws IllegalArgumentException, IOException
	{
		long t1 = System.currentTimeMillis();
		System.out.println("status :" + path );
		InputStream in = null;
        try {
        	Path p = new Path(path);
            FileStatus status = fs.getFileStatus(p);
            System.out.println(status);   
        }
        finally 
        {
            IOUtils.closeStream(in);
        }
        
        System.out.println("use:" + (System.currentTimeMillis() - t1) +" \n\n");
	}
	
}
