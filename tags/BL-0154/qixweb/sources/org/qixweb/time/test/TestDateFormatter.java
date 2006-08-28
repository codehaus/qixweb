package org.qixweb.time.test;

import java.text.ParseException;
import java.util.*;

import junit.framework.TestCase;

import org.qixweb.time.*;

public class TestDateFormatter extends TestCase
{
    public void testFormatMMM_YY()
    {
        QixwebDate theDate = new QixwebDate(22, 2, 2002);
        assertEquals("feb-02", DateFormatter.formatMMM_YY(theDate));
        assertEquals("Feb-02", DateFormatter.formatMMM_YY(theDate, Locale.US));

        QixwebDate anotherDate = new QixwebDate(31, 12, 1999);
        assertEquals("dic-99", DateFormatter.formatMMM_YY(anotherDate));
        assertEquals("Dec-99", DateFormatter.formatMMM_YY(anotherDate, Locale.US));
    }

    public void testFormatHH_colon_mm()
    {
        QixwebTime theDate = QixwebTime.timeOnly(12, 30, 21);
        assertEquals("12:30", DateFormatter.formatHH_colon_mm(theDate));
    }

    public void testParseMMM_YY() throws ParseException
    {
        Calendar expectedDate = new GregorianCalendar(2002, Calendar.FEBRUARY, 1, 0, 0, 0);
        assertEquals(expectedDate, DateFormatter.parseMMM_YY("feb-02"));
        assertEquals(expectedDate, DateFormatter.parseMMM_YY("Feb-02", Locale.US));

        expectedDate = new GregorianCalendar(1999, Calendar.DECEMBER, 1, 0, 0, 0);
        assertEquals(expectedDate, DateFormatter.parseMMM_YY("dic-99"));
        assertEquals(expectedDate, DateFormatter.parseMMM_YY("Dec-99", Locale.US));
    }

    public void testParseyyyyMMdd_HHmmss() throws ParseException
    {
        Calendar expectedDate = new GregorianCalendar(2002, Calendar.SEPTEMBER, 23, 10, 30, 22);

        assertEquals("Wrong format", expectedDate, DateFormatter.parseyyyyMMdd_HHmmss("20020923-103022"));
    }

    public void testParseHHmm()
    {
        QixwebTime expectedTime = QixwebTime.timeOnly(10, 30, 0);
        QixwebTime time = DateFormatter.parseHH_colon_mm("10:30");

        assertEquals(expectedTime, time);
    }

    public void testParseDDslashMMslashYYYY_HH_MM()
    {
        assertEquals(new QixwebTime(5, 12, 2005, 16, 07, 00), DateFormatter.parseDDslashMMslashYYYY_HH_MMasQixwebTime("05/12/2005 16:07"));
    }

    public void testFormatyyyyMMdd_HHmmss() throws ParseException
    {
        QixwebTime aDate = new QixwebTime(DateFormatter.parseyyyyMMdd_HHmmss("20020923-103022"));
        assertEquals("Wrong format", "20020923-103022", DateFormatter.formatyyyyMMdd_HHmmss(aDate));
    }

    public void testFormatyyyyMMdd()
    {
        QixwebTime aDate = new QixwebTime(new GregorianCalendar(2002, Calendar.SEPTEMBER, 23, 10, 30, 22));
        assertEquals("Wrong format", "20020923", DateFormatter.formatyyyyMMdd(aDate));
    }

    public void testFormatyyyy_MM_dd()
    {
        QixwebTime aDate = new QixwebTime(new GregorianCalendar(2002, Calendar.SEPTEMBER, 23, 10, 30, 22));
        assertEquals("Wrong format", "2002-09-23", DateFormatter.formatyyyy_MM_dd(aDate));
    }

    public void testFormatyyyy_MM_dd_HH_mm_ss()
    {
        QixwebTime aDate = new QixwebTime(new GregorianCalendar(2002, Calendar.SEPTEMBER, 23, 10, 30, 22));
        assertEquals("Wrong format", "2002-09-23_10:30:22", DateFormatter.formatyyyy_MM_dd_HH_mm_ss(aDate));
    }

    public void testFormatyyyyMM()
    {
        QixwebTime aDate = new QixwebTime(new GregorianCalendar(2002, Calendar.SEPTEMBER, 23, 10, 30, 22));
        assertEquals("Wrong format", "200209", DateFormatter.formatyyyyMM(aDate));
    }

    public void testParseDDslashMMslashYYYY() throws ParseException
    {
        Calendar expectedDate = new GregorianCalendar(2003, Calendar.JULY, 26, 0, 0, 0);

        assertEquals("Wrong parsing for first date", expectedDate, DateFormatter.parseDDslashMMslashYYYY("26/07/2003"));
        assertEquals("Wrong parsing for one digit month", expectedDate, DateFormatter.parseDDslashMMslashYYYY("26/7/2003"));

        expectedDate = new GregorianCalendar(1999, Calendar.DECEMBER, 1, 0, 0, 0);
        assertEquals("Wrong parsing for second date", expectedDate, DateFormatter.parseDDslashMMslashYYYY("01/12/1999"));
        assertEquals("Wrong parsing for one digit day", expectedDate, DateFormatter.parseDDslashMMslashYYYY("1/12/1999"));

        try
        {
            DateFormatter.parseDDslashMMslashYYYY("12/1999");
            fail("Should throw an exception for wrong format date");
        }
        catch (ParseException expectedException)
        {
        }
        try
        {
            DateFormatter.parseDDslashMMslashYYYY("this is not a date");
            fail("Should throw an exception for not date text");
        }
        catch (ParseException expectedException)
        {
        }
    }

