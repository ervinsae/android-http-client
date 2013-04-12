package com.lurencun.http.test;

import java.net.URL;

import net.yoojia.asynchttp.BinaryResponseHandler;
import net.yoojia.asynchttp.support.ParamsWrapper;


/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-11-7
 * @desc   : TODO
 */
public class BinaryDataTestCase extends BaseTeseCase {

	@Override
	protected String[] loadUrls() {
		return new String[]{"http://127.0.0.1:8080/stores"};
	}

	@Override
	protected void sendGetRequest(String url) {
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
				System.out.println("返回数据 -> "+new String(data));
				requestBack();
			}
		});
		
	}
	
}
