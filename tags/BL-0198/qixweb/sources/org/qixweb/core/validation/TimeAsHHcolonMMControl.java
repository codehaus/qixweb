package org.qixweb.core.validation;

import org.qixweb.core.Parameters;
import org.qixweb.time.QixwebTime;

public class TimeAsHHcolonMMControl extends WebCommandRequestControl
{
    public TimeAsHHcolonMMControl(Parameters someParametersToControl)
    {
        super(someParametersToControl);
    }

    public boolean isValid(String parameterKey)
    {
        return !QixwebTime.NULL.equals(itsParametersToControl.getAsTimeHH_colon_mm(parameterKey));
    }
}
