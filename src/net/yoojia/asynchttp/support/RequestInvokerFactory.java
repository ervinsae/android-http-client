package net.yoojia.asynchttp.support;

import net.yoojia.asynchttp.ResponseCallback;
import net.yoojia.asynchttp.support.RequestInvoker.HttpMethod;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-10-23
 * @desc   : Invoker实现类创建工厂
 */
public class RequestInvokerFactory {
	
	private static Class<? extends RequestInvoker> InvokerType = SimpleHttpInvoker.class;
	
	public static RequestInvoker obtain(HttpMethod method,String url,ParamsWrapper params,ResponseCallback callback){
		return obtain(method, url, params, null, callback);
	}
	
	public static RequestInvoker obtain(HttpMethod method,String url,ParamsWrapper params,Object token,ResponseCallback callback){
		RequestInvoker invoker = null;
		if(!InvokerType.equals(SimpleHttpInvoker.class)){
			try {
				invoker = InvokerType.newInstance();
			} catch (Exception exp) {
				System.err.println(String.format("Cannot instance from %s, used default HttpUrlInvoker",InvokerType.getName()));
				invoker = new SimpleHttpInvoker();
			}
		}else{
			invoker = new SimpleHttpInvoker();
		}
		invoker.initWithToken(method, url, params, token, callback);
		return invoker;
	}
	
	/**
	 * 向工厂注册一个Invoker实现类
	 * @param clazz
	 */
	public static void register(Class<? extends RequestInvoker> clazz){
		InvokerType = clazz;
	}
	
}
