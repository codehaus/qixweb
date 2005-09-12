package org.qixweb.time.test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.qixweb.time.QixwebCalendar;
import org.qixweb.time.QixwebTime;
import org.qixweb.util.EqualsBehaviourVerifier;


public class TestQixwebTime extends TestQixwebCalendar
{
    public void testEquals()
    {
        QixwebTime today = new QixwebTime(16, 8, 2003, 12, 34, 23);
        QixwebTime todayAgain = new QixwebTime(16, 8, 2003, 12, 34, 23);
        QixwebTime tomorrow = new QixwebTime(17, 8, 2003, 12, 34, 23);
        EqualsBehaviourVerifier.check(today, todayAgain, tomorrow);
        assertEquals("wrong hashcode", today.hashCode(), todayAgain.hashCode());
    }

    public void testBeforeOrEquals() 
    {
        QixwebTime today = new QixwebTime(16, 8, 2003, 12, 34, 23);
        QixwebTime todayAgain = new QixwebTime(16, 8, 2003, 12, 34, 23);
        QixwebTime tomorrow = new QixwebTime(17, 8, 2003, 12, 34, 23);
        assertTrue(today.beforeOrEquals(todayAgain));
        assertTrue(todayAgain.beforeOrEquals(today));
        assertTrue(today.beforeOrEquals(tomorrow));
        assertFalse(tomorrow.beforeOrEquals(today));
    }
    
    public void testCustomDateFormat()
    {
        QixwebTime anydate = new QixwebTime(22, 2, 2004, 11, 23, 24);
        assertEquals("2004-02-22 11:23:24AM", anydate.asStringCustomDateFormat("yyyy-MM-dd hh:mm:ssaaa"));

    }
    
    public void testOneMinute()
    {
        QixwebTime anydate = new QixwebTime(22, 2, 2004, 11, 00, 24);
        QixwebTime oneMinuteAgo = new QixwebTime(22, 2, 2004, 10, 59, 24);
        assertEquals(oneMinuteAgo, anydate.oneMinuteAgo());
    }

    public void testOneHour()
    {
        QixwebTime anydate = new QixwebTime(22, 2, 2004, 00, 22, 24);
        QixwebTime oneHourAgo = new QixwebTime(21, 2, 2004, 23, 22, 24);
        assertEquals(oneHourAgo, anydate.oneHourAgo());
    }
    
    public void testElapsedHours()
    {
        QixwebTime anydate = new QixwebTime(22, 2, 2004, 00, 22, 24);
        QixwebTime oneHourAgo = new QixwebTime(21, 2, 2004, 23, 22, 24);
        assertEquals(1, anydate.elapsedHours(oneHourAgo));
    }

    public void testParseYYYYMMDD_HHMMSS() throws ParseException
    {
        assertEquals(new QixwebTime(16, 8, 2003, 12, 34, 23), QixwebTime.parseYYYYMMDD_HHMMSS("20030816-123423"));
        assertEquals(new QixwebTime(1, 12, 2004, 3, 14, 1), QixwebTime.parseYYYYMMDD_HHMMSS("20041201-031401"));
    }

    public void testAsStringYYYYMMDD_HHMMSS()
    {
        assertEquals("20030816-123423", new QixwebTime(16, 8, 2003, 12, 34, 23).asStringYYYYMMDD_HHMMSS());
        assertEquals("20041201-031401", new QixwebTime(1, 12, 2004, 3, 14, 1).asStringYYYYMMDD_HHMMSS());
    }
    
    public void testGetTimeInMillis()
    {
        QixwebTime anyDate = new QixwebTime(22, 2, 2004, 11, 23, 24);
        GregorianCalendar theSameDate = anyDate.toGregorianCalendar();
        assertEquals(theSameDate.getTimeInMillis(), anyDate.getTimeInMillis());
    }

    protected QixwebCalendar concreteInstance(int day, int month, int year) {
        return new QixwebTime(day, month, year, 0, 0, 0);
    }

    protected QixwebCalendar concreteInstance(Calendar aCalendar) {
        return new QixwebTime(aCalendar);
    }
}