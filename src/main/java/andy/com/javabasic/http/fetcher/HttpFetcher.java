package andy.com.javabasic.http.fetcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import andy.com.javabasic.http.bean.FetchResult;
import andy.com.javabasic.http.bean.HttpFetchResult;

public class HttpFetcher implements Fetcher{
	 	
	
	private Map<String,String> httpHeaders = new HashMap<String,String>();
	
	private ResponseHandler<HttpFetchResult> responseHandler = new ResponseHandler<HttpFetchResult>(){

		@Override
		public HttpFetchResult handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

			HttpFetchResult hfr = new HttpFetchResult();
            int status = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            byte [] content = EntityUtils.toByteArray(entity);                
            hfr.setContent(content);
            hfr.setHttpStatus(status);
            return hfr;
		}
		
	};
	
	
	//代理
	private String proxyHost = null;
	private int    proxyPort = -1;
	private String proxyProtocal = "http";
	
	
	//timeout
	private int socketTimeout = 10000;
	private int connectTimeout = 10000;
	private int connectionRequestTimeout = 10000;

    
	public void setProxy(String proxyHost, int proxyPort, String proxyProtocal)
	{
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
		this.proxyProtocal = proxyProtocal;
	}
	
	public HttpFetcher() {
		initHeaders(null);
	}
	
	public HttpFetcher(Map<String,String> headers) {
		initHeaders(headers);
	}
	
	
	
	private void initHeaders(Map<String,String> headers )
	{
		if(headers == null ){
			httpHeaders.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");		      
			httpHeaders.put("Accept-Language", "zh-cn,zh;q=0.5");
			httpHeaders.put("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
		}
	}
	
	 
	
	@Override
	public FetchResult fetch(String url) throws Exception{
		
		
		HttpClientBuilder httpClientBuilder =  HttpClients.custom(); 
		CloseableHttpClient httpClient = null;
		try{
			
			HttpGet request = new HttpGet(url);			    
            httpClient = httpClientBuilder.build();
            
            
			//如果设置了headers使用设置的header
			if (this.httpHeaders != null)
			{
				for(Map.Entry<String, String> entry: httpHeaders.entrySet())
				request.setHeader(entry.getKey(), entry.getValue());
			}
			
			//如果设置了proxy就用proxy
			HttpHost proxy = null;
			if (!StringUtils.isBlank(proxyHost))
			{
	            proxy = new HttpHost(this.proxyHost, this.proxyPort, this.proxyProtocal);
 
			}
			
		
		   Builder builder = RequestConfig.custom();
		   if (proxy != null)
			  builder.setProxy(proxy);
		  
		   if(this.connectTimeout > 0)
			  builder.setConnectTimeout(this.connectTimeout);
		  
		   if(this.socketTimeout > 0)
			  builder.setSocketTimeout(socketTimeout);
		  
		   if(this.connectionRequestTimeout > 0 )
			  builder.setConnectionRequestTimeout(this.connectionRequestTimeout);
	   
	       RequestConfig config = builder.build();     	          
	       request.setConfig(config);
	        
            
	        //HttpFetchResult ret = httpClient.execute(get, responseHandler);
			HttpFetchResult ret = httpClient.execute(request, responseHandler,null);
			ret.setUrl(url);
            return  ret;		
		}
		finally
		{
			httpClient.close();
		}
	}

	public ResponseHandler<HttpFetchResult> getResponseHandler() {
		return responseHandler;
	}

	public void setResponseHandler(ResponseHandler<HttpFetchResult> responseHandler) {
		this.responseHandler = responseHandler;
	}
	
	
	
	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyProtocal() {
		return proxyProtocal;
	}

	public void setProxyProtocal(String proxyProtocal) {
		this.proxyProtocal = proxyProtocal;
	}

	
	
	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	public final static void main(String [] args) throws Exception
	{
		//String url = "http://blog.csdn.net/sin90lzc/article/details/7543262";
		String url = "http://www.oxfordlearnersdictionaries.com/definition/english/culminate?q=culminate";
		HttpFetcher hf = new HttpFetcher();
		
		if(true)
		{
			hf.setProxy("120.92.3.127", 80, "http");
		}	
		
		HttpFetchResult hfr = (HttpFetchResult) hf.fetch(url);		
		System.out.println(new String(hfr.getContent()));	
	}
}
