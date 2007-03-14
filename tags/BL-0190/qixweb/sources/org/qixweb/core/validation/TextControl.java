package org.qixweb.core.validation;

import org.qixweb.core.Parameters;

public class TextControl extends WebCommandRequestControl
{
    public TextControl(Parameters someParametersToControl)
    {
        super(someParametersToControl);
    }

    public boolean isValid(String parameterKey)
    {
        return itsParametersToControl.isNotEmpty(parameterKey);
    }

}
