package net.yoojia.test;

import net.yoojia.asynchttp.utility.ThreadWaiter;
import org.junit.Test;

/**
 * author : 桥下一粒砂 (chenyoca@gmail.com)
 * date   : 2013-10-21
 * 测试Cookies
 */
public class CookieTestCase extends UrlsTestCase {

    @Test
    public final void testCallback(){

        ThreadWaiter.reset();

        http.get("http://xtznxj.eicp.net", null, callbackTrace.trace(stringCallback));

        //等待所有线程返回
        ThreadWaiter.waitingThreads();
    }

}
