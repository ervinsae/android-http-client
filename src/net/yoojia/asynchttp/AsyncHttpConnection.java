package net.yoojia.asynchttp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.yoojia.asynchttp.support.ParamsWrapper;
import net.yoojia.asynchttp.support.RequestInvoker;
import net.yoojia.asynchttp.support.RequestInvoker.HttpMethod;
import net.yoojia.asynchttp.support.RequestInvokerFactory;
import net.yoojia.asynchttp.support.RequestInvokerFilter;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-10-22
 * @desc   : An asynchronous multithread http connection framework. 
 */
public class AsyncHttpConnection {

	public static final String VERSION = "1.2.0";
	static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors()*2;
	static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	
	private AsyncHttpConnection(){}
	private static class SingletonProvider {
		private static AsyncHttpConnection instance = new AsyncHttpConnection();
	}
	public static AsyncHttpConnection getInstance(){
		return SingletonProvider.instance;
	}

	private RequestInvokerFilter requestInvokerFilter;

	public void setRequestInvokerFilter (RequestInvokerFilter requestInvokerFilter) {
		this.requestInvokerFilter = requestInvokerFilter;
	}

	/**
	 * Send a 'get' request to url without params.
	 * @param url
	 * @param callback
	 */
	public void get(String url,ResponseCallback callback){
		get(url, null, callback);
	}
	
	/**
	 * Send a 'get' request to url with params, response on callback.
	 * @param url
	 * @param params
	 * @param callback
	 * @return request id
	 *
	 */
	public void get(String url,ParamsWrapper params,ResponseCallback callback){
		verifyUrl(url);
		sendRequest(url, params, HttpMethod.GET, callback);
	}
	
	/**
	 * Send a 'post' request to url without params.
	 * @param url
	 * @param callback
	 */
	public void post(String url,ResponseCallback callback){
		post(url, null, callback);
	}
	
	/**
	 * Send a 'post' request to url with params, response on callback.
	 * @param url
	 * @param params
	 * @param callback
	 * @return request id
	 *
	 */
	public void post(String url,ParamsWrapper params,ResponseCallback callback){
		verifyUrl(url);
		sendRequest(url, params, HttpMethod.POST, callback);
	}
	
	private void verifyUrl(String url){
		if(url == null) throw new IllegalArgumentException("Connection url cannot be null");
	}
	
	/**
	 * send request to url with params,method and callback, without token.
	 * @param url
	 * @param params
	 * @param method
	 * @param callback
	 */
	public void sendRequest(String url,ParamsWrapper params,HttpMethod method, ResponseCallback callback){
		if(url == null) return;
		RequestInvoker invoker = RequestInvokerFactory.obtain(method, url, params, callback);
		if( requestInvokerFilter != null ) requestInvokerFilter.onRequestInvoke(invoker);
		THREAD_POOL.submit(invoker);
	}

}
