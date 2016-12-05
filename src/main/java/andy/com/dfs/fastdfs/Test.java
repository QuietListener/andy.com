package andy.com.dfs.fastdfs;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;


public class Test {

	public static  String upload(String file,TrackerClient tracker ) throws IOException, MyException
	{
		TrackerServer trackerServer = null;
		try
		{
			trackerServer = tracker.getConnection();
	  		StorageServer storageServer = tracker.getStoreStorage(trackerServer);
	  		
	  		if (storageServer == null)
	  		{
	  			System.out.println("getStoreStorage fail, error code: " + tracker.getErrorCode());
	  			return null;
	  		}
	  		
	  		NameValuePair [] meta_list = new NameValuePair[4];
	  		meta_list[0] = new NameValuePair("width", "800");
	  		meta_list[1] = new NameValuePair("heigth", "600");
	  		meta_list[2] = new NameValuePair("bgcolor", "#FFFFFF");
	  		meta_list[3] = new NameValuePair("author", "Mike");
	  		
	  		byte[] bytes = FileUtils.readFileToByteArray(new File(file));
	  		
	  		StorageClient1 client = new StorageClient1(trackerServer, storageServer);
	  		
	  		System.out.println("uploading: " + file);
	  		String file_id = client.upload_file1(bytes, "txt", meta_list);	  		
	  		return file_id;
		}
  		finally{
  			if (trackerServer!=null)
  				trackerServer.close();
  		}
	}
	

	public static  File download(String file_id,String file, TrackerClient tracker ) throws IOException, MyException, Exception
	{
		TrackerServer trackerServer = null;
		try
		{
			trackerServer = tracker.getConnection();
	  		StorageServer storageServer = tracker.getStoreStorage(trackerServer);
	  		StorageClient1 client = new StorageClient1(trackerServer, storageServer);
	  		System.out.println("downloading: " + file_id);  		
	  		byte[] ret = client.download_file1(file_id);
	  		if(ret == null)
	  		{
	  			throw new Exception("fail to download "+file_id+"");
	  		}
	  		
	  		File f = new File(file);
	  		FileUtils.writeByteArrayToFile(f, ret);
	  		return  f;
		}
  		finally{
  			if (trackerServer!=null)
  				trackerServer.close();
  		}
	}
	
	public static void main(String[] args) {
		
		String conf_filename = "/src/main/resources/fdfs_client.conf";
		TrackerClient tracker = null;
		String baseDir = System.getProperty("user.dir"); 
		System.out.println(baseDir);
		
		try {
			//初始化
			ClientGlobal.init(baseDir+"/"+conf_filename);
			
			//tracker的客户端
			tracker = new TrackerClient();
			
			String fileName = "res/star.mp3";
			String download_file = "star_download.mp3";
			
			//测试上传
			String file_id = upload(baseDir+fileName, tracker);
			System.out.println("file_id: " + file_id +"("+fileName+")");
			
			//测试下载
			if(!StringUtils.isBlank(file_id))
			{
				download(file_id,baseDir+"/"+download_file, tracker);
			}
			
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
