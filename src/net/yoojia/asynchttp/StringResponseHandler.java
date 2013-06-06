package net.yoojia.asynchttp;

import net.yoojia.asynchttp.utility.StreamUtility;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * @author : 桥下一粒砂 chenyoca@gmail.com
 * date   : 2012-10-23
 * 字符型响应处理类
 */
public abstract class StringResponseHandler implements ResponseCallback {

	@Override
	final public void onResponse(InputStream response,URL url) {
		String data = null;
		try {
			data = StreamUtility.convertToString(response);
		} catch (IOException exp) {
			onStreamError(exp);
			exp.printStackTrace();
		} finally{
			StreamUtility.closeSilently(response);
		}
		onResponse(data,url);
	}

	protected abstract void onResponse(String content,URL url);
	
}
