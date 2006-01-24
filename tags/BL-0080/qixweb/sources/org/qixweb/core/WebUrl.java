package org.qixweb.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.qixweb.block.Function;
import org.qixweb.block.LightInternalIterator;
import org.qixweb.time.*;
import org.qixweb.util.UrlParametersExtractor;
import org.qixweb.util.XpLogger;

public class WebUrl implements Comparable
{
    private static final String ENCONDING_ISO_8859_1 = "ISO-8859-1";

    protected Map itsParameters;
    private String itsUrlBeforeParameters;
    private boolean isEnabled;
    protected String itsLabel;

    public static String encode(String parameterValue)
    {
        try
        {
            return URLEncoder.encode(parameterValue, ENCONDING_ISO_8859_1);
        }
        catch (UnsupportedEncodingException uee)
        {
            XpLogger.logException(ENCONDING_ISO_8859_1 + " no longer supported?!?", uee);
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
            XpLogger.logException(ENCONDING_ISO_8859_1 + " no longer supported?!?", uee);
            return parameterValue;
        }
    }

    public WebUrl(String anUrl)
    {
        this(anUrl, anUrl);
    }

    public WebUrl(String anUrl, String aLabel)
    {
        if (anUrl == null)
        {
            itsUrlBeforeParameters = "";
            itsParameters = new HashMap();
            isEnabled = false;
        }
        else
        {
            setUrlBeforeParameters(anUrl);
            itsParameters = new UrlParametersExtractor(anUrl).run();
            itsLabel = StringUtils.isEmpty(aLabel) ? anUrl : aLabel;
            isEnabled = true;
        }
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

    public int getParameterAsInt(String key)
    {
        return Integer.parseInt(getParameter(key));
    }

    public int getParameterAsInt(String key, int defaultValue)
    {
        try
        {
            return getParameterAsInt(key);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    public boolean getParameterAsBoolean(String key)
    {
        return new Boolean(getParameter(key)).booleanValue();
    }

    public QixwebCalendar getParameterAsDateWithPrefix(String keyPrefix)
    {
        return QixwebDate.createFrom(this, keyPrefix);
    }

    public QixwebCalendar getParameterAsCalendarDD_MM_YYYY(String key)
    {
        return DateFormatter.parseDD_MM_YYYYasQixwebDate(getParameter(key));
    }

    public String[] getParameterValuesOf(String parameterKey)
    {
        if (itsParameters.get(parameterKey) != null)
        {
            if (itsParameters.get(parameterKey).getClass().isArray())
                return (String[]) itsParameters.get(parameterKey);
            else
                return new String[] { (String) itsParameters.get(parameterKey) };
        }
        else
            return new String[0];
    }

    public Integer[] getParameterValuesAsIntegerOf(String key)
    {
        String[] values = getParameterValuesOf(key);
        return (Integer[]) LightInternalIterator.createOn(values).collect(new Function()
        {
            public Object eval(Object eachValue)
            {
                try
                {
                    return Integer.decode((String)eachValue);
                }
                catch (NumberFormatException ex)
                {
                    return null;
                }
            }
        }, Integer.class);
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

    public String parameters()
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
            buf.setCharAt(0, UrlParametersExtractor.QUESTION_MARK.charAt(0));

            return buf.toString();
        }
        else
            return "";
    }

    private void appendParameter(StringBuffer buf, String key, String parameterValue)
    {
        buf.append(UrlParametersExtractor.AMPERSAND);
        buf.append(key);
        buf.append(UrlParametersExtractor.EQUAL);
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

    public void setParameter(String key, Object value)
    {
        itsParameters.put(key, value.toString());
    }

    public void setParameter(String key, int value)
    {
        itsParameters.put(key, Integer.toString(value));
    }

    public void setParameter(String key, long value)
    {
        itsParameters.put(key, Long.toString(value));
    }

    public void setParameter(String key, double value)
    {
        itsParameters.put(key, Double.toString(value));
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
            return destination().equals(anotherUrl.destination()) && isEnabled() == anotherUrl.isEnabled() && label().equals(anotherUrl.label());
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
        return destination() + " enabled = " + isEnabled() + " label = " + label();
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

    public void setUrlBeforeParameters(String url)
    {
        itsUrlBeforeParameters = url.split("\\?")[0];
    }

    public String label()
    {
        return itsLabel;
    }

    protected void label(String newLabel)
    {
        itsLabel = newLabel;
    }

    public int compareTo(Object anObject)
    {
        if (anObject instanceof WebUrl)
        {
            WebUrl anotherWebUrl = (WebUrl) anObject;
            return itsLabel.compareTo(anotherWebUrl.itsLabel);
        }
        return -1;
    }

    public Integer getParameterAsInteger(String key)
    {
        return Integer.decode(getParameter(key));
    }

    public Integer getParameterAsInteger(String key, Integer defaultValue)
    {
        try
        {
            return getParameterAsInteger(key);
        }
        catch (Exception ex)
        {
            return defaultValue;
        }
    }

}
