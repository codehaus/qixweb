package org.qixweb.time.test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.qixweb.time.CalendarUtils;
import org.qixweb.util.test.ExtendedTestCase;


public class TestCalendarUtils extends ExtendedTestCase
{
    public void testMoveMinutesHand()
    {
        Calendar date = new GregorianCalendar(2002, 11, 31, 20, 30, 18);
        Calendar expectedDateInTheFuture = new GregorianCalendar(2002, 11, 31, 20, 35, 18);
        assertEquals(expectedDateInTheFuture, CalendarUtils.moveMinutesHand(5, date));

        Calendar expectedDateInThePast = new GregorianCalendar(2002, 11, 31, 20, 25, 18);
        assertEquals(expectedDateInThePast, CalendarUtils.moveMinutesHand(-5, date));
    }

    public void testMoveHoursHand()
    {
        Calendar date = new GregorianCalendar(2002, 11, 31, 20, 30, 18);
        Calendar expectedDateInTheFuture = new GregorianCalendar(2002, 11, 31, 21, 30, 18);
        assertEquals(expectedDateInTheFuture, CalendarUtils.moveHoursHand(1, date));

        Calendar expectedDateInThePast = new GregorianCalendar(2002, 11, 31, 15, 30, 18);
        assertEquals(expectedDateInThePast, CalendarUtils.moveHoursHand(-5, date));

        expectedDateInThePast = new GregorianCalendar(2002, 11, 30, 20, 30, 18);
        assertEquals(expectedDateInThePast, CalendarUtils.moveHoursHand(-24, date));
    }

    public void testElapsedHours()
    {
        Calendar firstDate = new GregorianCalendar(2002, 11, 31, 20, 30, 18);
        Calendar secondDate = new GregorianCalendar(2002, 11, 31, 15, 30, 18);

        assertEquals(5, CalendarUtils.elapsedHours(firstDate, secondDate));
        assertEquals(5, CalendarUtils.elapsedHours(secondDate, firstDate));

        firstDate = new GregorianCalendar(2002, 11, 30, 20, 30, 18);
        secondDate = new GregorianCalendar(2002, 11, 31, 20, 40, 18);

        assertEquals(24, CalendarUtils.elapsedHours(firstDate, secondDate));

        firstDate = new GregorianCalendar(2002, 11, 30, 20, 30, 18);
        secondDate = new GregorianCalendar(2002, 11, 31, 20, 29, 18);

        assertEquals(23, CalendarUtils.elapsedHours(firstDate, secondDate));
    }

    public void testElapsedMinutes()
    {
        Calendar firstDate = new GregorianCalendar(2002, 11, 31, 20, 30, 18);
        Calendar secondDate = new GregorianCalendar(2002, 11, 31, 20, 31, 18);

        int firstTimeInMinutes = (int) firstDate.getTimeInMillis() / CalendarUtils.MILLIS_IN_A_MINUTE;
        int secondTimeInMinutes = (int) secondDate.getTimeInMillis() / CalendarUtils.MILLIS_IN_A_MINUTE;

        assertEquals(1, CalendarUtils.elapsedMinutes(firstTimeInMinutes, secondTimeInMinutes));
        assertEquals(1, CalendarUtils.elapsedMinutes(secondTimeInMinutes, firstTimeInMinutes));

        firstDate = new GregorianCalendar(2002, 11, 31, 20, 30, 18);
        secondDate = new GregorianCalendar(2002, 11, 31, 20, 32, 00);

        firstTimeInMinutes = (int) firstDate.getTimeInMillis() / CalendarUtils.MILLIS_IN_A_MINUTE;
        secondTimeInMinutes = (int) secondDate.getTimeInMillis() / CalendarUtils.MILLIS_IN_A_MINUTE;

        assertEquals(2, CalendarUtils.elapsedMinutes(firstTimeInMinutes, secondTimeInMinutes));
    }

    public void testOneDayAfterCrossingYear()
    {
        Calendar date = new GregorianCalendar(2002, 11, 31);
        Calendar expectedDate = new GregorianCalendar(2003, 0, 1);
        assertEquals(expectedDate, CalendarUtils.oneDayAfter(date));
    }

    public void testOneDayBeforeCrossingYear()
    {
        Calendar date = new GregorianCalendar(2003, 0, 1);
        Calendar expectedDate = new GregorianCalendar(2002, 11, 31);
        assertEquals(expectedDate, CalendarUtils.oneDayBefore(date));
    }

    public void testOneMonthBefore()
    {
        Calendar date = new GregorianCalendar(1999, 10, 17);
        Calendar expectedDate = new GregorianCalendar(1999, 9, 17);
        assertEquals(expectedDate, CalendarUtils.oneMonthBefore(date));

        date = new GregorianCalendar(1999, 0, 1);
        expectedDate = new GregorianCalendar(1998, 11, 1);
        assertEquals(expectedDate, CalendarUtils.oneMonthBefore(date));
    }

    public void testOneMonthLater()
    {
        Calendar expectedDate = new GregorianCalendar(1999, 10, 17);
        Calendar date = new GregorianCalendar(1999, 9, 17);
        assertEquals(expectedDate, CalendarUtils.oneMonthLater(date));

        expectedDate = new GregorianCalendar(1999, 0, 1);
        date = new GregorianCalendar(1998, 11, 1);
        assertEquals(expectedDate, CalendarUtils.oneMonthLater(date));
    }

    public void testOneWeekLater()
    {
        Calendar date = new GregorianCalendar(1970, 10, 17);
        Calendar expectedDate = new GregorianCalendar(1970, 10, 24);
        assertEquals(expectedDate, CalendarUtils.oneWeekLater(date));
    }

    public void testOneWeekLaterCrossingMonth()
    {
        Calendar date = new GregorianCalendar(2003, 6, 29);
        Calendar expectedDate = new GregorianCalendar(2003, 7, 5);
        assertEquals(expectedDate, CalendarUtils.oneWeekLater(date));
    }

    public void testOneWeekLaterCrossingYear()
    {
        Calendar date = new GregorianCalendar(2002, 11, 31);
        Calendar expectedDate = new GregorianCalendar(2003, 0, 7);
        assertEquals(expectedDate, CalendarUtils.oneWeekLater(date));
    }

}