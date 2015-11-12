package andy.com.javabasic.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import com.google.gson.Gson;

@SuppressWarnings("deprecation")
public class PushClient
{
	public static final int GET = 0;
	public static final int POST = 1;
	
	private static MultiThreadedHttpConnectionManager connectionManager = null;
	private static int maxConPerHost = 20;
	private static int conTimeOutMs = 2000;
	private static int maxTotalCon = 100;
	
	static {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire.header", "debug");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "debug");
		
		connectionManager = new MultiThreadedHttpConnectionManager();;
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setDefaultMaxConnectionsPerHost(maxConPerHost);
		params.setConnectionTimeout(conTimeOutMs);
		params.setMaxTotalConnections(maxTotalCon);
	}
	//private int soTimeOutMs = ;
	//private int maxSize;
	
	
	private PushClient() {
		
		//params.setSoTimeout(soTimeOutMs);
	}
	
	public static PushClient getInstance()
	{
		return new PushClient();
	}
	
	public Map<String,String> postMultiPart(String url, Map<String,Object> params) throws Exception
	{
		print("request:");
		print("post url:"+url);
		Map<String,String> rets = new HashMap<>();
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.addHeader(new BasicHeader("Cookie","JSESSIONID=10i3mkqba33goa53pervq2p2e; old_user=1; _bigbang_editor_session=BAh7DEkiD3Nlc3Npb25faWQGOgZFRkkiJWRkOWIzNmE5M2VmMzc0ZTM5OGJiMTE1YjEyN2IxODZkBjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMTluYmdKVEpQM014dUVHWG56MndvTDJWVUI4VWlOcG9CZ2RXYUQ5NlpxZjQ9BjsARkkiDHVzZXJfaWQGOwBGaWNJIg51c2VyX25hbWUGOwBGSSIJdGVzdAY7AFRJIg51c2VyX3JvbGUGOwBGSSIKYWRtaW4GOwBUSSINdXNlcl90YWcGOwBGaQBJIgpmbGFzaAY7AEZvOiVBY3Rpb25EaXNwYXRjaDo6Rmxhc2g6OkZsYXNoSGFzaAk6CkB1c2VkbzoIU2V0BjoKQGhhc2h7BjoLbm90aWNlVDoMQGNsb3NlZEY6DUBmbGFzaGVzewY7CkkiAAY7AFQ6CUBub3cw--ed3ce54d207eb2c5b155977117ebc15c0ad03698")); 
		
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("utf-8"));
		if(params != null)
		{
			for(Map.Entry<String, Object> entry : params.entrySet())
			{
				String key = entry.getKey();
				Object value = entry.getValue();
				
				if(String.class.isInstance(value))
				{
					StringBody stringBody = new StringBody((String)value,Charset.forName("UTF-8"));   
					entity.addPart(key, stringBody);  	
				}
				else if(File.class.isInstance(value))
				{
					FileBody fileBody = new FileBody((File)value); 
					
				    entity.addPart(key, fileBody);  	
				}				
				print("param:key = "+key+" value="+value.toString());
			}
		}

		post.setEntity(entity);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		HttpResponse response = httpclient.execute(post);
		BufferedReader reader =  new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		String ret = "";
	    String line = reader.readLine();
		
		ret+=line;
		while(!StringUtils.isBlank(line))
		{
			line = reader.readLine();
			if(!StringUtils.isBlank(line))	
			 ret+=line;
		}
		print(ret);
		int statusCode = response.getStatusLine().getStatusCode();
		rets.put("status",statusCode+"");
		rets.put("data",ret);
		
