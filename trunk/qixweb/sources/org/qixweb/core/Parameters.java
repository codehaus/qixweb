package org.qixweb.core;

import java.util.*;

import org.qixweb.block.Function;
import org.qixweb.block.LightInternalIterator;
import org.qixweb.time.*;
import org.qixweb.time.DateFormatter;
import org.qixweb.time.QixwebCalendar;
import org.qixweb.util.UrlParametersExtractor;

public class Parameters
{
    protected Map itsParameters;

    public Parameters()
    {
        this(new HashMap());
    }

    public Parameters(Map parameters)
    {
        itsParameters = parameters;
    }
    
    // TODO: da rimuovere CAS
    public Object getThatCASMustRemove(String key)
    {
        return itsParameters.get(key);
    }

    // TODO: da rimuovere CAS
    public void setThatCASMustRemove(String key, Object value)
    {
        itsParameters.put(key, value);
    }
    
    public String get(String key)
    {
        Object value = itsParameters.get(key);
        if (value == null)
            return null;
        else if (value.getClass().isArray())
            return ((String[]) value)[0];
        else
            return (String) value;
    }    
    
    public void set(String key, String value)
    {
        itsParameters.put(key, value);
    }
    
    public boolean getAsBoolean(String key)
    {
        return new Boolean(get(key)).booleanValue();
    }
    
    public void set(String key, boolean value)
    {
        itsParameters.put(key, Boolean.toString(value));
    }
    
    public QixwebCalendar getAsCalendarDD_MM_YYYY(String key)
    {
        return DateFormatter.parseDD_MM_YYYYasQixwebDate(get(key));
    }    
    
    public QixwebCalendar getAsDateWithPrefix(String keyPrefix)
    {
        return QixwebDate.createFrom(this, keyPrefix);
    }
    
    
    public int getAsInt(String key)
    {
        return Integer.parseInt(get(key));
    }
    
    public void set(String key, int value)
    {
        itsParameters.put(key, Integer.toString(value));
    }
    
    public int getAsInt(String key, int defaultValue)
    {
        try
        {
            return getAsInt(key);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }
    
    public Integer getAsInteger(String key)
    {
        String value = get(key);
        if (value != null)
            return Integer.decode(value);
        else
            return null;
    }    
    
    public void set(String key, Object value)
    {
        itsParameters.put(key, value.toString());
    }
    
    public Integer getAsInteger(String key, Integer defaultValue)
    {
        try
        {
            Integer foundValue = getAsInteger(key);
            if (foundValue != null)
                return foundValue;
        }
        catch (Exception ex) {}
        return defaultValue;
    }
    
    public String[] getAllValuesOf(String parameterKey)
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
    
    public Integer[] getAllValuesAsIntegerOf(String key)
    {
        String[] values = getAllValuesOf(key);
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
    
    public Map allBeginningWith(String aPrefix)
    {
        HashMap parametersBeginningWithPrefix = new HashMap();

        Iterator parametersIterator = itsParameters.keySet().iterator();
        while (parametersIterator.hasNext())
        {
            String key = (String) parametersIterator.next();
            if (key.startsWith(aPrefix))
                parametersBeginningWithPrefix.put(removePrefixFrom(aPrefix, key), get(key));
        }

        return parametersBeginningWithPrefix;
    }

    public void set(String key, double value)
    {
        itsParameters.put(key, Double.toString(value));
    }

    public void set(String key, long value)
    {
        itsParameters.put(key, Long.toString(value));
    }

    public void set(String key, String[] someValues)
    {
        itsParameters.put(key, someValues);
    }

    public void set(Map newParametersMap)
    {
        if (newParametersMap != null)
            itsParameters.putAll(newParametersMap);
    }
    
    public int size()
    {
        return itsParameters.size();
    }
    
    protected String removePrefixFrom(String aPrefix, String aString)
    {
        return aString.substring(aPrefix.length());
    }
    
    protected void removeAll()
    {
        itsParameters.clear();
    }


    public String allAsString()
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
                    append(buf, key, get(key));
                else
                    append(buf, key, getAllValuesOf(key));
            }
            buf.setCharAt(0, UrlParametersExtractor.QUESTION_MARK.charAt(0));
    
            return buf.toString();
        }
        else
            return "";
    }


    private void append(StringBuffer buf, String key, String parameterValue)
    {
        buf.append(UrlParametersExtractor.AMPERSAND);
        buf.append(key);
        buf.append(UrlParametersExtractor.EQUAL);
        buf.append(WebUrl.encode(parameterValue));
    }


    private void append(StringBuffer buf, String key, String[] parameterValues)
    {
        for (int j = 0; j < parameterValues.length; j++)
            append(buf, key, parameterValues[j]);
    }
    
    public Set keys()
    {
        return itsParameters.keySet();
    }
    
    public boolean equals(Object anotherObject)
    {
        if (anotherObject instanceof Parameters)
        {
            Parameters other = (Parameters) anotherObject;
            return allAsString().equals(other.allAsString());
        }
        else
            return false;
    }
    
}
