package org.qixweb.time.test;

import java.text.ParseException;

import org.qixweb.time.CalendarDateTime;
import org.qixweb.util.EqualsBehaviourVerifier;
import org.qixweb.util.test.ExtendedTestCase;


public class TestCalendarDateTime extends ExtendedTestCase
{
    public void testEquals()
    {
        CalendarDateTime today = new CalendarDateTime(16, 8, 2003, 12, 34, 23);
        CalendarDateTime todayAgain = new CalendarDateTime(16, 8, 2003, 12, 34, 23);
        CalendarDateTime tomorrow = new CalendarDateTime(17, 8, 2003, 12, 34, 23);
        EqualsBehaviourVerifier.check(today, todayAgain, tomorrow);
        assertEquals("wrong hashcode", today.hashCode(), todayAgain.hashCode());
    }

    public void testBeforeOrEquals() 
    {
        CalendarDateTime today = new CalendarDateTime(16, 8, 2003, 12, 34, 23);
        CalendarDateTime todayAgain = new CalendarDateTime(16, 8, 2003, 12, 34, 23);
        CalendarDateTime tomorrow = new CalendarDateTime(17, 8, 2003, 12, 34, 23);
        assertTrue(today.beforeOrEquals(todayAgain));
        assertTrue(todayAgain.beforeOrEquals(today));
        assertTrue(today.beforeOrEquals(tomorrow));
        assertFalse(tomorrow.beforeOrEquals(today));
    }
    
    public void testCustomDateForma()
    {
        CalendarDateTime anydate = new CalendarDateTime(22, 2, 2004, 11, 23, 24);
        assertEquals("2004-02-22 11:23:24AM", anydate.asStringCustomDateFormat("yyyy-MM-dd hh:mm:ssaaa"));

    }
    
    public void testOneMinute()
    {
        CalendarDateTime anydate = new CalendarDateTime(22, 2, 2004, 11, 00, 24);
        CalendarDateTime oneMinuteAgo = new CalendarDateTime(22, 2, 2004, 10, 59, 24);
        assertEquals(oneMinuteAgo, anydate.oneMinuteAgo());
    }

    public void testOneHour()
    {
        CalendarDateTime anydate = new CalendarDateTime(22, 2, 2004, 00, 22, 24);
        CalendarDateTime oneHourAgo = new CalendarDateTime(21, 2, 2004, 23, 22, 24);
        assertEquals(oneHourAgo, anydate.oneHourAgo());
    }

    public void testEqualsFalseWithNull()
    {
        CalendarDateTime anyDate = new CalendarDateTime(22, 2, 2004, 11, 23, 24);

        assertNotEquals(anyDate, null);
    }
    
    public void testElapsedHours()
    {
        CalendarDateTime anydate = new CalendarDateTime(22, 2, 2004, 00, 22, 24);
        CalendarDateTime oneHourAgo = new CalendarDateTime(21, 2, 2004, 23, 22, 24);
        assertEquals(1, anydate.elapsedHours(oneHourAgo));
    }
    
    public void testEqualsFalseWithNonCalendarDateTime()
    {
        CalendarDateTime anyDate = new CalendarDateTime(22, 2, 2004, 11, 23, 24);

        assertNotEquals(anyDate, new Object());
    }

    public void testParseYYYYMMDD_HHMMSS() throws ParseException
    {
        assertEquals(new CalendarDateTime(16, 8, 2003, 12, 34, 23), CalendarDateTime.parseYYYYMMDD_HHMMSS("20030816-123423"));
        assertEquals(new CalendarDateTime(1, 12, 2004, 3, 14, 1), CalendarDateTime.parseYYYYMMDD_HHMMSS("20041201-031401"));
    }

    public void testAsStringYYYYMMDD_HHMMSS()
    {
        assertEquals("20030816-123423", new CalendarDateTime(16, 8, 2003, 12, 34, 23).asStringYYYYMMDD_HHMMSS());
        assertEquals("20041201-031401", new CalendarDateTime(1, 12, 2004, 3, 14, 1).asStringYYYYMMDD_HHMMSS());
    }
}