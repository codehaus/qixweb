package org.qixweb.core.test;

import java.io.*;
import java.util.Properties;

import org.qixweb.util.XpLogger;



public class FakeHttpServletResponse implements javax.servlet.http.HttpServletResponse
{
	private int itsStatus;
	private Properties itsHeaders;
	private String itsContentType;
	private FakeServletOutputStream itsServletOutputStream;
	private PrintWriter itsPrintWriter;
	private String itsRedirectedUrl;
    private int itsSentError;

	private class FakeServletOutputStream extends javax.servlet.ServletOutputStream
	{
		private ByteArrayOutputStream itsByteArrayStream;

		FakeServletOutputStream()
		{
			itsByteArrayStream = new ByteArrayOutputStream();
		}
		public void write(int aByte) throws IOException
		{
			itsByteArrayStream.write(aByte);
		}

		public byte[] outputAsBytes()
		{
			return itsByteArrayStream.toByteArray();
		}
	}

	public FakeHttpServletResponse()
	{
		itsServletOutputStream = new FakeServletOutputStream();
		itsPrintWriter = new PrintWriter(itsServletOutputStream);
		itsHeaders = new Properties();
	}
	public void addCookie(javax.servlet.http.Cookie arg1)
	{
	}
	public void addDateHeader(java.lang.String name, long date)
	{
	}
	public void addHeader(java.lang.String name, java.lang.String value)
	{
	}
	public void addIntHeader(java.lang.String name, int value)
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
	public void flushBuffer() throws java.io.IOException
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
	public javax.servlet.ServletOutputStream getOutputStream() throws java.io.IOException
	{
		return itsServletOutputStream;
	}
	public java.io.PrintWriter getWriter() throws java.io.IOException
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
	public void sendError(int arg1) throws java.io.IOException
	{
	    itsSentError = arg1;
	}
	public void sendError(int arg1, String arg2) throws java.io.IOException
	{
	}
	public void sendRedirect(String aRedirectUrl) throws java.io.IOException
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
