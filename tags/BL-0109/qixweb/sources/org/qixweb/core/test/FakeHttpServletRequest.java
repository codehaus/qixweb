package org.qixweb.core.test;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.qixweb.block.LightInternalIterator;
import org.qixweb.block.Procedure;
import org.qixweb.util.XpLogger;


public class FakeHttpServletRequest implements javax.servlet.http.HttpServletRequest
{
	// @PMD:REVIEWED:LooseCoupling: by bop on 3/2/05 12:05 PM
	private HashMap itsAttributes;
	private Properties itsProperties;
	private byte[] itsObjectsAsArray;
	private boolean isForm = false;
	private HttpSession itsSession;
	private HttpSession itsCreateSession;
	private String itsPathInfo;
	private String itsServletPath;
    // @PMD:REVIEWED:LooseCoupling: by bop on 3/2/05 12:05 PM
	private HashMap itsHeaderNames;

	private class FakeServletInputStream extends javax.servlet.ServletInputStream
	{
		private ByteArrayInputStream itsByteArrayStream;

		FakeServletInputStream()
		{
			itsByteArrayStream = new ByteArrayInputStream(itsObjectsAsArray);
		}
		public int read() throws java.io.IOException
		{
			return itsByteArrayStream.read();
		}
	}


	public FakeHttpServletRequest()
	{
		this(new Properties());
	}

	public FakeHttpServletRequest(Properties someProperties)
	{
		this(someProperties, false);
	}

	public FakeHttpServletRequest(Properties someProperties, boolean isRequestFromForm)
	{
		itsProperties = someProperties;
		isForm = isRequestFromForm;
		itsHeaderNames = new HashMap();
		itsAttributes = new HashMap();
	}

	public Object getAttribute(String attributeName)
	{
		return itsAttributes.get(attributeName);
	}
	public java.util.Enumeration getAttributeNames()
	{
		return new Vector().elements();
	}
	public String getAuthType()
	{
		return null;
	}
	public String getCharacterEncoding()
	{
		return null;
	}
	public int getContentLength()
	{
		return 0;
	}
	public String getContentType()
	{
		if (isForm)
			return "application/x-www-form-urlencoded";
		else
			return null;
	}
	public java.lang.String getContextPath()
	{
		return null;
	}
	public javax.servlet.http.Cookie[] getCookies()
	{
		return null;
	}
	public long getDateHeader(String arg1)
	{
		return 0;
	}

	public java.util.Enumeration getHeaderNames()
	{
		return new Vector().elements();
	}
	public java.util.Enumeration getHeaders(java.lang.String name)
	{
		return null;
	}
	public javax.servlet.ServletInputStream getInputStream() throws java.io.IOException
	{
		return new FakeServletInputStream();
	}
	public int getIntHeader(String arg1)
	{
		return 0;
	}
	public java.util.Locale getLocale()
	{
		return null;
	}
	public java.util.Enumeration getLocales()
	{
		return null;
	}
	public String getMethod()
	{
		return null;
	}
	public String getParameter(String aParameter)
	{
		return itsProperties.getProperty(aParameter);
	}

	public void simulateSession(HttpSession aSession)
	{
		itsSession = aSession;
	}

	public void simulatePathInfo(String aPathInfo)
	{
		itsPathInfo = aPathInfo;
	}

	public void simulateServletPath(String aServletPath)
	{
		itsServletPath = aServletPath;
	}

	public void simulateSessionWhenAskedToCreateANewOne(HttpSession aSession)
	{
		itsCreateSession = aSession;
	}
	
	public void simulateParameter(String parameterName, String parameterValue)
	{
		itsProperties.setProperty(parameterName, parameterValue);
	}
	public java.util.Enumeration getParameterNames()
	{
		return itsProperties.keys();
	}
	public java.lang.String[] getParameterValues(String arg1)
	{
		return (String[]) itsProperties.get(arg1);
	}
	public String getPathInfo()
	{
		return itsPathInfo;
	}
	public String getPathTranslated()
	{
		return null;
	}
	public String getProtocol()
	{
		return null;
	}
	public String getQueryString()
	{
		return null;
	}
	public java.io.BufferedReader getReader() throws java.io.IOException
	{
		return null;
	}
	public String getRealPath(String arg1)
	{
		return null;
	}
	public String getRemoteAddr()
	{
		return null;
	}
	public String getRemoteHost()
	{
		return null;
	}
	public String getRemoteUser()
	{
		return null;
	}
	public javax.servlet.RequestDispatcher getRequestDispatcher(java.lang.String path)
	{
		return null;
	}
	public String getRequestedSessionId()
	{
		return null;
	}
	public String getRequestURI()
	{
		return null;
	}
	public String getScheme()
	{
		return null;
	}
	public String getServerName()
	{
		return null;
	}
	public int getServerPort()
	{
		return 0;
	}
	public String getServletPath()
	{
		return itsServletPath;
	}
	public HttpSession getSession()
	{
		return itsSession;
	}
	public HttpSession getSession(boolean create)
	{
		if (itsSession == null && create)
			itsSession = itsCreateSession;
		return itsSession;
	}
	public java.security.Principal getUserPrincipal()
	{
		return null;
	}
	public boolean isRequestedSessionIdFromCookie()
	{
		return false;
	}
	public boolean isRequestedSessionIdFromUrl()
	{
		return false;
	}
	public boolean isRequestedSessionIdFromURL()
	{
		return false;
	}
	public boolean isRequestedSessionIdValid()
	{
		return false;
	}
	public boolean isSecure()
	{
		return false;
	}
	public boolean isUserInRole(java.lang.String role)
	{
		return false;
	}
	public void removeAttribute(java.lang.String name)
	{
	}
	public void setAttribute(String arg1, Object arg2)
	{
	}

	public void simulatePost(Object oneObject)
	{
		try
		{
			ByteArrayOutputStream theByteArrayStream = new ByteArrayOutputStream();
			ObjectOutputStream theObjectOutputStream = new ObjectOutputStream(theByteArrayStream);
			theObjectOutputStream.writeObject(oneObject);
			itsObjectsAsArray = theByteArrayStream.toByteArray();
		}
		catch (IOException ioex)
		{			
			XpLogger.logException(ioex);
		}
	}
	
	public void simulateAttribute(String attributeName, Object oneObject)
	{
		itsAttributes.put(attributeName, oneObject);
	}	
	
	public void simulatePost(byte[] bytesToSend)
	{
		try
		{
			ByteArrayOutputStream theByteArrayStream = new ByteArrayOutputStream();
			theByteArrayStream.write(bytesToSend);
			itsObjectsAsArray = theByteArrayStream.toByteArray();
		}
		catch (IOException ioex)
		{			
			XpLogger.logException(ioex);
		}
	}	
	public StringBuffer getRequestURL()
	{
		return null;
	}

	public Map getParameterMap()
	{
		final HashMap propertiesMap = new HashMap();
		LightInternalIterator.createOn(itsProperties.keySet().iterator()).forEach(new Procedure()
		{
			public void run(Object anObject)
			{
				propertiesMap.put(anObject, new String[] { (String)itsProperties.get(anObject)});
			}
		});
		return propertiesMap;
	}

	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException
	{
	}
	public void simulateHeader(String name, String value)
	{
		itsHeaderNames.put(name, value);
	}
	
	public String getHeader(String name)
	{
		return (String)itsHeaderNames.get(name);
	}
	

// @PMD:REVIEWED:ExcessivePublicCount: by bop on 1/26/05 4:57 PM
}
