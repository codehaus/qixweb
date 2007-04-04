package org.qixweb.time.test;

import junit.framework.TestCase;

import org.qixweb.time.*;

public class TestQixwebCalendarComparator extends TestCase
{
	private QixwebCalendarComparator itsAscendingComparator;
    public TestQixwebCalendarComparator(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        itsAscendingComparator = new QixwebCalendarComparator(QixwebCalendarComparator.ASCENDING_ORDER);
    }

	private int ascendingCompare(QixwebCalendar firstDate, QixwebCalendar secondDate)
	{
		return itsAscendingComparator.compare(firstDate, secondDate);
	}

    public void testConsiderDay()
    {
        QixwebCalendar date = new QixwebDate(15, 1, 2003);
        QixwebCalendar dayAfter = new QixwebDate(16, 1, 2003);

        assertEquals(-1, ascendingCompare(date, dayAfter));
        assertEquals(1, ascendingCompare(dayAfter, date));
    }
    
	public void testOrder()
	{
		QixwebCalendar date = new QixwebDate(15, 1, 2003);

		QixwebCalendar dayAfter = new QixwebDate(16, 1, 2003);

		assertEquals(-ascendingCompare(date, dayAfter), descendingCompare(date, dayAfter));
		assertEquals(-ascendingCompare(dayAfter, date), descendingCompare(dayAfter, date));
	}

	private int descendingCompare(QixwebCalendar firstDate, QixwebCalendar secondDate)
	{
		QixwebCalendarComparator descendingComparator = new QixwebCalendarComparator(DatedObjectComparator.DESCENDING_ORDER);
		
		return descendingComparator.compare(firstDate, secondDate);
	}
        
    public void testConsiderMonth()
    {
        QixwebCalendar date = new QixwebDate(15, 1, 2003);
        QixwebCalendar monthAfter = new QixwebDate(15, 2, 2003);
        
        assertEquals(-1, ascendingCompare(date, monthAfter));
        assertEquals(1, ascendingCompare(monthAfter, date));
    }
        
    public void testConsiderYear()
    {
        QixwebCalendar date = new QixwebDate(15, 1, 2003);
        QixwebCalendar yearBefore = new QixwebDate(15, 1, 2002);        
        
        assertEquals(1, ascendingCompare(date, yearBefore));
        assertEquals(-1, ascendingCompare(yearBefore, date));
    }    
}
