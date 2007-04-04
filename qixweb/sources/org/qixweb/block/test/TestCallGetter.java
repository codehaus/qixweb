package org.qixweb.block.test;

import java.lang.reflect.InvocationTargetException;

import org.qixweb.block.CallGetter;

import junit.framework.TestCase;



public class TestCallGetter extends TestCase
{
	private ClassToCall objectToCall;

    public class ClassToCall
	{
		public String methodToCall()
		{
			return "hello world";
		}
        public void methodThatGeneratesSecurityException()
        {
            throw new SecurityException();
        }
	}
	
    protected void setUp() 
    {
        objectToCall = new ClassToCall();
    }

    public void testCallMethod()
	{
		CallGetter callGetter = new CallGetter("methodToCall");
		assertEquals("Should call the method", objectToCall.methodToCall(), callGetter.eval(objectToCall));
	}
	
	public void testThrowRuntimeExceptionCallingNotExistentMethod()
	{
        checkCallingGeneratesRuntimeThatWraps("notExistentMethodToCall", NoSuchMethodException.class);
	}

    private Throwable checkCallingGeneratesRuntimeThatWraps(String methodName, Class wrappedExceptionClass)
    {
        CallGetter callGetter = new CallGetter(methodName);
        Throwable cause = null; 
        try
		{
            callGetter.eval(objectToCall);
            fail("Calling "+methodName+" should throw an exception");    
		}
		catch (RuntimeException expectedException)
		{
            cause = expectedException.getCause();
            assertEquals(cause.getClass(), wrappedExceptionClass);
		}
        return cause;
    }	

    public void testThrowingSecurityExceptionIsWrappedWithinInvocationTargetException()
    {
        InvocationTargetException wrapperException = (InvocationTargetException)checkCallingGeneratesRuntimeThatWraps("methodThatGeneratesSecurityException", InvocationTargetException.class);
        assertTrue("Wrapped cause should be SecurityException",wrapperException.getCause() instanceof SecurityException);
    }   
}
