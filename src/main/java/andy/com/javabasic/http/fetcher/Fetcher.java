package andy.com.javabasic.http.fetcher;

import andy.com.javabasic.http.bean.FetchResult;

public interface Fetcher {
	public FetchResult fetch(String url) throws Exception;
}
