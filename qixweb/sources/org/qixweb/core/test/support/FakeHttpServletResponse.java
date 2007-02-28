package org.qixweb.core.test.support;

import java.io.*;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.qixweb.util.XpLogger;

public class FakeHttpServletResponse implements HttpServletResponse
{
    private int itsStatus;
    private Properties itsHeaders;
    private String itsContentType;
    private FakeServletOutputStream itsServletOutputStream;
    private PrintWriter itsPrintWriter;
    private String itsRedirectedUrl;
    private int itsSentError;

    public FakeHttpServletResponse()
    {
        itsServletOutputStream = new FakeServletOutputStream();
        itsPrintWriter = new PrintWriter(itsServletOutputStream);
        itsHeaders = new Properties();
    }

    public void addCookie(Cookie arg1)
    {
    }

    public void addDateHeader(String name, long date)
    {
    }

    public void addHeader(String name, String value)
    {
    }

    public void addIntHeader(String name, int value)
    {
    }

    public boolean containsHeader(String arg1)
    {
        return false;
    }

    public String encodeRedirectUrl(String arg1)
    {
        return null;
    }

    public String encodeRedirectURL(String arg1)
    {
        return null;
    }

    public String encodeUrl(String arg1)
    {
        return null;
    }

    public String encodeURL(String arg1)
    {
        return null;
    }

    public void flushBuffer() throws IOException
    {
    }

    public int getBufferSize()
    {
        return 0;
    }

    public int status()
    {
        return itsStatus;
    }

    public String getCharacterEncoding()
    {
        return null;
    }

    public java.util.Locale getLocale()
    {
        return null;
    }

    public ServletOutputStream getOutputStream() throws IOException
    {
        return itsServletOutputStream;
    }

    public PrintWriter getWriter() throws IOException
    {
        return itsPrintWriter;
    }

    public boolean isCommitted()
    {
        return false;
    }

    public Object outputAsObject()
    {
        Object theObjectToReturn = null;
        try
        {
            ObjectInputStream theObjectStream = readableObjectOutput();
            theObjectToReturn = theObjectStream.readObject();
        }
        catch (IOException ioex)
        {
            XpLogger.logException(ioex);
        }
        catch (ClassNotFoundException cnfex)
        {
            XpLogger.logException(cnfex);
        }
        return theObjectToReturn;
    }

    public String outputAsString()
    {
        itsPrintWriter.flush();
        return outputAsStringWithoutFlushing();
    }

    public String outputAsStringWithoutFlushing()
    {
        // @PMD:REVIEWED:StringInstantiation: by bop on 3/8/05 5:31 PM
        return new String(itsServletOutputStream.outputAsBytes());
    }

    public byte[] outputAsBytes()
    {
        itsPrintWriter.flush();
        return itsServletOutputStream.outputAsBytes();
    }

    private ObjectInputStream readableObjectOutput() throws IOException
    {
        return new ObjectInputStream(new ByteArrayInputStream(itsServletOutputStream.outputAsBytes()));
    }

    public void reset()
    {
    }

    public void sendError(int arg1) throws IOException
    {
        itsSentError = arg1;
    }

    public void sendError(int arg1, String arg2) throws IOException
    {
    }

    public void sendRedirect(String aRedirectUrl) throws IOException
    {
        itsRedirectedUrl = aRedirectUrl;
    }

    public void setBufferSize(int size)
    {
    }

    public void setContentLength(int arg1)
    {
    }

    public void setContentType(String aContentType)
    {
        itsContentType = aContentType;
    }

    public String contentType()
    {
        return itsContentType;
    }

    public void setDateHeader(String arg1, long arg2)
    {
    }

    public void setHeader(String anHeaderKey, String anHeaderValue)
    {
        itsHeaders.setProperty(anHeaderKey, anHeaderValue);
    }

    public void setIntHeader(String arg1, int arg2)
    {
    }

    public void setLocale(java.util.Locale loc)
    {
    }

    public void setStatus(int aStatusCode)
    {
        itsStatus = aStatusCode;
    }

    public void setStatus(int arg1, String arg2)
    {
    }

    public void resetBuffer()
    {
    }

    public String redirectedUrl()
    {
        return itsRedirectedUrl;
    }

    public String headerFor(String anHeaderKey)
    {
        return itsHeaders.getProperty(anHeaderKey);
    }

    public int sentError()
    {
        return itsSentError;
    }

}
