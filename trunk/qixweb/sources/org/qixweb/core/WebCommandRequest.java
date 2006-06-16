package org.qixweb.core;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.qixweb.time.QixwebDate;
import org.qixweb.time.QixwebTime;
import org.qixweb.util.DeepEquals;


public abstract class WebCommandRequest 
{
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
    
    public boolean isValid()
    {
        return areAllRight;
    }
    
    protected void addMandatoryControlText(String parameterKey, String messageWhenInvalid)
    {
        append(parameterKey, messageWhenInvalid, StringUtils.isNotEmpty(itsSubmittedValues.get(parameterKey)));
    }

    protected void addOptionalControlDateAsDDslashMMslashYYYY(String parameterKey, String messageWhenInvalid)
    {
        appendIfNotEmpty(parameterKey, messageWhenInvalid, !QixwebDate.NULL.equals(itsSubmittedValues.getAsCalendarDD_MM_YYYY(parameterKey)));
    }
    
    protected void addOptionalControlTimeAsTimeHH_colon_mm(String parameterKey, String messageWhenInvalid)
    {
        appendIfNotEmpty(parameterKey, messageWhenInvalid, !QixwebTime.NULL.equals(itsSubmittedValues.getAsTimeHH_colon_mm(parameterKey)));
    }
    
    private void appendIfNotEmpty(String key, String messageWhenInvalid, boolean isKeyValid)
    {
        if (StringUtils.isNotEmpty(itsSubmittedValues.get(key)))
            append(key, messageWhenInvalid, isKeyValid);  
    }

    private void append(String key, String messageWhenInvalid, boolean isKeyValid)
    {
        if (! isKeyValid)
            itsInvalidParameters.put(key, messageWhenInvalid);  
        
        areAllRight &= isKeyValid;
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
