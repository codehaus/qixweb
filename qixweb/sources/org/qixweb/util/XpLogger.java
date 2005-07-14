package org.qixweb.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.*;

public class XpLogger
{
    private static Level itsOldLevel;

	// @PMD:REVIEWED:LoggerIsNotStaticFinal: by bop on 1/26/05 6:14 PM
	private static Logger itsLogger;
      
	private static Logger logger()
	{
		if (itsLogger == null)
		{
			if (LogManager.exists("xplogger") != null)
				itsLogger = Logger.getLogger("xplogger");
			else
			{
				itsLogger = Logger.getLogger("default logger");
				itsLogger.setLevel(Level.DEBUG);
				itsLogger.addAppender(new ConsoleAppender(new PatternLayout("[%d{dd MMM yyyy HH:mm:ss}] %-5p %c - %m %n")));
			}
			itsOldLevel = itsLogger.getLevel();
		}		
		
		return itsLogger;
	}
    
	public static void debug(String aMessage)
	{
        logger().debug(aMessage);
	}
	
	public static void info(String aMessage)
	{
		logger().info(aMessage);
	}

	public static void off()
	{
		itsOldLevel = logger().getLevel();
		logger().setLevel(Level.OFF);
	}

	public static void resume()
	{
		logger().setLevel(itsOldLevel);
	}

	public static void warning(String aMessage)
	{
        logger().warn(aMessage);
	}
	
	public static void error(String aMessage)
	{
        logger().error(aMessage);
	}

	public static void fatal(String aMessage)
	{
        logger().fatal(aMessage);
	}

	public static boolean isDebugEnabled()
	{
        return logger().isDebugEnabled();    
	}

    public static void logParametersFrom(HttpServletRequest aRequest)
    {
        if (isDebugEnabled())
        {
    		debug("*** Headers ***");
            Enumeration headerNames = aRequest.getHeaderNames();
            if (headerNames != null)
            {
            	while (headerNames.hasMoreElements())
            	{
            		String element = (String) headerNames.nextElement();
            		debug(element + ": " + aRequest.getHeader(element));
            	}
            }
            
            debug("*** Request parameters ***");
    		Enumeration parameterNames = aRequest.getParameterNames();
    		if (parameterNames != null)
    		{
    			while (parameterNames.hasMoreElements())
    			{
    				String element = (String) parameterNames.nextElement();
                    debug(element + ": " + aRequest.getParameter(element));
    			}
    		}
    		
            debug("*** Extra path info ***");		
            debug(aRequest.getPathInfo());
        }        
    }

    public static void logException(Throwable exc)
    {
        logException("", exc);
    }
    
    public static void logException(String aMessage, Throwable exc)
    {
        error(aMessage+" "+PrintStackTraceIntoString.runOn(exc));
    }    


    
}
