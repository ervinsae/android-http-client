package net.yoojia.test;

import net.yoojia.asynchttp.utility.ThreadWaiter;
import org.junit.Test;

/**
 * author : 桥下一粒砂 (chenyoca@gmail.com)
 * date   : 2013-06-06
 * 简单测试
 */
public class SimpleTestCase extends UrlsTestCase {

	@Test
	public final void testCallback(){

		ThreadWaiter.reset();

		for(String url : URLs){
			// 使用回调动态代理追踪回调执行情况，可以自动处理等待其它线程返回的问题
			http.get(url, null, callbackTrace.trace(callback));
		}

		//等待所有线程返回
		ThreadWaiter.waitingThreads();
	}

	@Test
	public final void testString(){

		ThreadWaiter.reset();

		for(String url : URLs){
			// 使用回调动态代理追踪回调执行情况，可以自动处理等待其它线程返回的问题
			http.get(url, null, callbackTrace.trace(stringCallback));
		}

		//等待所有线程返回
		ThreadWaiter.waitingThreads();
	}

	@Test
	public final void testBin(){

		ThreadWaiter.reset();

		for(String url : URLs){
			// 使用回调动态代理追踪回调执行情况，可以自动处理等待其它线程返回的问题
			http.get(url, null, callbackTrace.trace(binCallback));
		}

		//等待所有线程返回
		ThreadWaiter.waitingThreads();
	}
}
