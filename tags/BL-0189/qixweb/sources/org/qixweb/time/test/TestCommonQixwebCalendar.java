package org.qixweb.time.test;

import org.qixweb.time.*;
import org.qixweb.util.test.ExtendedTestCase;

public class TestCommonQixwebCalendar extends ExtendedTestCase
{
    public void testParse()
    {
        assertEquals(new QixwebTime(5, 12, 2005, 16, 07, 23), QixwebCalendar.parse("05/12/2005 16:07:23"));
        assertEquals(new QixwebDate(5, 12, 2005), QixwebCalendar.parse("05/12/2005"));
        assertEquals(QixwebCalendar.NULL, QixwebCalendar.parse("foo bar"));
    }

    public void testEquivalenceBetweenDateAndTime()
    {
        assertEquals(new QixwebDate(5, 12, 2005), new QixwebTime(5, 12, 2005, 0, 0, 0));
    }

    public void testToStringInvariance()
    {
        QixwebCalendar calendar = QixwebCalendar.parse("05/12/2005 16:07:23");
        assertEquals("05/12/2005 16:07:23", calendar.toString());
        calendar = QixwebCalendar.parse("05/12/2005");
        assertEquals("05/12/2005", calendar.toString());
    }
}
