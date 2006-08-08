package org.qixweb.core.validation;

import org.qixweb.core.Parameters;

public class DoubleControl extends WebCommandRequestControl
{
    public DoubleControl(Parameters someParametersToControl)
    {
        super(someParametersToControl);        
    }

    public boolean isValid(String parameterKey)
    {
        return !Double.isNaN(itsParametersToControl.getAsDouble(parameterKey, Double.NaN)); 
    }

}
