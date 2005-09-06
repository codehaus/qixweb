package org.qixweb.time.test;

import java.util.Calendar;
import java.util.Date;

import org.qixweb.core.WebAppUrl;
import org.qixweb.time.CalendarDate;
import org.qixweb.util.EqualsBehaviourVerifier;
import org.qixweb.util.test.ExtendedTestCase;


public class TestCalendarDate extends ExtendedTestCase
{
	public void testCreationCallingToday()
	{
		CalendarDate today = CalendarDate.today();
		Calendar expectedValues = Calendar.getInstance();
		assertEquals(expectedValues.get(Calendar.DAY_OF_MONTH), today.day());
		assertEquals(expectedValues.get(Calendar.MONTH) + 1, today.month());
		assertEquals(expectedValues.get(Calendar.YEAR), today.year());
	}
    
    public void testCreationPassingDayMonthYear()
    {
        CalendarDate firstOfApril2003 = new CalendarDate("1", "4", "2003");
        assertEquals(1, firstOfApril2003.get(Calendar.DAY_OF_MONTH));
        assertEquals(4, firstOfApril2003.get(Calendar.MONTH) + 1);
        assertEquals(2003, firstOfApril2003.get(Calendar.YEAR));    
    }
    
    public void testCreateFromUrl() 
    {
        CalendarDate firstOfApril2003 = new CalendarDate("1", "4", "2003");
        WebAppUrl url = new WebAppUrl("");
        url.setParameter("prefix"+CalendarDate.DAY_PARAM, 1);
        url.setParameter("prefix"+CalendarDate.MONTH_PARAM, 4);
        url.setParameter("prefix"+CalendarDate.YEAR_PARAM, 2003);
        assertEquals(firstOfApril2003, CalendarDate.createFrom(url, "prefix"));
    }
    
    public void testCreationPassingJavaUtilDate()
    {
        assertEquals(CalendarDate.today(), new CalendarDate(new Date()));
    }
    
	public void testEquals()
	{
		CalendarDate today = CalendarDate.today();
		CalendarDate todayAgain = CalendarDate.today();
		todayAgain.add(Calendar.HOUR_OF_DAY, 1);
		CalendarDate tomorrow = CalendarDate.today();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		EqualsBehaviourVerifier.check(today, todayAgain, tomorrow);
	}
	
	public void testBeforeOrEquals()
	{
		CalendarDate august24_2004 = new CalendarDate(24, 8, 2004);
        assertFalse(august24_2004.beforeOrEquals(null));
		assertTrue(august24_2004.beforeOrEquals(august24_2004));

		CalendarDate sameDayButDifferentHour = new CalendarDate(24, 8, 2004);
		sameDayButDifferentHour.add(Calendar.HOUR_OF_DAY, 1);
		assertTrue(august24_2004.beforeOrEquals(sameDayButDifferentHour));
		assertTrue(sameDayButDifferentHour.beforeOrEquals(august24_2004));
		
		CalendarDate tomorrow = new CalendarDate(25, 8, 2004);
		assertTrue(august24_2004.beforeOrEquals(tomorrow));
		assertFalse(tomorrow.beforeOrEquals(august24_2004));

		CalendarDate nextMonth = new CalendarDate(24, 9, 2004);
		assertTrue(august24_2004.beforeOrEquals(nextMonth));
		assertFalse(nextMonth.beforeOrEquals(august24_2004));

		CalendarDate majorMonthButLessYear = new CalendarDate(24, 10, 1999);
		assertTrue(majorMonthButLessYear.beforeOrEquals(august24_2004));
		assertFalse(august24_2004.beforeOrEquals(majorMonthButLessYear));

		CalendarDate majorDayButLessMonthAndYear = new CalendarDate(29, 5, 1997);
		assertTrue(majorDayButLessMonthAndYear.beforeOrEquals(august24_2004));
		assertFalse(august24_2004.beforeOrEquals(majorDayButLessMonthAndYear));
	}	
	
	public void testEqualsFalseWithNull()
	{
		CalendarDate anyDate = new CalendarDate(22, 2, 2004);

		assertNotEquals(anyDate, null);
	}
    
    public void testEqualsFalseWithNonCalendarDate()
    {
        CalendarDate anyDate = new CalendarDate(22, 2, 2004);

        assertNotEquals(anyDate, new Object());
    }
	
	public void testHash()
	{
		CalendarDate today = CalendarDate.today();
		assertEquals("same date => same hash", today.hashCode(), today.hashCode());

		CalendarDate todayWithDifferentHour = CalendarDate.today();
		todayWithDifferentHour.add(Calendar.HOUR_OF_DAY, 1);
		assertEquals("same date, different hour => same hash", today.hashCode(), todayWithDifferentHour.hashCode());
		
		CalendarDate anotherDay = CalendarDate.today();
		anotherDay.add(Calendar.MONTH, 9);
		assertNotEquals("different date => different hash", new Integer(today.hashCode()), new Integer(anotherDay.hashCode()));
	}
	
	public void testKey()
	{
		assertEquals("2003-02-19", new CalendarDate(19,2,2003).key());
		assertEquals("2003-11-09", new CalendarDate(9,11,2003).key());		
		assertEquals("2003-12-23", new CalendarDate(23,12,2003).key());		
	}
	public void testBack()
	{
		CalendarDate date = new CalendarDate(1, 1, 2003);
		CalendarDate expectedDate = new CalendarDate(29, 12, 2002);
		assertEquals(expectedDate, date.backOfDays(3));
	}
		
}
