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
    private HashMap itsMessageForInvalidParameters;

    protected WebCommandRequest(Parameters submittedValues)
    {
        itsSubmittedValues = submittedValues;        
        areAllRight = true;
        itsMessageForInvalidParameters = new HashMap();
    }

    public abstract Browsable destinationWhenNotValid(QixwebEnvironment environment);
    

    
    protected void addControl(WebCommandRequestControl control, boolean mandatoryFlag, String parameterKey, String messageWhenInvalid)
    {
        if (mandatoryFlag == MANDATORY || itsSubmittedValues.isNotEmpty(parameterKey))
        {
            boolean isKeyValid = control.isValid(parameterKey);
            if (!isKeyValid)
                itsMessageForInvalidParameters.put(parameterKey, messageWhenInvalid);  
            
            areAllRight &= isKeyValid;
        } 
    }
    
    public boolean isValid()
    {
        return areAllRight;
    }
    
    public HashMap messagesForInvalidParameters()
    {
        return itsMessageForInvalidParameters;
    }
    
    public boolean equals(Object other)
    {
        return DeepEquals.equals(this, other);
    }

}
