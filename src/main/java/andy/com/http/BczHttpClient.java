package andy.com.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

class BczHttpClient {

    Logger logger = LoggerFactory.getLogger(BczHttpClient.class);
    private CloseableHttpClient client = null;
    private RequestConfig requestConfig = null;

    public BczHttpClient(int socketTimeoutMs, int connectTimeoutMs, int connectionRequestTimeoutMs) {
        PoolingHttpClientConnectionManager poolConnManager = new PoolingHttpClientConnectionManager();
        //最多多少个链接
        poolConnManager.setMaxTotal(100);
        //perroute 表示每个网站最多建立多少个链接，比如www.baidu.com建立一个链接。www.google.com建立一个链接
        poolConnManager.setDefaultMaxPerRoute(100);

        requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeoutMs)
                .setConnectTimeout(connectTimeoutMs)
                .setConnectionRequestTimeout(connectionRequestTimeoutMs)
                .build();

        client = HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(poolConnManager)
                // 设置请求配置
                .setDefaultRequestConfig(requestConfig)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();
    }

    public String post(String url, Map<String, String> params) {

        HttpEntity entity = null;
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.setParameter(key, value);
            }

            HttpPost request = new HttpPost(builder.build());
            addHeader(request);
            response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if (code != 200) {
                throw new Exception("trans failed: url=" + url + ";params=" + params);
            }

            entity = response.getEntity();
            String ret = EntityUtils.toString(entity);
            return ret;
        } catch (Exception e) {
            String error = "url=" + url + ";params" + params;
            logger.error(error, e);
        } finally {
            try {
                if(entity!=null)
                    EntityUtils.consume(entity);
            } catch (Exception e) {
                String error = "url=" + url + ";params" + params;
                logger.error(error, e);
            }

            try {
                if(response != null)
                    response.close();
            } catch (Exception e) {
                String error = "url=" + url + ";params" + params;
                logger.error(error, e);
            }
        }

        return null;
    }

    private void addHeader(HttpEntityEnclosingRequestBase request) {
        request.setConfig(requestConfig);
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
        request.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
        request.setHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
    }

    public static void main(String[] args){
        BczHttpClient client = new BczHttpClient(2,2,2);
        client.post("",null);
    }

    public void close(){
        try{
            client.close();
        }
        catch(Exception e){}
    }
}
