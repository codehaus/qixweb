package org.qixweb.time;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.qixweb.core.WebAppUrl;

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

    public static QixwebDate createFrom(WebAppUrl anUrl, String aPrefix)
    {
        return new QixwebDate(
                Integer.parseInt(anUrl.getParameter(aPrefix+DAY_PARAM)),
                Integer.parseInt(anUrl.getParameter(aPrefix+MONTH_PARAM)),
                Integer.parseInt(anUrl.getParameter(aPrefix+YEAR_PARAM))
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
