package org.qixweb.core;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class WebLink extends WebAppUrl
{
    protected String itsLabel;

    public WebLink(String anUrl, String aLabel)
    {
        this(Object.class, anUrl, aLabel);
    }

    public WebLink(Class aTarget, String anUrl, String aLabel)
    {
        super(aTarget, anUrl);
        itsLabel = aLabel;
    }

    public static WebLink createGhostLink(String label)
    {
        WebLink ghostLink = new WebLink("", label);
        ghostLink.disable();
        return ghostLink;
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof WebLink)
        {
            WebLink anotherLink = (WebLink) obj;
            return super.equals(obj) && itsLabel.equals(anotherLink.itsLabel);
        }
        else
            return false;
    }
    
    public int hashCode()
    {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(itsLabel).toHashCode();       
    }

    public String toString()
    {
        return super.toString() + " label = " + label();
    }

    public String label()
    {
        return itsLabel;
    }
}
