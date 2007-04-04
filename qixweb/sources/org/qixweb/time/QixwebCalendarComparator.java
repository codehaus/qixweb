package org.qixweb.time;

import java.util.Comparator;


public class QixwebCalendarComparator implements Comparator
{
    public static final int ASCENDING_ORDER = 1;
    public static final int DESCENDING_ORDER = 2;

    private int itsOrder;

    public QixwebCalendarComparator(int anOrder)
    {
        itsOrder = anOrder;
    }

    public int compare(Object first, Object second)
    {
        if (itsOrder == ASCENDING_ORDER)
            return ascendingCompare(first, second);
        else
            return -ascendingCompare(first, second);
    }

    private int ascendingCompare(Object first, Object second)
    {
        QixwebCalendar firstCalendarDate = (QixwebCalendar) first;
        QixwebCalendar secondCalendarDate = (QixwebCalendar) second;

        if (firstCalendarDate.equals(secondCalendarDate))
            return 0;
        else if (firstCalendarDate.after(secondCalendarDate))
            return 1;
        else
            return -1;
    }
}