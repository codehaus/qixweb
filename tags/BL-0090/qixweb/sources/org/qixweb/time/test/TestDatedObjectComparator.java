package org.qixweb.time.test;

import junit.framework.TestCase;

import org.qixweb.time.*;


public class TestDatedObjectComparator extends TestCase
{
	class SampleDateComparable implements DateComparable
	{
		private QixwebDate itsDate;
	
		public SampleDateComparable(QixwebDate aDate)
		{
			itsDate = aDate;
		}

		public QixwebCalendar date()
		{
			return itsDate;
		}
	}
		
	
	public void testConsiderDay()
	{
		QixwebCalendarComparator calendarDateComparator = new QixwebCalendarComparator(QixwebCalendarComparator.ASCENDING_ORDER);
		DatedObjectComparator datedObjectComparator = new DatedObjectComparator(DatedObjectComparator.ASCENDING_ORDER);
		QixwebDate date = new QixwebDate(15, 1, 2003);
		QixwebDate dayAfter = new QixwebDate(16, 1, 2003);		

		assertEquals
		(
			"DatedObjectComparator must delegate the comparison to CalendarDateComparator",
			calendarDateComparator.compare(date, dayAfter),
			datedObjectComparator.compare(new SampleDateComparable(date), new SampleDateComparable(dayAfter))
		);
		assertEquals
		(
			"DatedObjectComparator must delegate the comparison to CalendarDateComparator",
			calendarDateComparator.compare(dayAfter, date),
			datedObjectComparator.compare(new SampleDateComparable(dayAfter), new SampleDateComparable(date))
		);
	}
    
}
