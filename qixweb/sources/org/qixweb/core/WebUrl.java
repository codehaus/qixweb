package org.qixweb.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.qixweb.util.UrlParametersExtractor;
import org.qixweb.util.XpLogger;

public class WebUrl implements Comparable
{
    private static final String ENCONDING_ISO_8859_1 = "ISO-8859-1";

    private Parameters itsParameters;    
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
            itsParameters = new Parameters(new HashMap());
            isEnabled = false;
        }
        else
        {
            setUrlBeforeParameters(anUrl);
            Map parameters = new UrlParametersExtractor(anUrl).run();
            itsParameters = new Parameters(parameters);
            itsLabel = StringUtils.isEmpty(aLabel) ? anUrl : aLabel;
            isEnabled = true;
        }
    }

    protected String removePrefixFrom(String aPrefix, String aString)
    {
        return aString.substring(aPrefix.length());
    }

    public String destination()
    {
        return itsUrlBeforeParameters + parameters().allAsString();
    }
    
    public String encodedDestination()
    {
        return StringEscapeUtils.escapeHtml(itsUrlBeforeParameters + parameters().allAsString());
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

    protected void resetParameters()
    {
        parameters().removeAll();
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

    public Parameters parameters()
    {
        return itsParameters;
    }

}
