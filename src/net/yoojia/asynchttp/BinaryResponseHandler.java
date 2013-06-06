package net.yoojia.asynchttp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.yoojia.asynchttp.utility.StreamUtility;

/**
 * @author : 桥下一粒砂 chenyoca@gmail.com
 * date   : 2012-10-24
 * desc   : 字节型数据处理类
 */
public abstract class BinaryResponseHandler implements ResponseCallback {

	@Override
	final public void onResponse(InputStream response,URL url) {
		byte[] data = null;
		try {
			data = StreamUtility.convertToByteArray(response);
		} catch (IOException exp) {
			onSteamError(exp);
			exp.printStackTrace();
		} finally{
			StreamUtility.closeSilently(response);
		}
		onResponse(data,url);
	}

	public abstract void onResponse(byte[] data,URL url);
	
}
