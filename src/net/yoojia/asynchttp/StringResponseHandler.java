package net.yoojia.asynchttp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.yoojia.asynchttp.utility.StreamUtility;


/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-10-23
 * @desc   : 字符型响应处理类
 */
public abstract class StringResponseHandler implements ResponseCallback {

	@Override
	final public void onResponse(InputStream response,URL url) {
		String data = null;
		try {
			data = StreamUtility.convertToString(response);
		} catch (IOException exp) {
			onSteamError(exp);
			exp.printStackTrace();
		} finally{
			StreamUtility.closeSilently(response);
		}
		onResponse(data,url);
	}

	protected abstract void onResponse(String content,URL url);
	
}
