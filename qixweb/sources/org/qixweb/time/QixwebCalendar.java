package org.qixweb.time;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class QixwebCalendar implements Serializable
{

    public static final QixwebCalendar NULL = new QixwebCalendar(0, 0, 0, 0, 0, 0)
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
            return "NullDate";
        }

        public String key()
        {
            return toString();
        }
    };

    private final GregorianCalendar itsCalendar;

    public QixwebCalendar(int day, int month, int year, int hours, int minutes, int seconds)
    {
        itsCalendar = new GregorianCalendar(year, month - 1, day, hours, minutes, seconds);
    }

    public final GregorianCalendar toGregorianCalendar()
    {
        return (GregorianCalendar) itsCalendar.clone();
    }

    public final int day()
    {
        return toGregorianCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public final int month()
    {
        return toGregorianCalendar().get(Calendar.MONTH) + 1;
    }

    public final int year()
    {
        return toGregorianCalendar().get(Calendar.YEAR);
    }

    public abstract String key();

    protected abstract QixwebCalendar newInstanceOfThis(Calendar aCalendar);

    public final QixwebCalendar oneWeekLater()
    {
        return newInstanceOfThis(CalendarUtils.oneWeekLater(toGregorianCalendar()));
    }

    public final QixwebCalendar oneWeekBefore()
    {
        return newInstanceOfThis(CalendarUtils.oneWeekBefore(toGregorianCalendar()));
    }

    public final QixwebCalendar oneDayAfter()
    {
        return newInstanceOfThis(CalendarUtils.oneDayAfter(toGregorianCalendar()));
    }

    public final QixwebCalendar oneDayBefore()
    {
        return newInstanceOfThis(CalendarUtils.oneDayBefore(toGregorianCalendar()));
    }

    public final QixwebCalendar oneMonthBefore()
    {
        return newInstanceOfThis(CalendarUtils.oneMonthBefore(toGregorianCalendar()));
    }

    public final QixwebCalendar backOfDays(int numberOfDays)
    {
        return newInstanceOfThis(CalendarUtils.backOfDays(numberOfDays, toGregorianCalendar()));
    }

    public final QixwebCalendar oneMonthLater()
    {
        return newInstanceOfThis(CalendarUtils.oneMonthLater(toGregorianCalendar()));
    }

    public QixwebCalendar add(int field, int amount)
    {
        Calendar theCalendar = toGregorianCalendar();
        theCalendar.add(field, amount);
        return newInstanceOfThis(theCalendar);
    }

    public boolean equals(Object another)
    {
        if (another == null)
            return false;
        if (!(another instanceof QixwebCalendar))
            return false;
        QixwebCalendar anotherDate = (QixwebCalendar) another;
        return toGregorianCalendar().equals(anotherDate.toGregorianCalendar());
    }

    public String format(SimpleDateFormat dateFormat)
    {
        return dateFormat.format(getTime());
    }

    public Date getTime()
    {
        return toGregorianCalendar().getTime();
    }

    public boolean after(QixwebCalendar aDate)
    {
        return toGregorianCalendar().after(aDate.toGregorianCalendar());
    }

    public boolean before(QixwebCalendar aDate)
    {
        return toGregorianCalendar().before(aDate.toGregorianCalendar());
    }
}
