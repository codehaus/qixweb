package org.qixweb.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarDateTime extends GregorianCalendar
{
    public CalendarDateTime(Calendar aCalendar)
    {
        this(aCalendar.get(Calendar.DAY_OF_MONTH), aCalendar.get(Calendar.MONTH) + 1, aCalendar.get(Calendar.YEAR), aCalendar.get(HOUR_OF_DAY), aCalendar.get(MINUTE), aCalendar.get(SECOND));
    }
    
    public CalendarDateTime(int day, int month, int year, int hours, int minutes, int seconds)
    {
        super(year, month-1, day, hours, minutes, seconds);
    }
    
	public boolean equals(Object another)
	{
		if (another == null)
			return false;
        if (!(another instanceof CalendarDateTime))
            return false;
				
		CalendarDateTime anotherDate = (CalendarDateTime) another;

		return day() == anotherDate.day() && month() == anotherDate.month() && year() == anotherDate.year() &&
		        hour() == anotherDate.hour() && minute() == anotherDate.minute() && second() == anotherDate.second();
	}
    public int hashCode()
    {
        return Integer.parseInt(String.valueOf(year())+String.valueOf(month())+String.valueOf(day())+String.valueOf(hour()));
    }

	public boolean beforeOrEquals(CalendarDateTime another)
	{
		return before(another) || equals(another); 
	}
    
	public int hour()
    {
    	return get(Calendar.HOUR_OF_DAY);
    }

    public int minute()
    {
    	return get(Calendar.MINUTE);
    }

    public int second()
    {
    	return get(Calendar.SECOND);
    }
    
    public String toString()
    {
        return DateFormatter.formatDD_MM_YYYY_HH_mm_ss(this);
    }

    public static CalendarDateTime parseYYYYMMDD_HHMMSS(String aDate) throws ParseException
    {
        return new CalendarDateTime(DateFormatter.parseyyyyMMdd_HHmmss(aDate));
    }
    
    public String asStringYYYYMMDD_HHMMSS()
    {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		return dateFormat.format(this.getTime());
    }
    
    
    public String asStringCustomDateFormat(String dateFormatAsString)
    {
              
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatAsString);
		return dateFormat.format(this.getTime());
    }

    public int day()
    {
    	return get(Calendar.DAY_OF_MONTH);
    }

    public int month()
    {
    	return get(Calendar.MONTH)+1;
    }

    public int year()
    {
    	return get(Calendar.YEAR);
    }

    public CalendarDateTime oneWeekLater()
    {
        return new CalendarDateTime(CalendarUtils.oneWeekLater(this));
    }

    public CalendarDateTime oneWeekBefore()
    {
        return new CalendarDateTime(CalendarUtils.oneWeekBefore(this));	
    }

    public CalendarDateTime moveMinutesHand(int minutesToAddOrRemove)
    {
        return new CalendarDateTime(CalendarUtils.moveMinutesHand(minutesToAddOrRemove, this));        
    }

    public CalendarDateTime moveHoursHand(int aNumberOfHours)
    {
        return new CalendarDateTime(CalendarUtils.moveHoursHand(aNumberOfHours, this));
    }
    
    public CalendarDateTime oneHourAgo()
    {
        return moveHoursHand(-1);
    }
    
    public CalendarDateTime oneMinuteAgo()
    {
        return moveMinutesHand(-1);
    }        
    
    public long elapsedHours(CalendarDateTime anotherCalendarDateTime)
    {
        return CalendarUtils.elapsedHours(this, anotherCalendarDateTime);
    }

    public CalendarDateTime oneDayBefore()
    {
        return new CalendarDateTime(CalendarUtils.oneDayBefore(this));
    }
    
}
