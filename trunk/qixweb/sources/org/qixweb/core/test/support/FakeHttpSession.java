package org.qixweb.core.test.support;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class FakeHttpSession implements HttpSession
{
	private String itsSessionID;
	// @PMD:REVIEWED:LooseCoupling: by bop on 3/2/05 12:05 PM
	private HashMap itsAttributes;
	
	public FakeHttpSession()
	{
		itsAttributes = new HashMap();
	}

	public long getCreationTime()
	{
		return 0;
	}

	public String getId()
	{
		return itsSessionID;
	}

	public long getLastAccessedTime()
	{
		return 0;
	}

	public ServletContext getServletContext()
	{
		return null;
	}

	public void setMaxInactiveInterval(int arg0)
	{
	}

	public int getMaxInactiveInterval()
	{
		return 0;
	}

	public HttpSessionContext getSessionContext()
	{
		return null;
	}

	public Object getAttribute(String key)
	{
		return itsAttributes.get(key);
	}

	public Object getValue(String arg0)
	{
		return null;
	}

	public Enumeration getAttributeNames()
	{
		return null;
	}

	public String[] getValueNames()
	{
		return null;
	}

	public void setAttribute(String key, Object value)
	{
		itsAttributes.put(key, value);		
	}

	public void putValue(String arg0, Object arg1)
	{
	}

	public void removeAttribute(String arg0)
	{
	}

	public void removeValue(String arg0)
	{
	}

	public void invalidate()
	{
	}

	public boolean isNew()
	{
		return false;
	}

	public void simulateSessionID(String aSessionID)
	{
		itsSessionID = aSessionID;
	}

}
