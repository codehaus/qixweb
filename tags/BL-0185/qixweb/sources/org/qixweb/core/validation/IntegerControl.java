package org.qixweb.core.validation;

import org.qixweb.core.Parameters;

public class IntegerControl extends WebCommandRequestControl
{
    public IntegerControl(Parameters someParametersToControl)
    {
        super(someParametersToControl);        
    }

    public boolean isValid(String parameterKey)
    {
        Integer defaultValue = null;
        return defaultValue != itsParametersToControl.getAsInteger(parameterKey, defaultValue); 
    }

}
