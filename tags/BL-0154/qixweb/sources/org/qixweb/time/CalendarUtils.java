package org.qixweb.time;

import java.util.Calendar;

// @PMD:REVIEWED:CloneMethodMustImplementCloneable: by bop on 1/25/05 4:36 PM
public class CalendarUtils
{
    public static final int MILLIS_IN_A_MINUTE = 60 * 1000;
    public static final int MINUTES_IN_A_DAY = 24 * 60;

    public static Calendar oneWeekLater(Calendar aCalendar)
    {
        Calendar clone = clone(aCalendar);
        clone.add(Calendar.DAY_OF_MONTH, 7);

        return clone;
    }

    public static Calendar oneWeekBefore(Calendar aCalendar)
    {
        Calendar clone = clone(aCalendar);
        clone.add(Calendar.DAY_OF_MONTH, -7);

        return clone;
    }

    public static Calendar backOfDays(int numberOfDays, Calendar aCalendar)
    {
        Calendar aNewDate = aCalendar;
        for (int i = 0; i < numberOfDays; i++)
            aNewDate = oneDayBefore(aNewDate);

        return aNewDate;
    }

    public static Calendar oneDayBefore(Calendar aCalendar)
    {
        Calendar clone = clone(aCalendar);
        clone.add(Calendar.DAY_OF_MONTH, -1);

        return clone;
    }

    public static Calendar oneDayAfter(Calendar aCalendar)
    {
        Calendar clone = clone(aCalendar);
        clone.add(Calendar.DAY_OF_MONTH, 1);

        return clone;
    }

    private static Calendar clone(Calendar aCalendar)
    {
        return (Calendar) aCalendar.clone();
    }

    public static Calendar oneMonthBefore(Calendar aCalendar)
    {
        Calendar clone = clone(aCalendar);
        clone.add(Calendar.MONTH, -1);

        return clone;
    }

    public static Calendar moveMinutesHand(int minutesToAddOrRemove, Calendar aCalendar)
    {
        Calendar clone = clone(aCalendar);
        clone.add(Calendar.MINUTE, minutesToAddOrRemove);

        return clone;
    }

    public static Calendar moveHoursHand(int aNumberOfHours, Calendar aCalendar)
    {
        Calendar clone = clone(aCalendar);
        clone.add(Calendar.HOUR_OF_DAY, aNumberOfHours);

        return clone;
    }

    public static long elapsedHours(Calendar aFirstDate, Calendar aSecondDate)
    {
        return Math.abs(millisToHours(aSecondDate.getTimeInMillis() - aFirstDate.getTimeInMillis()));
    }

    private static long millisToHours(long millis)
    {
        return millis / 1000 / 60 / 60;
    }

    public static int elapsedMinutes(int beginTimeInMinutes, int endTimeInMinutes)
    {
        return Math.abs(endTimeInMinutes - beginTimeInMinutes);
    }

    public static Calendar oneMonthLater(Calendar aCalendar)
    {
        Calendar clone = clone(aCalendar);
        clone.add(Calendar.MONTH, 1);

        return clone;
    }
}