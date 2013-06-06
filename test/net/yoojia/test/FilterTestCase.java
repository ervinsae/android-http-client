package net.yoojia.test;

import net.yoojia.asynchttp.support.RequestInvoker;
import net.yoojia.asynchttp.support.RequestInvokerFilter;
import net.yoojia.asynchttp.utility.ThreadWaiter;
import org.junit.Test;

/**
 * author : 桥下一粒砂 (chenyoca@gmail.com)
 * date   : 2013-06-06
 * TODO
 */
public class FilterTestCase extends UrlsTestCase {

	public void initFilter(){

		http.setRequestInvokerFilter(new RequestInvokerFilter() {
			@Override
			public void onRequestInvoke (RequestInvoker requestInvoker) {
				requestInvoker.setHttpSocketTimeout(30*1000);
			}
		});

	}

	@Test
	public final void testString(){

		initFilter();

		ThreadWaiter.reset();

		for(String url : URLs){
			// 使用回调动态代理追踪回调执行情况，可以自动处理等待其它线程返回的问题
			http.get(url, null, callbackTrace.trace(stringCallback));
		}

		//等待所有线程返回
		ThreadWaiter.waitingThreads();
	}
}