    public void testParseDDslashMMslashYYYYasQixwebDate()
    {
        QixwebDate expectedDate = new QixwebDate(26, 7, 2003);
        assertEquals("Wrong parsing for a valid date", expectedDate, DateFormatter.parseDDslashMMslashYYYYasQixwebDate("26/07/2003"));

        assertEquals("Wrong parsing for invalid string", QixwebCalendar.NULL, DateFormatter.parseDDslashMMslashYYYYasQixwebDate("sfklgkjlg h9u897"));
        assertEquals("Wrong parsing for empty string", QixwebCalendar.NULL, DateFormatter.parseDDslashMMslashYYYYasQixwebDate(""));
    }

    public void testFormatDD_MMM_YYYY()
    {
        QixwebDate theDate = new QixwebDate(22, 2, 2002);
        assertEquals("22-feb-2002", DateFormatter.formatDD_MMM_YYYY(theDate));
        assertEquals("22-Feb-2002", DateFormatter.formatDD_MMM_YYYY(theDate, Locale.US));

        QixwebDate anotherDate = new QixwebDate(31, 12, 1999);
        assertEquals("31-dic-1999", DateFormatter.formatDD_MMM_YYYY(anotherDate));
        assertEquals("31-Dec-1999", DateFormatter.formatDD_MMM_YYYY(anotherDate, Locale.US));
    }

    public void testDefaultLocale() throws Exception
    {
        assertEquals(Locale.ITALY, DateFormatter.defaultLocale());
        DateFormatter.changeDefaultLocale(Locale.US);
        assertEquals(Locale.US, DateFormatter.defaultLocale());
    }

    protected void setUp()
    {
        if (!DateFormatter.defaultLocale().equals(Locale.ITALY))
            DateFormatter.changeDefaultLocale(Locale.ITALY);
    }

    public void testFormatDDslashMMslashYYYY()
    {
        QixwebDate theDate = new QixwebDate(22, 2, 2002);
        assertEquals("Wrong format", "22/02/2002", DateFormatter.formatDDslashMMslashYYYY(theDate));
    }

    public void testFormatWithNullCalendar()
    {
        assertEquals("Wrong formatDD_MM_YYYY", "", DateFormatter.formatDDslashMMslashYYYY(QixwebCalendar.NULL));
        assertEquals("Wrong formatDD_MM_YYYY_HH_mm", "", DateFormatter.formatDDslashMMslashYYYY_HH_mm(QixwebCalendar.NULL));
        assertEquals("Wrong formatDD_MM_YYYY_HH_mm_ss", "", DateFormatter.formatDDslashMMslashYYYY_HH_mm_ss(QixwebCalendar.NULL));
        assertEquals("Wrong formatMMM_YY", "", DateFormatter.formatMMM_YY(QixwebCalendar.NULL));
        assertEquals("Wrong formatMMM_YY with Locale", "", DateFormatter.formatMMM_YY(QixwebCalendar.NULL, Locale.US));
        assertEquals("Wrong formatDD_MMM_YYYY with Locale", "", DateFormatter.formatDD_MMM_YYYY(QixwebCalendar.NULL, Locale.US));
        assertEquals("Wrong formatDD_MMM_YYYY", "", DateFormatter.formatDD_MMM_YYYY(QixwebCalendar.NULL));
    }

    public void testFormatDDslashMMslashYYYY_HH_mm()
    {
        QixwebCalendar theDate = new QixwebTime(22, 2, 2002, 11, 40, 00);
        assertEquals("Wrong format", "22/02/2002 11:40", DateFormatter.formatDDslashMMslashYYYY_HH_mm(theDate));

        QixwebCalendar anotherDate = new QixwebTime(22, 2, 2002, 13, 40, 00);
        assertEquals("Wrong format not 24 hours", "22/02/2002 13:40", DateFormatter.formatDDslashMMslashYYYY_HH_mm(anotherDate));
    }

    public void testFormatDDslashMMslashYYYY_HH_mm_ss()
    {
        QixwebCalendar theDate = new QixwebTime(22, 2, 2002, 11, 40, 13);
        assertEquals("Wrong format", "22/02/2002 11:40:13", DateFormatter.formatDDslashMMslashYYYY_HH_mm_ss(theDate));

        QixwebCalendar anotherDate = new QixwebTime(22, 2, 2002, 13, 40, 23);
        assertEquals("Wrong format not 24 hours", "22/02/2002 13:40:23", DateFormatter.formatDDslashMMslashYYYY_HH_mm_ss(anotherDate));
    }
}