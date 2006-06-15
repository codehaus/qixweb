package org.qixweb.core;

public class AlwaysValidWebCommandRequest extends WebCommandRequest
{
    protected AlwaysValidWebCommandRequest(Parameters submittedValues)
    {
        super(submittedValues);        
    }

    public AlwaysValidWebCommandRequest()
    {
        this(null);
    }

    public boolean isValid()
    {
        return true;
    }

    public Browsable destinationWhenNotValid(QixwebEnvironment notUsed)
    {
        return null;
    }
    
    public boolean equals(Object other)
    {
        return other instanceof AlwaysValidWebCommandRequest;
    }
}
