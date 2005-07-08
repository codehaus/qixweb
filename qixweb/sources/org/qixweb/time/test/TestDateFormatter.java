package org.qixweb.time.test;
import java.text.ParseException;
import java.util.*;

import junit.framework.TestCase;

import org.qixweb.time.CalendarDate;
import org.qixweb.time.DateFormatter;


public class TestDateFormatter extends TestCase
{
	public void testFormatMMM_YY()
	{
		Calendar theDate = Calendar.getInstance();

		theDate.set(2002, Calendar.FEBRUARY, 22);
		assertEquals("feb-02", DateFormatter.formatMMM_YY(theDate));
        assertEquals("Feb-02", DateFormatter.formatMMM_YY(theDate, Locale.US));

		theDate.set(1999, Calendar.DECEMBER, 31);
		assertEquals("dic-99", DateFormatter.formatMMM_YY(theDate));
        assertEquals("Dec-99", DateFormatter.formatMMM_YY(theDate, Locale.US));
	}
	
	public void testParseMMM_YY() throws ParseException
	{
		Calendar expectedDate =  new GregorianCalendar(2002, Calendar.FEBRUARY, 1, 0, 0, 0);
		assertEquals(expectedDate, DateFormatter.parseMMM_YY("feb-02"));
        assertEquals(expectedDate, DateFormatter.parseMMM_YY("Feb-02", Locale.US));

		expectedDate = new GregorianCalendar(1999, Calendar.DECEMBER, 1, 0, 0, 0);
		assertEquals(expectedDate, DateFormatter.parseMMM_YY("dic-99"));
        assertEquals(expectedDate, DateFormatter.parseMMM_YY("Dec-99", Locale.US));
	}
	
	public void testParseyyyyMMdd_HHmmss() throws ParseException
	{
		Calendar expectedDate =  new GregorianCalendar(2002, Calendar.SEPTEMBER, 23, 10, 30, 22);
		
		assertEquals("Wrong format", expectedDate, DateFormatter.parseyyyyMMdd_HHmmss("20020923-103022"));
	}	
	
	public void testFormatyyyyMMdd_HHmmss() throws ParseException
	{
		assertEquals("Wrong format", "20020923-103022", DateFormatter.formatyyyyMMdd_HHmmss(DateFormatter.parseyyyyMMdd_HHmmss("20020923-103022")));
	}	

	public void testFormatyyyyMMdd()
	{
		Calendar date =  new GregorianCalendar(2002, Calendar.SEPTEMBER, 23, 10, 30, 22);		
		assertEquals("Wrong format", "20020923", DateFormatter.formatyyyyMMdd(date));
	}	

	public void testFormatyyyyMM()
	{
		Calendar date =  new GregorianCalendar(2002, Calendar.SEPTEMBER, 23, 10, 30, 22);		
		assertEquals("Wrong format", "200209", DateFormatter.formatyyyyMM(date));
	}	
	
	public void testParseDD_MM_YYYY() throws ParseException
	{
		Calendar expectedDate =  new GregorianCalendar(2003, Calendar.JULY, 26, 0, 0, 0);
		
		assertEquals("Wrong parsing for first date", expectedDate, DateFormatter.parseDD_MM_YYYY("26/07/2003"));
		assertEquals("Wrong parsing for one digit month", expectedDate, DateFormatter.parseDD_MM_YYYY("26/7/2003"));		


		expectedDate = new GregorianCalendar(1999, Calendar.DECEMBER, 1, 0, 0, 0);
		assertEquals("Wrong parsing for second date", expectedDate, DateFormatter.parseDD_MM_YYYY("01/12/1999"));
		assertEquals("Wrong parsing for one digit day", expectedDate, DateFormatter.parseDD_MM_YYYY("1/12/1999"));		

		try
		{
			DateFormatter.parseDD_MM_YYYY("12/1999");
			fail("Should throw an exception for wrong format date");		
		}
		catch(ParseException expectedException)
		{
		}
	}
		
