package org.qixweb.core;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class WebLabel implements Comparable
{
    public static final WebLabel EMPTY = new WebLabel("");

    private final String itsString;

    public WebLabel(Object aValue)
    {
        itsString = aValue != null ? aValue.toString() : "";
    }

    public String toString()
    {
        return StringUtils.trimToEmpty(StringEscapeUtils.escapeHtml(itsString));
    }

    public int compareTo(Object aComparable)
    {
        return this.toString().compareTo(aComparable.toString());
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof WebLabel)
            return itsString.equals(((WebLabel) obj).itsString);
        else
            return false;
    }

    public int hashCode()
    {
        return itsString.hashCode();
    }

    public boolean isEmpty()
    {
        return EMPTY.equals(this);
    }

}
