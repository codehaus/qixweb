package org.qixweb.core;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.qixweb.util.DeepEquals;

public abstract class DefaultWebForm implements WebForm
{
    private boolean isEnabled;
    
    public DefaultWebForm()
    {
        isEnabled = true;
    }
    
    public DefaultWebForm(QixwebUser user)
    {
        isEnabled = user.hasWritePermission();
    }

    public boolean equals(Object otherForm)
    {
        return DeepEquals.equals(this, otherForm);
    }
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
    public void disable()
    {
    	isEnabled = false;
    }
    public boolean isEnabled()
    {
        return isEnabled;
    }
    public QixwebUrl actionUrl()
    {
        QixwebUrl url = concreteActionUrl();
        if (!isEnabled())
            url.disable();
        
        return url;
    }
    protected abstract QixwebUrl concreteActionUrl();
}