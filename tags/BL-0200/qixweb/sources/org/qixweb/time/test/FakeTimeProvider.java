package org.qixweb.time.test;

import java.util.Calendar;

import org.qixweb.time.*;

public class FakeTimeProvider implements TimeProvider
{
    private Calendar itsDate;

    public QixwebTime now()
    {
        return new QixwebTime(itsDate);
    }

    public void simulateTime(Calendar aDate)
    {
        itsDate = aDate;
    }

    public void simulateTime(int aDay, int aMonth, int aYear, int hour, int min, int sec)
    {
        itsDate = Calendar.getInstance();
        itsDate.set(aYear, aMonth - 1, aDay, hour, min, sec);
    }

    public void simulateTime(QixwebTime time)
    {
        simulateTime(time.day(), time.month(), time.year(), time.hour(), time.minute(), time.second());
    }

    public void simulateADay(int aDay, int aMonth, int aYear)
    {
        simulateTime(aDay, aMonth, aYear, 0, 0, 0);
    }

    public QixwebDate calendarDate()
    {
        return new QixwebDate(itsDate);
    }

    public void advanceOfMinutes(int numberOfMinutes)
    {
        itsDate.add(Calendar.MINUTE, numberOfMinutes);
    }
    
    public void advanceOfSeconds(int numberOfSeconds)
    {
        itsDate.add(Calendar.SECOND, numberOfSeconds);
    }

    public String toString()
    {
        return now().toString();
    }
}
