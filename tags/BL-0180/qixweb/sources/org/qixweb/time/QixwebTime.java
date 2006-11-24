package org.qixweb.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QixwebTime extends QixwebCalendar
{
    public static final QixwebTime NULL = new QixwebTime(0, 1, 0, 0, 0, 0)
    {
        protected QixwebCalendar newInstanceOfThis(Calendar aCalendar)
        {
            return this;
        }

        public String format(SimpleDateFormat dateFormat)
        {
            return "";
        }

        public String toString()
        {
            return "NullCalendar";
        }

        public String key()
        {
            return toString();
        }
    };
    
    public QixwebTime(Calendar aCalendar)
    {
        this(aCalendar.get(Calendar.DAY_OF_MONTH), aCalendar.get(Calendar.MONTH) + 1, aCalendar.get(Calendar.YEAR), aCalendar.get(Calendar.HOUR_OF_DAY), aCalendar.get(Calendar.MINUTE), aCalendar.get(Calendar.SECOND));
    }
    
    public QixwebTime(int day, int month, int year, int hours, int minutes, int seconds)
    {
        super(day, month, year, hours, minutes, seconds);
    }

    public static QixwebTime timeOnly(int hours, int minutes, int seconds)
    {
        return new QixwebTime(0, 1, 0, hours, minutes, seconds);
    }
    
	public boolean beforeOrEquals(QixwebTime another)
	{
		return toGregorianCalendar().before(another.toGregorianCalendar()) || equals(another); 
	}
    
	public int hour()
    {
    	return toGregorianCalendar().get(Calendar.HOUR_OF_DAY);
    }

    public int minute()
    {
    	return toGregorianCalendar().get(Calendar.MINUTE);
    }

    public int second()
    {
    	return toGregorianCalendar().get(Calendar.SECOND);
    }
    
    public String key()
    {
        return DateFormatter.formatyyyy_MM_dd_HH_mm_ss(this);
    }
    
    public String toString()
    {
        return DateFormatter.formatDDslashMMslashYYYY_HH_mm_ss(this);
    }

    public static QixwebTime parseYYYYMMDD_HHMMSS(String aDate) throws ParseException
    {
        return new QixwebTime(DateFormatter.parseyyyyMMdd_HHmmss(aDate));
    }
    
    public String asStringYYYYMMDD_HHMMSS()
    {
		return asStringCustomDateFormat("yyyyMMdd-HHmmss");
    }
    
    public String asStringDDSlashMMSlashYearBlankHHColonMM()
    {
        return asStringCustomDateFormat("dd/MM/yyyy hh:mm");
    }
    
    public String asStringCustomDateFormat(String dateFormatAsString)
    {              
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatAsString);
		return dateFormat.format(toGregorianCalendar().getTime());
    }

    public QixwebTime moveMinutesHand(int minutesToAddOrRemove)
    {
        return new QixwebTime(CalendarUtils.moveMinutesHand(minutesToAddOrRemove, toGregorianCalendar()));        
    }

    public QixwebTime moveHoursHand(int aNumberOfHours)
    {
        return new QixwebTime(CalendarUtils.moveHoursHand(aNumberOfHours, toGregorianCalendar()));
    }
    
    public QixwebTime oneHourAgo()
    {
        return moveHoursHand(-1);
    }
    
    public QixwebTime oneMinuteAgo()
    {
        return moveMinutesHand(-1);
    }        
    
    public long elapsedHours(QixwebTime anotherCalendarDateTime)
    {
        return CalendarUtils.elapsedHours(toGregorianCalendar(), anotherCalendarDateTime.toGregorianCalendar());
    }

    public long getTimeInMillis()
    {
        return toGregorianCalendar().getTimeInMillis();
    }

    protected QixwebCalendar newInstanceOfThis(Calendar aCalendar)
    {
        return new QixwebTime(aCalendar);
    }
    
    public int hashCode()
    {
        return Integer.parseInt(String.valueOf(year())+String.valueOf(month())+String.valueOf(day())+String.valueOf(hour()));
    }

    public QixwebTime endOfTheDay()
    {
        return new QixwebTime(day(), month(), year(), 23, 59, 59);
    }

    public QixwebTime beginningOfTheDay()
    {
        return new QixwebTime(day(), month(), year(), 0, 0, 0);
    }


}
