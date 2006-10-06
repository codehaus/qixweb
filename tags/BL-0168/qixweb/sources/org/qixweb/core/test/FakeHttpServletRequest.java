package org.qixweb.core.test;

import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.*;

import org.qixweb.block.LightInternalIterator;
import org.qixweb.block.Procedure;
import org.qixweb.util.XpLogger;

public class FakeHttpServletRequest implements HttpServletRequest
{
    // @PMD:REVIEWED:LooseCoupling: by bop on 3/2/05 12:05 PM
    private HashMap itsAttributes;
    private Properties itsProperties;
    private byte[] itsObjectsAsArray;

    private HttpSession itsSession;
    private HttpSession itsCreateSession;
    private String itsPathInfo;
    private String itsServletPath;
    // @PMD:REVIEWED:LooseCoupling: by bop on 3/2/05 12:05 PM
    private HashMap itsHeaderNames;
    private String itsContextPath;
    private String itsContentType;

    private class FakeServletInputStream extends ServletInputStream
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
        itsProperties = someProperties;

        itsHeaderNames = new HashMap();
        itsAttributes = new HashMap();
    }

    public Object getAttribute(String attributeName)
    {
        return itsAttributes.get(attributeName);
    }

    public Enumeration getAttributeNames()
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
        return itsContentType;
    }

    public String getContextPath()
    {
        return itsContextPath;
    }

    public Cookie[] getCookies()
    {
        return null;
    }

    public long getDateHeader(String arg1)
    {
        return 0;
    }

    public Enumeration getHeaderNames()
    {
        return new Vector().elements();
    }

    public Enumeration getHeaders(String name)
    {
        return null;
    }

    public ServletInputStream getInputStream() throws IOException
    {
        return new FakeServletInputStream();
    }

    public int getIntHeader(String arg1)
    {
        return 0;
    }

    public Locale getLocale()
    {
        return null;
    }

    public Enumeration getLocales()
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

    public void simulateContextPath(String aContextPath)
    {
        itsContextPath = aContextPath;
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

    public Enumeration getParameterNames()
    {
        return itsProperties.keys();
    }

    public String[] getParameterValues(String arg1)
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

    public BufferedReader getReader() throws IOException
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

    public RequestDispatcher getRequestDispatcher(String path)
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

    public boolean isUserInRole(String role)
    {
        return false;
    }

    public void removeAttribute(String name)
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
                propertiesMap.put(anObject, new String[] { (String) itsProperties.get(anObject) });
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
        return (String) itsHeaderNames.get(name);
    }

    public void setContentType(String contentType)
    {
        itsContentType = contentType;
    }

    // @PMD:REVIEWED:ExcessivePublicCount: by bop on 1/26/05 4:57 PM
}
