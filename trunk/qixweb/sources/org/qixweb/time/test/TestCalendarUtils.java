package org.qixweb.time.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.qixweb.time.CalendarUtils;
import org.qixweb.util.ClassUtil;
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

    private void invoke_on_checking_with(String methodToTest, String classToTest, Calendar expectedDate, Calendar date) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException
    {
        Class clazz = Class.forName(classToTest);
        Method method = clazz.getMethod(methodToTest, null);
        assertEquals("Invoking " + classToTest + "." + methodToTest, ClassUtil.newInstance(clazz, new Class[] { Calendar.class }, new Object[] { expectedDate }), method.invoke(ClassUtil.newInstance(
                clazz, new Class[] { Calendar.class }, new Object[] { date }), null));
    }

    public void testOneDayAfterCrossingYear() throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
            InstantiationException
    {
        Calendar date = new GregorianCalendar(2002, 11, 31);
        Calendar expectedDate = new GregorianCalendar(2003, 0, 1);
        assertEquals(expectedDate, CalendarUtils.oneDayAfter(date));
        invoke_on_checking_with("oneDayAfter", "org.qixweb.time.QixwebDate", expectedDate, date);
    }

    public void testOneDayBeforeCrossingYear() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Calendar date = new GregorianCalendar(2003, 0, 1);
        Calendar expectedDate = new GregorianCalendar(2002, 11, 31);
        assertEquals(expectedDate, CalendarUtils.oneDayBefore(date));
        invoke_on_checking_with("oneDayBefore", "org.qixweb.time.QixwebDate", expectedDate, date);
        invoke_on_checking_with("oneDayBefore", "org.qixweb.time.QixwebTime", expectedDate, date);
    }

    public void testOneMonthBefore() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Calendar date = new GregorianCalendar(1999, 0, 1);
        Calendar expectedDate = new GregorianCalendar(1998, 11, 1);
        assertEquals(expectedDate, CalendarUtils.oneMonthBefore(date));
        invoke_on_checking_with("oneMonthBefore", "org.qixweb.time.QixwebDate", expectedDate, date);
    }

    public void testOneMonthLater() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Calendar expectedDate = new GregorianCalendar(1999, 0, 1);
        Calendar date = new GregorianCalendar(1998, 11, 1);
        assertEquals(expectedDate, CalendarUtils.oneMonthLater(date));
        invoke_on_checking_with("oneMonthLater", "org.qixweb.time.QixwebDate", expectedDate, date);
    }

    public void testOneWeekLaterCrossingMonth() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Calendar date = new GregorianCalendar(2003, 6, 29);
        Calendar expectedDate = new GregorianCalendar(2003, 7, 5);
        assertEquals(expectedDate, CalendarUtils.oneWeekLater(date));
        invoke_on_checking_with("oneWeekLater", "org.qixweb.time.QixwebDate", expectedDate, date);
        invoke_on_checking_with("oneWeekLater", "org.qixweb.time.QixwebTime", expectedDate, date);
    }

    public void testOneWeekBeforeCrossingMonth() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        Calendar date = new GregorianCalendar(2003, 7, 5);
        Calendar expectedDate = new GregorianCalendar(2003, 6, 29);
        assertEquals(expectedDate, CalendarUtils.oneWeekBefore(date));
        invoke_on_checking_with("oneWeekBefore", "org.qixweb.time.QixwebDate", expectedDate, date);
        invoke_on_checking_with("oneWeekBefore", "org.qixweb.time.QixwebTime", expectedDate, date);
    }

}