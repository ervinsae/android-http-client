package net.yoojia.test;

import java.io.InputStream;
import java.net.URL;

import junit.framework.Assert;
import net.yoojia.asynchttp.AsyncHttpConnection;
import net.yoojia.asynchttp.BinaryResponseHandler;
import net.yoojia.asynchttp.ResponseCallback;
import net.yoojia.asynchttp.StringResponseHandler;
import net.yoojia.asynchttp.support.ParamsWrapper;
import net.yoojia.asynchttp.utility.ResponseCallbackTrace;
import net.yoojia.asynchttp.utility.ThreadWaiter;

import org.junit.Test;


/**
 * author : 桥下一粒砂
 * e-mail : chenyoca@gmail.com
 * date   : 2012-10-22
 * desc   : TODO
 */
public class UrlsTestCase{

	final static String[] URLs = {
		"http://www.lurencun.com",
		"http://www.163.com",
		"http://www.126.com",
		"http://www.google.com.hk",
		"http://www.qq.com",
		"http://www.ifeng.com",
		"http://www.renren.com",
		"http://www.58.com",
		"http://www.psbc.com",
		"http://www.suning.com",
		"http://www.jumei.com",
		"http://www.xiu.com",
		"http://www.letv.com",
		"http://www.miercn.com",
		"http://www.jrj.com",
		"http://www.suning.com",
		"http://www.9ku.com",
		"http://www.jumei.com",
		"http://www.ctrip.com",
		"http://www.tuniu.com",
		"http://www.icbc.com",
		"http://www.ccb.com",
		"http://www.gexing.com",
		"http://www.nipic.com",
		"http://www.jxedt.com",
		"http://www.iqiyi.com",
		"http://www.skycn.com",
		"http://www.7k7k.com",
	};
	
	protected final ResponseCallback callback = new ResponseCallback() {
		
		@Override
		public void onResponse(InputStream response,URL url) {
			System.out.println("返回InputStream数据："+url);
			Assert.assertNotNull(response);
		}
		
		@Override
		public void onError(Throwable exp) {
			Assert.fail(exp.getMessage());
		}

		@Override
		public void onSubmit(URL url,ParamsWrapper params) {}

		@Override
		public void onResponseWithToken(InputStream response, URL url, Object token) {
			Assert.assertNotNull(token);
			System.out.println("返回Token数据："+token);
		}
	};
	
	protected final ResponseCallback binCallback = new BinaryResponseHandler() {
		
		@Override
		public void onSubmit(URL url, ParamsWrapper params) { }
		
		@Override
		public void onError(Throwable exp) { 
			Assert.fail(exp.getMessage());
		}
		
		@Override
		public void onResponse(byte[] data, URL url) {
			Assert.assertNotNull(data);
			System.out.println("返回Binary数据长度："+data.length);
		}
	};
	
	protected final ResponseCallback stringCallback = new StringResponseHandler() {
		
		@Override
		public void onSubmit(URL url, ParamsWrapper params) { }
		
		@Override
		public void onError(Throwable exp) {
			Assert.fail(exp.getMessage());
		}
		
		@Override
		public void onResponse(String content, URL url) {
			Assert.assertNotNull(content);
			System.out.println("返回String数据长度："+content.length());
		}
	};

	protected final AsyncHttpConnection http = AsyncHttpConnection.getInstance();
	
	protected final ResponseCallbackTrace callbackTrace = new ResponseCallbackTrace();
	
	@Test
	public final void testCallback(){
		
		for(String url : URLs){
			// 使用回调动态代理追踪回调执行情况，可以自动处理等待其它线程返回的问题
			http.get(url, null, callbackTrace.trace(callback));
		}
		
		//等待所有线程返回
		ThreadWaiter.waitingThreads();
	}
	
	@Test
	public final void testString(){
		for(String url : URLs){
			// 使用回调动态代理追踪回调执行情况，可以自动处理等待其它线程返回的问题
			http.get(url, null, callbackTrace.trace(stringCallback));
		}
		
		//等待所有线程返回
		ThreadWaiter.waitingThreads();
	}
	
	@Test
	public final void testBin(){
		for(String url : URLs){
			// 使用回调动态代理追踪回调执行情况，可以自动处理等待其它线程返回的问题
			http.get(url, null, callbackTrace.trace(binCallback));
		}
		
		//等待所有线程返回
		ThreadWaiter.waitingThreads();
	}
	
	@Test
	public void testToken(){
		
		for(String url : URLs){
			Object token = url;
			// 使用回调动态代理追踪回调执行情况，可以自动处理等待其它线程返回的问题
			http.get(url, null,token, callbackTrace.trace(callback));
		}
		
		//等待所有线程返回
		ThreadWaiter.waitingThreads();
	}

}
