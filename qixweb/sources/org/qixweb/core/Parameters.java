package org.qixweb.core;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.qixweb.block.*;
import org.qixweb.time.*;
import org.qixweb.util.UrlParametersExtractor;

import sun.misc.BASE64Encoder;

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

    public String get(String key, String defaultValue)
    {
        String result = get(key);
        if (result == null)
            result = defaultValue;

        return result;
    }

    public Parameters set(String key, String value)
    {
        itsParameters.put(key, value);
        return this;
    }

    public boolean getAsBoolean(String key)
    {
        return new Boolean(get(key)).booleanValue();
    }

    public Parameters set(String key, boolean value)
    {
        itsParameters.put(key, Boolean.toString(value));
        return this;
    }

    public QixwebDate getAsCalendarDD_MM_YYYY(String key)
    {
        String date = get(key);
        if (StringUtils.isNotEmpty(date))
            return DateFormatter.parseDDslashMMslashYYYYasQixwebDate(date);
        else
            return QixwebDate.NULL;
    }

    public QixwebCalendar getAsDateWithPrefix(String keyPrefix)
    {
        return QixwebDate.createFrom(this, keyPrefix);
    }

    public int getAsInt(String key)
    {
        return Integer.parseInt(get(key));
    }

    public Character getAsCharacter(String key)
    {
        String value = get(key);
        return StringUtils.isNotEmpty(value)? new Character(value.charAt(0)) : null;
    }

    public Parameters set(String key, int value)
    {
        itsParameters.put(key, Integer.toString(value));
        return this;
    }

    public byte[] getAsByteArray(String key)
    {
        return (byte[]) itsParameters.get(key);
    }

    public Parameters set(String key, byte[] arrayOfBytes)
    {
        itsParameters.put(key, arrayOfBytes);
        return this;
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
        return (value != null)? Integer.decode(value) : null;
    }

    /**
     * @deprecated
     * @see #set(String key, String value)
     */
    public Parameters set(String key, Object value)
    {
        itsParameters.put(key, value.toString());
        return this;
    }

    public Parameters set(String key, Integer value)
    {
        itsParameters.put(key, value.toString());
        return this;
    }

    public Integer getAsInteger(String key, Integer defaultValue)
    {
        try
        {
            Integer foundValue = getAsInteger(key);
            if (foundValue != null)
                return foundValue;
        }
        catch (Exception ex)
        {
        }
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
                    return Integer.decode((String) eachValue);
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

    public Parameters set(String key, double value)
    {
        itsParameters.put(key, Double.toString(value));
        return this;
    }

    public Parameters set(String key, long value)
    {
        itsParameters.put(key, Long.toString(value));
        return this;
    }

    public Parameters set(String key, String[] someValues)
    {
        itsParameters.put(key, someValues);
        return this;
    }

    public Parameters set(Map newParametersMap)
    {
        if (newParametersMap != null)
            itsParameters.putAll(newParametersMap);
        return this;
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
                else if (itsParameters.get(key) instanceof byte[])
                    append(buf, key, new BASE64Encoder().encode(getAsByteArray(key)));
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

    public void addExcluding(final Parameters source, final Set keysToExclude)
    {
        LightInternalIterator.createOn(source.itsParameters.keySet()).forEach(new Procedure()
        {
            public void run(Object aEach)
            {
                String key = (String) aEach;
                if (!keysToExclude.contains(key))
                {
                    Object object = source.itsParameters.get(key);
                    itsParameters.put(key, object);
                }
            }
        });
    }

    public void add(Parameters source)
    {
        addExcluding(source, new HashSet());
    }

    public void remove(String key)
    {
        itsParameters.remove(key);
    }

    public QixwebTime getAsTimeHH_colon_mm(String key)
    {
        String timeAsString = get(key);
        if (StringUtils.isNotEmpty(timeAsString))
            return DateFormatter.parseHH_colon_mm(timeAsString);
        else
            return QixwebTime.NULL;
    }

    public double getAsDouble(String key)
    {
        return Double.parseDouble(get(key));
    }
}