		return rets;
	}
	
	private PostMethod getPostMethod(String url, Map<String, String> params,String charset)
	{
		print("request:");
		print("post url:"+url);
		
		PostMethod  postMethod = new UTF8PostMethod(url);
		if(params!=null)
		{
			for(Map.Entry<String, String> entry : params.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				postMethod.addParameter(key, value);			
			}
		}
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		return postMethod;	
	}
	
	
	private GetMethod getGetMethod(String url, Map<String, String> params,String charset)
	{
		
		print("request:");
		print("request url:"+url);

		if(params!=null)
		{
			for(Map.Entry<String, String> entry : params.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				
				if(url.indexOf("?") == -1)
					url+="?"+key+"="+value;
				else
					url+="&"+key+"="+value+"";			
			}
		}
		
		GetMethod  getMethod = new GetMethod(url);
		return getMethod;	
	}
	
	private static HttpClient getHttpClient() 
	{		
		HttpClientParams clientParams = new HttpClientParams();
		// 忽略cookie 避免 Cookie rejected 警告
		clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		HttpClient client = new HttpClient(clientParams,connectionManager);
		client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		return client;	
	}
	
	
	public int Push(String url, Map<String, String> params, int method)
	{
		HttpMethod httpMethod = null;
		if(method == GET)
		{
			httpMethod = getGetMethod(url, params, "utf-8");
		}
		else if(method == POST)
		{
			httpMethod = getPostMethod(url, params, "utf-8");
		}
		else 
		{
			return -1;
		}
		
		
		HttpClient client = getHttpClient();
		try {
			client.executeMethod(httpMethod);
			int statusCode = client.executeMethod(httpMethod);
			
			if (statusCode != HttpStatus.SC_OK) 
			{  	          
				print("Method failed: " + httpMethod.getStatusLine());  
	        }  
			return statusCode;
		} catch (HttpException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return -1;
		
	}
	
	public int push(String url, Map<String, String> params, int method)
	{
		print("in push:"+params.toString());
		HttpMethod httpMethod = null;
		if(method == GET)
		{
			httpMethod = getGetMethod(url, params, "utf-8");
		}
		else if(method == POST)
		{
			httpMethod = getPostMethod(url, params, "utf-8");
		}
		else 
		{
			return -1;
		}
		
		
		HttpClient client = getHttpClient();
		try {
			print("push_client"+httpMethod.getQueryString());
			int statusCode = client.executeMethod(httpMethod);
			
			if (statusCode != HttpStatus.SC_OK) 
			{  	          
				print("request :Method failed: " + httpMethod.getStatusLine());  
	        }  
			else
			{
				print("request:Method success: " + httpMethod.getStatusLine()); 
			}
			
			print("response:"  + httpMethod.getResponseBodyAsString());  
			
			return statusCode;
		} catch (HttpException e) {
			e.printStackTrace();
			print("exception:"+e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			print("exception:"+e.toString());
		}
		return -1;	
		
	}
	
	
	
	
	private static class UTF8PostMethod extends PostMethod { 
		public UTF8PostMethod(String url) { 
		super(url); 
		} 

		@Override 
		public String getRequestCharSet() { 
		//return super.getRequestCharSet(); 
		return "UTF-8"; 
		} 
	}
	
	public static void main(String [] args)
	{
		
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");
		//String url = "http://talent.baicizhan.com/services/labels";
		String url = "http://112.124.4.53/words/push_talent_data_test";
//		HttpClient client = getHttpClient();
//		GetMethod getMethod = getGetMethod(url, null, "utf8");
//		try {
//			int statusCode = client.executeMethod(getMethod);
//			if (statusCode != HttpStatus.SC_OK) 
//			{  	          
//				System.err.ln("Method failed: " + getMethod.getStatusLine());  
//	        }  
//	           
//	         /*获得返回的结果*/  
//	         byte[] responseBody = getMethod.getResponseBody();  
//	         System.out.ln("getMethod:");
//	         System.out.ln(statusCode+":"+new String(responseBody));  
//	       
//		} catch (HttpException e) {
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
//		
//		
//		PostMethod postMethod = getPostMethod(url, null, "utf8");
//		try {
//			int statusCode = client.executeMethod(postMethod);
//			if (statusCode != HttpStatus.SC_OK) 
//			{  	          
//				System.err.ln("Method failed: " + postMethod.getStatusLine());  
//	        }  
//	           
//	         /*获得返回的结果*/  
//	         byte[] responseBody = postMethod.getResponseBody();  
//	         System.out.ln("postMethod:");
//	         System.out.ln(statusCode+":"+new String(responseBody));  
//		} catch (HttpException e) {
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}	
		Map<String, String> params = new HashMap<String,String>();
		params.put("topic_word", "from junjun");
		params.put("sequence",  "{type:1, question:-1, score:0},{type:4, question:3, score:1}=-=-=");
		
		String gson = new Gson().toJson(params);
		Map<String, String> params4server = new HashMap<String,String>();
		params4server.put("data", gson);
		
	
		PushClient.getInstance().push(url, params4server, PushClient.POST);
		//push(url, null, POST);
	}
	
	private void print(String str)
	{
		System.out.println(str);
	}
}
