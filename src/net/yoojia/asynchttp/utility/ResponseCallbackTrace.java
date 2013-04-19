package net.yoojia.asynchttp.utility;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.yoojia.asynchttp.ResponseCallback;

public class ResponseCallbackTrace implements InvocationHandler {
	
	private Object object;       
	
    public ResponseCallback trace(Object object){         
        this.object = object;
        Class<?>[] interfaces = new Class<?>[]{ResponseCallback.class};
        return (ResponseCallback)Proxy.newProxyInstance(object.getClass().getClassLoader(), interfaces,this);         
    }

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		String methodName = method.getName();
		Object result = method.invoke(object, args);
		if(methodName.equalsIgnoreCase("onsubmit")){
			ThreadWaiter.threadStarted();
		}else if(methodName.equalsIgnoreCase("onerror")){
			ThreadWaiter.threadFinished();
		}else if(methodName.equalsIgnoreCase("onresponse")){
			ThreadWaiter.threadFinished();
		}else if(methodName.equalsIgnoreCase("onresponsewithtoken")){
			ThreadWaiter.threadFinished();
		}
		return result;
	}

}
