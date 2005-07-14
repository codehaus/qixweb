package org.qixweb.time.test;

import java.util.*;

import org.qixweb.time.*;


public class FakeTimeProvider implements TimeProvider
{
	private Calendar itsDate;

    public CalendarDateTime now()
    {
        return new CalendarDateTime(itsDate);
    }

	public void simulateTime(Calendar aDate)
	{
		itsDate = aDate;
	}

	public void simulateTime(int aDay, int aMonth, int aYear, int hour, int min, int sec)
	{
		itsDate = GregorianCalendar.getInstance();
		itsDate.set(aYear, aMonth - 1, aDay, hour, min, sec);
	}

	public void simulateADay(int aDay, int aMonth, int aYear)
	{
		simulateTime(aDay, aMonth, aYear, 0, 0, 0);
	}
	
	public CalendarDate calendarDate()
	{
		return new CalendarDate(itsDate);
	}

    public void advance(int numberOfMinutes)
    {
        itsDate.add(Calendar.MINUTE, numberOfMinutes);
    }
}
