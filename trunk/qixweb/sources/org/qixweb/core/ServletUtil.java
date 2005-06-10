package org.qixweb.core;

import javax.servlet.http.HttpServletResponse;

import org.qixweb.util.XpLogger;


//TODO: e questo puo' diventare un metodo di utilita' della versione base
public class ServletUtil
{
	public static void reportException(HttpServletResponse response, Exception ex)
	{
		try
		{
			ex.printStackTrace();
			ex.printStackTrace(response.getWriter());
		}
		catch (Exception e)
		{
			XpLogger.logException(e);
		}
	}
}
