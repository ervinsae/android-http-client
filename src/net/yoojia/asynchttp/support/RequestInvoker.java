package net.yoojia.asynchttp.support;

import net.yoojia.asynchttp.ResponseCallback;


/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-10-23
 * @desc   : Http请求执行者，Http请求由此接口的实现类处理。
 */
public abstract class RequestInvoker implements Runnable{
	
//	public final static String METHOD_GET = "GET";
//	public final static String METHOD_POST = "POST";
//	
//	public final static String METHOD_PUT = "PUT";
//	public final static String METHOD_DELETE = "DELETE";
//	public final static String METHOD_OPTIONS = "OPTIONS";
//	public final static String METHOD_HEAD = "HEAD";
//	public final static String METHOD_TRACE = "TRACE";
//	public final static String METHOD_CONNECT = "CONNECT";
	
	public enum HttpMethod{
		GET,POST,PUT,DELETE,OPTIONS,HEAD,TRACE,CONNECT;
	}
	
	protected HttpMethod method;
	protected String url;
	protected ParamsWrapper params;
	protected ResponseCallback callback;
	protected Object token;

	public void init(HttpMethod method,String url,ParamsWrapper params,ResponseCallback callback){
		initWithToken(method, url, params, null, callback);
	}
	
	public void initWithToken(HttpMethod method,String url,ParamsWrapper params,Object token,ResponseCallback callback){
		this.method = method;
		this.url = url;
		this.params = params;
		this.callback = callback;
		this.token = token;
	}

	@Override
	public final void run() {
		executing();
	}
	
	protected abstract void executing();
	
}
