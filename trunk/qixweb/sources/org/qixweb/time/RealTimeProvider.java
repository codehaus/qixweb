package org.qixweb.time;

import java.util.Calendar;

public class RealTimeProvider implements TimeProvider
{
    public QixwebTime now()
    {
        return new QixwebTime(Calendar.getInstance());
    }

}
