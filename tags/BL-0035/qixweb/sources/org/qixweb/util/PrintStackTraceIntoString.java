package org.qixweb.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class PrintStackTraceIntoString
{
	public static String runOn(Throwable exc)
	{
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		exc.printStackTrace(printWriter);
        
		return stringWriter.toString();
	}
}
