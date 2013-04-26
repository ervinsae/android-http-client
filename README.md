# Async Http Connection
=====================

 * a multithread callback-based async http connection library. it can be use on android project or general java project.
 * 一个基于回调机制的多线程异步Http连接库。它可用于Android项目或者一般Java项目。

## 适用
	
	Async HTTP Connection为简单的Http连接请求而设计。适用于API SDK等小数据传输项目，设计目标为Android项目。
	当前项目属性为Java项目，并使用JUnit4作为测试环境，主要是Android项目调试不方便。
	如果需要完善的功能，推荐一位国外大神的项目：[android-async-http](https://github.com/loopj/android-async-http)
	
---

## 特点

 * **简单** 提供POST和GET两个接口，通过扩展接口可设置PUT、DELETE方法。通过参数和回调接口完成整个Http连接的交互。
 * **轻量** 纯JDK实现，不依赖第三方Jar包。
 * **快速** 采用Executor多线程并发框架，秉承它的并发处理优势。
 * **可扩展** 框架提供Invoker扩展，通过实现RequestInvoker可方便的把HttpClient等优秀框架整合到项目中。

---

## 测试辅助

在多线程测试中，如果并发多条线程，则测试主线程需要等待所在测试子线程全部返回后才结束。一般采用两种方式：1、Thread.sleep()方式。2、CountDown计数方式。

所有问题的的解决方式都不只有一种，看谁的方式更好更——优雅而已。

比如：本框架内置多线程测试辅助工具组合 ResponseCallbackTrace + ThreadWaiter。可以利用简单的几个方法，就解决并发线程等待的问题。

### 多线程测试辅助的使用方法

使用方法很简单

1. 创建回调接口动态代理

```java

	protected final ResponseCallbackTrace callbackTrace = new ResponseCallbackTrace();

```

ResponseCallbackTrace 是一个动态代理实现类，它可以监听 ResponseCallback 的调用。并将线程执行情况记录在ThreadWaiter中。

1. 对回调接口添加代理

```java

	for(String url : urls){
			http.get(url, null,
					// 使用回调动态代理，可以自动处理等待其它线程返回的问题
					callbackTrace.trace(callback));
		}

```

1. 等待所有线程返回

```java

	//等待所有线程返回
	ThreadWaiter.waitingThreads();

```

### 完整的测试代码

```java

	public class UrlsTestCase{

	final static String[] AvailableUrls = {
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
	
	protected ResponseCallback callback = new ResponseCallback() {
		
		@Override
		public void onResponse(InputStream response,URL url) {
			System.out.println("[test GET] --> response back, url = "+url);
			Assert.assertNotNull(response);
		}
		
		@Override
		public void onError(Throwable exp) {
			Assert.fail(exp.getMessage());
			System.err.println("[test GET] --> response error");
		}

		@Override
		public void onSubmit(URL url,ParamsWrapper params) {}

		@Override
		public void onResponseWithToken(InputStream response, URL url, Object token) {}
	};

	final AsyncHttpConnection http = AsyncHttpConnection.getInstance();
	
	final ResponseCallbackTrace callbackTrace = new ResponseCallbackTrace();
	
	@Test
	public final void test(){
		
		testAllUrls(AvailableUrls);
		
		//等待所有线程返回
		ThreadWaiter.waitingThreads();
	}
	
	protected void testAllUrls(String[] urls){
		
		for(String url : urls){
			http.get(url, null,
					// 使用回调动态代理，可以自动处理等待其它线程返回的问题
					callbackTrace.trace(callback));
		}
	}

}

```

----

## 使用

默认使用SimpleHttpInvoker类执行Http连接，基于HttpUrlConnection实现。
提供两个接口实现类：BinaryResponseHandler、StringResponseHandler。分别处理二进制数据和字符数据。

更多例子见源目录的 **test** 目录

### 简单的例子

``` java
//使用Get方法，取得服务端响应流：
AsyncHttpConnection http = AsyncHttpConnection.getInstance();
ParamsWrapper params = ...;
String url = ...

// callback with InputStream

	int requestId = http.get(url, params, new ResponseCallback() {
		
		@Override
		public void onResponse(InputStream response,URL url) {
			System.out.println("[test GET] --> response back, url = "+url);
			Assert.assertNotNull(response);
		}
		
		@Override
		public void onError(Throwable exp) {
			System.err.println("[test GET] --> response error, url = "+url);
		}

		@Override
		public void onSubmit(URL url,ParamsWrapper params) {
		}
	});

// callback with String 

	int requestId2 = http.get(url, null, new StringResponseHandler() {
		
		@Override
		public void onSubmit(URL url,ParamsWrapper params) {
		}
		
		@Override
		public void onError(Throwable exp) {
			exp.printStackTrace();
			requestBack();
		}
		
		@Override
		public void onResponse(String content, URL url) {
			System.out.println("content->"+content);
			requestBack();
		}
	});

// callback with Binary 

	http.get(url, null, new BinaryResponseHandler() {
		
		@Override
		public void onSubmit(URL url, ParamsWrapper params) {
		}
		
		@Override
		public void onError(Throwable exp) {
			exp.printStackTrace();
			requestBack();
		}
		
		@Override
		public void onResponse(byte[] data, URL url) {
			final int size = 4465;
			Assert.assertEquals(size, data.length);
			System.out.println("Data size -> "+String.valueOf(data.length));
			requestBack();
		}
	});

```

``` java
//使用POST方法，取得服务端响应流：
AsyncHttpConnection http = AsyncHttpConnection.getInstance();
ParamsWrapper params = ...;
String url = ...

// callback with Stream

	int requestId = http.post(url, params, new ResponseCallback() {
		
		@Override
		public void onResponse(InputStream response,URL url) {
			System.out.println("[test POST] --> response back, url = "+url);
			Assert.assertNotNull(response);
			requestBack();
		}
		
		@Override
		public void onError(Throwable exp) {
			System.err.println("[test POST] --> response error, url = "+url);
			requestBack();
		}

		@Override
		public void onSubmit(URL url) {
		}
	});

// callback with String

	http.post(url, params, new StringResponseHandler() {
		@Override
		public void onSubmit(URL url,ParamsWrapper params) { 
			System.out.println(">> target: "+url.getHost()+" --> "+url.getPath());
		}
		@Override
		public void onError(Throwable exp) {
			exp.printStackTrace();
		}
		@Override
		public void onResponse(String content, URL url) {
			System.out.println("Return -> \n"+content);
		}

	});
```

### 更详细的例子 

``` java
//使用POST方法，取得服务端响应流：
AsyncHttpConnection http = AsyncHttpConnection.getInstance();
final int KEY_VAL = 24;
ParamsWrapper params = new ParamsWrapper();
params.put("firstname", "chen");
params.put("lastname", "yoca");
params.put("foo", KEY_VAL);
params.put("cookiename", KEY_VAL);
params.put("cookievalue", KEY_VAL);
int requestId = http.post(url, params, new StringResponseHandler() {

	@Override
	public void onSubmit(URL url) { 
		System.out.println(">> target: "+url.getHost()+" --> "+url.getPath());
	}

	@Override
	public void onError(Throwable exp) {
		requestBack();
		exp.printStackTrace();
	}

	@Override
	public void onResponse(String content, URL url) {
		Assert.assertNotNull(content);
		boolean containsKey = content.contains(String.valueOf(KEY_VAL));
		Assert.assertEquals(true, containsKey);
		requestBack();
	}
});

```

``` java
// 在大量并发的异步请求情况下，每个请求的回调可能需要一个标识码来标记这个回调结果。
// 有两种方式来解决这个问题：
// 1、使用get和post返回的RequestID来标识，但这需要对RequestID进行管理
// 2、使用get和post的token参数

AsyncHttpConnection http = AsyncHttpConnection.getInstance();
final int KEY_VAL = 24;
ParamsWrapper params = new ParamsWrapper();
params.put("firstname", "chen");
params.put("lastname", "yoca");
params.put("foo", KEY_VAL);
params.put("cookiename", KEY_VAL);
params.put("cookievalue", KEY_VAL);

//  ******** 利用token 接口 ************
Object token = "1234566";

// 如果调用了带token的方法，回调的方法将是onResponse(String content, URL url, Object token)
int requestId = http.post(url, params, token, new StringResponseHandler() {

	@Override
	public void onSubmit(URL url) { 
		System.out.println(">> target: "+url.getHost()+" --> "+url.getPath());
	}

	@Override
	public void onError(Throwable exp) {
		requestBack();
		exp.printStackTrace();
	}

	@Override
	public void onResponse(String content, URL url, Object token) {
		// token == "1234566" Token被传到这里作为标识
		Assert.assertNotNull(content);
		boolean containsKey = content.contains(String.valueOf(KEY_VAL));
		Assert.assertEquals(true, containsKey);
		requestBack();
	}
});

```

### HTTP Method

需要使用其它Http Mehtod，可以通过

	* sendRequest(String url,ParamsWrapper params,HttpMethod method,Object token,ResponseCallback callback)
	* sendRequest(String url,ParamsWrapper params,HttpMethod method, ResponseCallback callback)

接口调用。


---

## 开源协议

The code of this project is released under the Apache License 2.0, see [LICENSE](https://github.com/chenyoca/async-http-connection-core/blob/master/LICENSE)

