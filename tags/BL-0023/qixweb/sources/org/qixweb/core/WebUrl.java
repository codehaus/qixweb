package org.qixweb.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import org.qixweb.util.StringUtil;
import org.qixweb.util.XpLogger;



public class WebUrl
{
	private static final String ENCONDING_ISO_8859_1 = "ISO-8859-1";
	
    protected Map itsParameters;
	private String itsUrlBeforeParameters;

    private boolean isEnabled;

	public static String encode(String parameterValue)
	{
		try
		{
			return URLEncoder.encode(parameterValue, ENCONDING_ISO_8859_1);
		}
		catch (UnsupportedEncodingException uee)
		{
			XpLogger.logException(uee);
			return parameterValue;
		}
	}

	public static String decode(String parameterValue)
	{
		try
		{
			return URLDecoder.decode(parameterValue, ENCONDING_ISO_8859_1);
		}
		catch (UnsupportedEncodingException uee)
		{
			XpLogger.logException(uee);
			return parameterValue;
		}
	}

	public WebUrl(String anUrl)
	{
		itsUrlBeforeParameters = anUrl.split("\\?")[0];
		itsParameters = extractParametersFrom(anUrl);
        isEnabled = true;
	}

	public String getParameter(String key)
	{
		Object value = itsParameters.get(key);
		if (value == null)
			return null;
		else if (value.getClass().isArray())
			return ((String[]) value)[0];
		else
			return (String) value;
	}

	public String[] getParameterValuesOf(String key)
	{
		if (itsParameters.get(key) != null)
        {
            if (itsParameters.get(key).getClass().isArray())
                return (String[]) itsParameters.get(key);
            else
                return new String[] { (String) itsParameters.get(key) };
        }
		else
			return new String[0];
	}

	public Map parametersBeginningWith(String aPrefix)
	{
		HashMap parametersBeginningWithPrefix = new HashMap();

		Iterator parametersIterator = itsParameters.keySet().iterator();
		while (parametersIterator.hasNext())
		{
			String key = (String) parametersIterator.next();
			if (key.startsWith(aPrefix))
				parametersBeginningWithPrefix.put(removePrefixFrom(aPrefix, key), getParameter(key));
		}

		return parametersBeginningWithPrefix;
	}

	protected String removePrefixFrom(String aPrefix, String aString)
	{
		return aString.substring(aPrefix.length());
	}

	protected String parameters()
	{
		if (itsParameters.size() > 0)
		{
			StringBuffer buf = new StringBuffer();
			Object[] keys = itsParameters.keySet().toArray();

			Arrays.sort(keys);
			for (int i = 0; i < keys.length; i++)
			{
				String key = (String) keys[i];
				if (itsParameters.get(key) instanceof String)
					appendParameter(buf, key, getParameter(key));
				else
					appendParameter(buf, key, getParameterValuesOf(key));
			}
			buf.setCharAt(0, '?');

			return buf.toString();
		}
		else
			return "";
	}

	private void appendParameter(StringBuffer buf, String key, String parameterValue)
	{
		buf.append("&");
		buf.append(key);
		buf.append("=");
		buf.append(encode(parameterValue));
	}

	private void appendParameter(StringBuffer buf, String key, String[] parameterValues)
	{
		for (int j = 0; j < parameterValues.length; j++)
			appendParameter(buf, key, parameterValues[j]);
	}

	public void setParameter(String key, String value)
	{
		itsParameters.put(key, value);
	}

    public void setParameter(String key, int value)
    {
        itsParameters.put(key, Integer.toString(value));
    }

    public void setParameter(String key, boolean value)
    {
        itsParameters.put(key, Boolean.toString(value));
    }

    public void setParameter(String key, String[] someValues)
	{
		itsParameters.put(key, someValues);
	}

	public void setParameters(Map newParametersMap)
	{
        if (newParametersMap != null)
    		itsParameters.putAll(newParametersMap);
	}

	public String destination()
	{
		return itsUrlBeforeParameters + parameters();
	}

	public boolean equals(Object anotherObject)
	{
		if (anotherObject instanceof WebUrl)
		{
			WebUrl anotherUrl = (WebUrl) anotherObject;
			return destination().equals(anotherUrl.destination()) && isEnabled() == anotherUrl.isEnabled();
		}
		else
			return false;
	}

    public int hashCode()
    {
        return destination().hashCode();
    }
	
    public String toString()
	{
		return destination() + " enabled = " + isEnabled;
	}

	protected static Map extractParametersFrom(String anUrl)
	{
		HashMap parameters = new HashMap();
		
		if (StringUtil.string_contains(anUrl, "?"))
		{
			String fromQuestionMark = anUrl.split("\\?")[1];
			String[] allKeyValues = fromQuestionMark.split("&");
	
			for (int i = 0; i < allKeyValues.length; i++)
				put_into(allKeyValues[i], parameters);
		}
		return parameters;
	}

	private static void put_into(String aKeyValuePair, Map aParametersMap)
	{
		String[] keyValue = aKeyValuePair.split("=");
		if (keyValue.length == 2)
		    aParametersMap.put(keyValue[0], new String[] {decode(keyValue[1])});
	}

    public int parametersLength()
    {
        return itsParameters.size();
    }
    
    protected void resetParameters()
    {
        itsParameters.clear();
    }

    public boolean isEnabled()
    {
    	return isEnabled;
    }

    public void disable()
    {
    	isEnabled = false;
    }
}