    public void testParseDD_MM_YYYYasCalendarDate()
    {
        CalendarDate expectedDate =  new CalendarDate(26, 7, 2003);
        assertEquals("Wrong parsing for a valid date", expectedDate, DateFormatter.parseDD_MM_YYYYasCalendarDate("26/07/2003"));

        assertNull("Wrong parsing for invalid string", DateFormatter.parseDD_MM_YYYYasCalendarDate("sfklgkjlg h9u897"));
        assertNull("Wrong parsing for empty string", DateFormatter.parseDD_MM_YYYYasCalendarDate(""));
    }

	public TestDateFormatter(String name)
	{
		super(name);
	}

	public void testFormatDD_MMM_YYYY()
	{
		Calendar theDate = Calendar.getInstance();

		theDate.set(2002, Calendar.FEBRUARY, 22);
		assertEquals("22-feb-2002", DateFormatter.formatDD_MMM_YYYY(theDate));
        assertEquals("22-Feb-2002", DateFormatter.formatDD_MMM_YYYY(theDate, Locale.US));

		theDate.set(1999, Calendar.DECEMBER, 31);
		assertEquals("31-dic-1999", DateFormatter.formatDD_MMM_YYYY(theDate));
        assertEquals("31-Dec-1999", DateFormatter.formatDD_MMM_YYYY(theDate, Locale.US));
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

	public void testFormatDD_MM_YYYY()
	{
		Calendar theDate = Calendar.getInstance();

		theDate.set(2002, Calendar.FEBRUARY, 22);
		assertEquals("Wrong format", "22/02/2002", DateFormatter.formatDD_MM_YYYY(theDate));
	}

    public void testFormatWithNullCalendar()
    {
        assertEquals("Wrong formatDD_MM_YYYY", "", DateFormatter.formatDD_MM_YYYY(null));
        assertEquals("Wrong formatDD_MM_YYYY_HH_mm", "", DateFormatter.formatDD_MM_YYYY_HH_mm(null));
        assertEquals("Wrong formatDD_MM_YYYY_HH_mm_ss", "", DateFormatter.formatDD_MM_YYYY_HH_mm_ss(null));
        assertEquals("Wrong formatMMM_YY", "", DateFormatter.formatMMM_YY(null));
        assertEquals("Wrong formatMMM_YY with Locale", "", DateFormatter.formatMMM_YY(null, Locale.US));
        assertEquals("Wrong formatDD_MMM_YYYY with Locale", "", DateFormatter.formatDD_MMM_YYYY(null, Locale.US));
        assertEquals("Wrong formatDD_MMM_YYYY", "", DateFormatter.formatDD_MMM_YYYY(null));
    }

	public void testFormatDD_MM_YYYY_HH_mm()
	{
		Calendar theDate = Calendar.getInstance();

		theDate.set(2002, Calendar.FEBRUARY, 22, 11, 40);
		assertEquals("Wrong format", "22/02/2002 11:40", DateFormatter.formatDD_MM_YYYY_HH_mm(theDate));

		theDate.set(2002, Calendar.FEBRUARY, 22, 13, 40);
		assertEquals("Wrong format not 24 hours", "22/02/2002 13:40", DateFormatter.formatDD_MM_YYYY_HH_mm(theDate));

	}

    public void testFormatDD_MM_YYYY_HH_mm_ss()
    {
        Calendar theDate = Calendar.getInstance();

        theDate.set(2002, Calendar.FEBRUARY, 22, 11, 40, 13);
        assertEquals("Wrong format", "22/02/2002 11:40:13", DateFormatter.formatDD_MM_YYYY_HH_mm_ss(theDate));

        theDate.set(2002, Calendar.FEBRUARY, 22, 13, 40, 23);
        assertEquals("Wrong format not 24 hours", "22/02/2002 13:40:23", DateFormatter.formatDD_MM_YYYY_HH_mm_ss(theDate));

    }
}