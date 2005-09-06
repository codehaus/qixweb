package org.qixweb.time;

import java.util.Calendar;


public class RealTimeProvider implements TimeProvider
{
	public CalendarDateTime now()
	{
		return new CalendarDateTime(Calendar.getInstance());
	}
	
	
}
