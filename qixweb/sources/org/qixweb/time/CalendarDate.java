package org.qixweb.time;

import java.util.*;

import org.qixweb.core.WebAppUrl;

public class CalendarDate extends GregorianCalendar
{
	public static final String YEAR_PARAM = "Year";
    public static final String MONTH_PARAM = "Month";
    public static final String DAY_PARAM = "Day";
    static final long serialVersionUID = 1L;

	public CalendarDate(String aDay, String aMonth, String aYear)
	{
		this(Integer.parseInt(aDay), Integer.parseInt(aMonth), Integer.parseInt(aYear));
	}

	public CalendarDate(int aDay, int aMonth, int aYear)
	{
		super(aYear, aMonth-1, aDay, 0, 0, 0);
	}
	
	public CalendarDate(Calendar aCalendar)
	{
	    this(aCalendar.get(Calendar.DAY_OF_MONTH), aCalendar.get(Calendar.MONTH) + 1, aCalendar.get(Calendar.YEAR));	
	}

    public CalendarDate(Date aDate)
    {
        this(toCalendar(aDate));    
    }

    private static Calendar toCalendar(Date aDate)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(aDate);
        return calendar;
    }
    
	public static CalendarDate today()
	{
		return new CalendarDate(Calendar.getInstance());
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

	public boolean equals(Object another)
	{
		if (another == null)
			return false;
        if (!(another instanceof CalendarDate))
			return false;
        
		CalendarDate anotherDate = (CalendarDate) another;
		return day() == anotherDate.day() && month() == anotherDate.month() && year() == anotherDate.year();
	}
	
	public boolean beforeOrEquals(CalendarDate anotherDate)
	{
		if (anotherDate == null)
        	return false;
        
		if (year() < anotherDate.year())
        	return true;
		else if (year() == anotherDate.year() && month() < anotherDate.month())
        	return true;
		else
		    return year() == anotherDate.year() && month() == anotherDate.month() && day() <= anotherDate.day(); 
	}
	
	

	public String key()
	{
		String month = Integer.toString(month());
		if (month() < 10)
			month = "0" + month;
			
		String day = Integer.toString(day());
		if (day() < 10)
			day = "0" + day;

			
		return Integer.toString(year())+"-"+month + "-" + day;
	}    

    public String toString()
    {
        return DateFormatter.formatDD_MM_YYYY_HH_mm(this);
    }
	public CalendarDate oneWeekLater()
	{
	    return new CalendarDate(CalendarUtils.oneWeekLater(this));	
	}
	
	public CalendarDate oneWeekBefore()
	{
	    return new CalendarDate(CalendarUtils.oneWeekBefore(this));	
	}	
	public CalendarDate oneDayAfter()
	{
	    return new CalendarDate(CalendarUtils.oneDayAfter(this));
	}
	
	public CalendarDate oneDayBefore()
	{
	    return new CalendarDate(CalendarUtils.oneDayBefore(this));
	}
	
	public CalendarDate oneMonthBefore()
	{
	    return new CalendarDate(CalendarUtils.oneMonthBefore(this));
	}
	
	public CalendarDate backOfDays(int numberOfDays)
	{
	    return new CalendarDate(CalendarUtils.backOfDays(numberOfDays, this));
	} 	

	public int hashCode() 
	{
		return day()+month()+year();
	}

    public CalendarDate oneMonthLater()
    {
        return new CalendarDate(CalendarUtils.oneMonthLater(this));
    }

    public static CalendarDate createFrom(WebAppUrl anUrl, String aPrefix)
    {
        return new CalendarDate(
                Integer.parseInt(anUrl.getParameter(aPrefix+DAY_PARAM)),
                Integer.parseInt(anUrl.getParameter(aPrefix+MONTH_PARAM)),
                Integer.parseInt(anUrl.getParameter(aPrefix+YEAR_PARAM))
            );
    }
}
