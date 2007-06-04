package org.qixweb.core.validation;

import org.apache.commons.lang.ArrayUtils;
import org.qixweb.core.Parameters;

public class ByteArrayControl extends WebCommandRequestControl
{
    public ByteArrayControl(Parameters someParametersToControl)
    {
        super(someParametersToControl);
    }

    public boolean isValid(String parameterKey)
    {
        return !ArrayUtils.isEmpty(itsParametersToControl.getAsByteArray(parameterKey));
    }

}
