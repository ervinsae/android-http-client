package net.yoojia.test;

import junit.framework.Assert;
import net.yoojia.asynchttp.AsyncHttpConnection;
import net.yoojia.asynchttp.BinaryResponseHandler;
import net.yoojia.asynchttp.ResponseCallback;
import net.yoojia.asynchttp.StringResponseHandler;
import net.yoojia.asynchttp.support.ParamsWrapper;
import net.yoojia.asynchttp.utility.ResponseCallbackTrace;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieStore;
import java.net.URL;


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
		"http://www.jrj.com.cn",
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
		public void onResponse(CookieStore cookieStore,InputStream response,URL url) {
			System.out.print("请求URL："+url+"  ");
			System.out.println("返回InputStream数据：" + url);
			Assert.assertNotNull(response);
		}

        @Override
        public void onUncatchedError(Throwable exp) {

        }

		@Override
		public void onConnectError (IOException exp) {
			Assert.fail(exp.getMessage());
		}

		@Override
		public void onStreamError (IOException exp) {
			Assert.fail(exp.getMessage());
		}

		@Override
		public void onSubmit(URL url,ParamsWrapper params) {
		}


	};
	
	protected final ResponseCallback binCallback = new BinaryResponseHandler() {
		
		@Override
		public void onSubmit(URL url, ParamsWrapper params) {
		}

		@Override
		public void onConnectError (IOException exp) {
			Assert.fail(exp.getMessage());
		}

		@Override
		public void onStreamError (IOException exp) {
			Assert.fail(exp.getMessage());
		}

		@Override
		public void onResponse(byte[] data, URL url) {
			Assert.assertNotNull(data);
			System.out.print("请求URL："+url+"  ");
			System.out.println("返回Binary数据长度："+data.length);
		}
	};
	
	protected final ResponseCallback stringCallback = new StringResponseHandler() {
		
		@Override
		public void onSubmit(URL url, ParamsWrapper params) {
		}

		@Override
		public void onConnectError (IOException exp) {
			Assert.fail(exp.getMessage());
		}

		@Override
		public void onStreamError (IOException exp) {
			Assert.fail(exp.getMessage());
		}

		@Override
		public void onResponse(String content, URL url) {
			Assert.assertNotNull(content);
			System.out.print("请求URL："+url+"  ");
			System.out.println("返回String数据长度："+content.length());
		}
		
	};

	protected final AsyncHttpConnection http = AsyncHttpConnection.getInstance();
	
	protected final ResponseCallbackTrace callbackTrace = new ResponseCallbackTrace();

	
}
