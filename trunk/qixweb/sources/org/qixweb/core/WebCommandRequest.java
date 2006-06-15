package org.qixweb.core;

import org.qixweb.util.DeepEquals;


public abstract class WebCommandRequest 
{
    protected final Parameters itsSubmittedValues;

    protected WebCommandRequest(Parameters submittedValues)
    {
        itsSubmittedValues = submittedValues;        
    }

    public abstract boolean isValid();
    public abstract Browsable destinationWhenNotValid(QixwebEnvironment environment);
    
    public boolean equals(Object other)
    {
        return DeepEquals.equals(this, other);
    }
}
