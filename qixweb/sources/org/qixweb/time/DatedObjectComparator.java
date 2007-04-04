package org.qixweb.time;

import java.util.Comparator;




public class DatedObjectComparator implements Comparator
{
	public static final int ASCENDING_ORDER = QixwebCalendarComparator.ASCENDING_ORDER;
	public static final int DESCENDING_ORDER = QixwebCalendarComparator.DESCENDING_ORDER;

	private QixwebCalendarComparator itsCalendarDateComparator;

	public DatedObjectComparator(int anOrder)
	{
		itsCalendarDateComparator = new QixwebCalendarComparator(anOrder);
	}

	public int compare(Object first, Object second)
	{
		DateComparable firstDateComparable = (DateComparable)first;
		DateComparable secondDateComparable = (DateComparable)second;
		
		return itsCalendarDateComparator.compare(firstDateComparable.date(), secondDateComparable.date());
	}
}
