package org.qixweb.time;

import java.util.*;

import org.qixweb.core.Parameters;

public class QixwebDate extends QixwebCalendar
{
	public static final String YEAR_PARAM = "Year";
    public static final String MONTH_PARAM = "Month";
    public static final String DAY_PARAM = "Day";
    static final long serialVersionUID = 1L;
    
	public QixwebDate(String aDay, String aMonth, String aYear)
	{
		this(Integer.parseInt(aDay), Integer.parseInt(aMonth), Integer.parseInt(aYear));
	}

	public QixwebDate(int aDay, int aMonth, int aYear)
	{
		super(aDay, aMonth, aYear, 0, 0, 0);
	}
	
	public QixwebDate(Calendar aCalendar)
	{
	    this(aCalendar.get(Calendar.DAY_OF_MONTH), aCalendar.get(Calendar.MONTH) + 1, aCalendar.get(Calendar.YEAR));	
	}

    public QixwebDate(Date aDate)
    {
        this(toCalendar(aDate));    
    }

    private static Calendar toCalendar(Date aDate)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(aDate);
        return calendar;
    }
    
	public static QixwebDate today()
	{
		return new QixwebDate(Calendar.getInstance());
	}
	
    public static QixwebDate todayIfNull(QixwebCalendar date)
    {
        if (NULL.equals(date))
            return today();
        else
            return new QixwebDate(date.toGregorianCalendar());
    }
    
	public boolean beforeOrEquals(QixwebDate anotherDate)
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
        return DateFormatter.formatyyyy_MM_dd(this);
	}    

    public String toString()
    {
        return DateFormatter.formatDDslashMMslashYYYY(this);
    }

    public static QixwebCalendar createFrom(Parameters anUrl, String aPrefix)
    {
        return new QixwebDate(
                Integer.parseInt(anUrl.get(aPrefix+DAY_PARAM)),
                Integer.parseInt(anUrl.get(aPrefix+MONTH_PARAM)),
                Integer.parseInt(anUrl.get(aPrefix+YEAR_PARAM))
            );
    }

    protected QixwebCalendar newInstanceOfThis(Calendar aCalendar) {
        return new QixwebDate(aCalendar);
    }

    public int hashCode() 
    {
        return day()+month()+year();
    }
}
