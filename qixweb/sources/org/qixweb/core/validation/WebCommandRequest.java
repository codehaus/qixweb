package org.qixweb.core.validation;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.qixweb.core.*;
import org.qixweb.util.DeepEquals;


public abstract class WebCommandRequest 
{
    public static final boolean MANDATORY = true; 
    public static final boolean OPTIONAL = !MANDATORY; 
    
    protected final Parameters itsSubmittedValues;
    private boolean areAllRight;
    private HashMap itsInvalidParameters;

    protected WebCommandRequest(Parameters submittedValues)
    {
        itsSubmittedValues = submittedValues;        
        areAllRight = true;
        itsInvalidParameters = new HashMap();
    }

    public abstract Browsable destinationWhenNotValid(QixwebEnvironment environment);
    
    private void append(String key, String messageWhenInvalid, boolean isKeyValid)
    {
        if (! isKeyValid)
            itsInvalidParameters.put(key, messageWhenInvalid);  
        
        areAllRight &= isKeyValid;
    }
    
    protected void addControl(WebCommandRequestControl control, boolean mandatoryFlag, String parameterKey, String messageWhenInvalid)
    {
        if (mandatoryFlag == MANDATORY ||
            (mandatoryFlag == OPTIONAL && StringUtils.isNotEmpty(itsSubmittedValues.get(parameterKey))))
            append(parameterKey, messageWhenInvalid, control.isValid(parameterKey)); 
    }
    
    public boolean isValid()
    {
        return areAllRight;
    }
    
    public HashMap invalidParameters()
    {
        return itsInvalidParameters;
    }
    
    public boolean equals(Object other)
    {
        return DeepEquals.equals(this, other);
    }

}
