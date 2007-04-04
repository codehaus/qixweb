package org.qixweb.block;

import java.lang.reflect.Method;

public class CallGetter implements Function
{
	private String itsMethodToCall;

	public CallGetter(String methodToCall)
	{
		itsMethodToCall = methodToCall;
	}

	public Object eval(Object each)
	{
		Throwable raisedException = null;
		try
		{
			Method methodToCall = each.getClass().getMethod(itsMethodToCall, new Class[0]);
			return methodToCall.invoke(each, new Object[0]);
		}
		catch (Throwable t)
		{
			raisedException = t;
		}
		throw new RuntimeException(raisedException);
	}
}
