package org.qixweb.util.test;

import org.qixweb.util.PrintStackTraceIntoString;


public class TestPrintStackTraceIntoString extends ExtendedTestCase
{


	public void testRunOn()
	{
		String result = PrintStackTraceIntoString.runOn(new IllegalArgumentException("foo"));
		assert_contains("exception not found", result, "java.lang.IllegalArgumentException: foo");
	}
}
