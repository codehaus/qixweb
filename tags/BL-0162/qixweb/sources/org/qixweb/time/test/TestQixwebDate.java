package org.qixweb.time.test;

import java.util.*;

import org.qixweb.core.Parameters;
import org.qixweb.time.QixwebCalendar;
import org.qixweb.time.QixwebDate;
import org.qixweb.util.EqualsBehaviourVerifier;


public class TestQixwebDate extends TestQixwebCalendar
{
    public void testCoarseResolution()
    {
        Calendar fromCalendar = new GregorianCalendar(2004, 1, 2, 3, 4, 5);
        Calendar toCalendar = new GregorianCalendar(2004, 1, 2);
        assertEquals(toCalendar, new QixwebDate(fromCalendar).toGregorianCalendar());
    }
	public void testCreationCallingToday()
	{
		QixwebDate today = QixwebDate.today();
		Calendar expectedValues = Calendar.getInstance();
		assertEquals(expectedValues.get(Calendar.DAY_OF_MONTH), today.day());
		assertEquals(expectedValues.get(Calendar.MONTH) + 1, today.month());
		assertEquals(expectedValues.get(Calendar.YEAR), today.year());
	}
    
    public void testCreationPassingDayMonthYear()
    {
        QixwebDate firstOfApril2003 = new QixwebDate("1", "4", "2003");
        assertEquals(1, firstOfApril2003.day());
        assertEquals(4, firstOfApril2003.month());
        assertEquals(2003, firstOfApril2003.year());    
    }
    
    public void testCreateFromUrlWithPrefix() 
    {
        QixwebDate firstOfApril2003 = new QixwebDate("1", "4", "2003");
        Parameters parameters = new Parameters();
        parameters.set("prefix"+QixwebDate.DAY_PARAM, 1);
        parameters.set("prefix"+QixwebDate.MONTH_PARAM, 4);
        parameters.set("prefix"+QixwebDate.YEAR_PARAM, 2003);
        assertEquals(firstOfApril2003, QixwebDate.createFrom(parameters, "prefix"));
    }
    
    public void testCreationPassingJavaUtilDate()
    {
        assertEquals(QixwebDate.today(), new QixwebDate(new Date()));
    }

    public void testIgnoresAnythingSmallerThanADay()
    {
        QixwebCalendar aDay = new QixwebDate(new GregorianCalendar(2000, 1, 1, 10, 20, 30));
        assertEquals(new GregorianCalendar(2000, 1, 1), aDay.toGregorianCalendar());
        QixwebCalendar theSameDay = aDay.add(Calendar.HOUR_OF_DAY, 1);
        QixwebCalendar theDayAfter = aDay.add(Calendar.DAY_OF_MONTH, 1);
        EqualsBehaviourVerifier.check(aDay, theSameDay, theDayAfter);
    }

	public void testBeforeOrEquals()
	{
		QixwebDate august24_2004 = new QixwebDate(24, 8, 2004);
        assertFalse(august24_2004.beforeOrEquals(null));
		assertTrue(august24_2004.beforeOrEquals(august24_2004));

		QixwebDate sameDayButDifferentHour = new QixwebDate(24, 8, 2004);
		sameDayButDifferentHour.add(Calendar.HOUR_OF_DAY, 1);
		assertTrue(august24_2004.beforeOrEquals(sameDayButDifferentHour));
		assertTrue(sameDayButDifferentHour.beforeOrEquals(august24_2004));
		
		QixwebDate tomorrow = new QixwebDate(25, 8, 2004);
		assertTrue(august24_2004.beforeOrEquals(tomorrow));
		assertFalse(tomorrow.beforeOrEquals(august24_2004));

		QixwebDate nextMonth = new QixwebDate(24, 9, 2004);
		assertTrue(august24_2004.beforeOrEquals(nextMonth));
		assertFalse(nextMonth.beforeOrEquals(august24_2004));

		QixwebDate majorMonthButLessYear = new QixwebDate(24, 10, 1999);
		assertTrue(majorMonthButLessYear.beforeOrEquals(august24_2004));
		assertFalse(august24_2004.beforeOrEquals(majorMonthButLessYear));

		QixwebDate majorDayButLessMonthAndYear = new QixwebDate(29, 5, 1997);
		assertTrue(majorDayButLessMonthAndYear.beforeOrEquals(august24_2004));
		assertFalse(august24_2004.beforeOrEquals(majorDayButLessMonthAndYear));
	}
	
	public void testHash()
	{
		QixwebDate aDay = new QixwebDate(1, 2, 2003);
		assertEquals("same date => same hash", aDay.hashCode(), aDay.hashCode());

		QixwebCalendar aDifferentHour = aDay.add(Calendar.HOUR_OF_DAY, 1);
		assertEquals("same date, different hour => same hash", aDay.hashCode(), aDifferentHour.hashCode());
		
        QixwebCalendar anotherDay = aDay.add(Calendar.MONTH, 9);
		assertNotEquals("different date => different hash", new Integer(aDay.hashCode()), new Integer(anotherDay.hashCode()));
	}
	
	public void testKey()
	{
		assertEquals("2003-02-19", new QixwebDate(19,2,2003).key());
		assertEquals("2003-11-09", new QixwebDate(9,11,2003).key());		
		assertEquals("2003-12-23", new QixwebDate(23,12,2003).key());		
	}
    
    public void testTodayIfNull()
    {
        assertEquals(QixwebDate.today(), QixwebDate.todayIfNull(QixwebCalendar.NULL));
        assertEquals(new QixwebDate(12, 3, 2004), QixwebDate.todayIfNull(new QixwebDate(12, 3, 2004)));
    }
    
	public void testBack()
	{
		QixwebDate date = new QixwebDate(1, 1, 2003);
		QixwebDate expectedDate = new QixwebDate(29, 12, 2002);
		assertEquals(expectedDate, date.backOfDays(3));
	}

    public void testConversionToGregorianCalendar()
    {
        final int month = 2;
        QixwebDate aDay = new QixwebDate(1, month, 2003);
        assertEquals(new GregorianCalendar(2003, month - 1, 1, 0, 0, 0), aDay.toGregorianCalendar());
    }

    protected QixwebCalendar concreteInstance(int day, int month, int year) {
        return new QixwebDate(day, month, year);
    }

    protected QixwebCalendar concreteInstance(Calendar aCalendar) {
        return new QixwebDate(aCalendar);
    }
}
