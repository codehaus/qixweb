package org.qixweb.core.validation;

import org.qixweb.core.Parameters;
import org.qixweb.time.QixwebDate;

public class DateAsDDslashMMslashYYYYControl extends WebCommandRequestControl
{
    public DateAsDDslashMMslashYYYYControl(Parameters someParametersToControl)
    {
        super(someParametersToControl);        
    }

    public boolean isValid(String parameterKey)
    {
        return !QixwebDate.NULL.equals(itsParametersToControl.getAsCalendarDD_MM_YYYY(parameterKey)); 
    }

}
