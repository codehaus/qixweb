package org.qixweb.core.validation;

import org.qixweb.core.Parameters;

public abstract class WebCommandRequestControl
{
    protected Parameters itsParametersToControl;

    public WebCommandRequestControl(Parameters someParametersToControl)
    {
        itsParametersToControl = someParametersToControl;
    }

    public abstract boolean isValid(String parameterKey);
}
