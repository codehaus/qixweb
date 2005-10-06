package org.qixweb.time.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.qixweb.time.QixwebCalendar;
import org.qixweb.time.QixwebDate;
import org.qixweb.time.QixwebTime;
import org.qixweb.util.EqualsBehaviourVerifier;
import org.qixweb.util.test.ExtendedTestCase;

public abstract class TestQixwebCalendar extends ExtendedTestCase
{

    protected abstract QixwebCalendar concreteInstance(int day, int month, int year);

    protected abstract QixwebCalendar concreteInstance(Calendar aCalendar);

    public void testAdd()
    {
        Calendar javaCalendar = new GregorianCalendar(1999, 11, 31, 0, 0, 0);
        QixwebCalendar ourCalendar = concreteInstance(31, 12, 1999);
        javaCalendar.add(Calendar.DAY_OF_MONTH, 1);
        QixwebCalendar oneDayLater = ourCalendar.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(concreteInstance(javaCalendar), oneDayLater);
        assertSame(ourCalendar.getClass(), oneDayLater.getClass());
    }

    public void testEqualsFalseWithNull()
    {
        QixwebCalendar anyDate = concreteInstance(22, 2, 2004);
        assertNotEquals(anyDate, null);
    }
    
    public void testEqualsFalseWithNonAbstractCalendarDate()
    {
        QixwebCalendar anyDate = concreteInstance(22, 2, 2004);
        assertNotEquals(anyDate, new Object());
    }
    
    public void testDifferentSubtypesCanBeCompared()
    {
        QixwebDate aDate = new QixwebDate(1, 2, 2003);
        QixwebTime sameDateTime = new QixwebTime(1, 2, 2003, 0, 0, 0);
        QixwebTime anotherDateTime = new QixwebTime(1, 2, 2003, 0, 0, 1);
        EqualsBehaviourVerifier.check(aDate, sameDateTime, anotherDateTime);
    }
    
    public void testFormatting()
    {
        QixwebCalendar aDate = concreteInstance(11, 2, 2003);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ITALY);
        assertEquals("11-feb-2003", aDate.format(dateFormat));
    }
    
    public void testToDate()
    {
        GregorianCalendar aCalendar = new GregorianCalendar(2003, 1, 11);
        QixwebCalendar aDate = concreteInstance(11, 2, 2003);
        assertEquals(aCalendar.getTime(), aDate.getTime());
    }
    
    public void testNull()
    {
        assertEquals(new GregorianCalendar(0,0,0), QixwebCalendar.NULL.toGregorianCalendar());
        assertTrue(concreteInstance(1,1,1).after(QixwebCalendar.NULL));
        assertEquals(QixwebCalendar.NULL.toString(), QixwebCalendar.NULL.key());
    }
    
    public void testAfter()
    {
        QixwebCalendar date = concreteInstance(1, 2, 2003);
        QixwebCalendar subsequentDate = date.add(Calendar.DAY_OF_MONTH, 1);
        assertTrue(subsequentDate.after(date));
        assertFalse(date.after(subsequentDate));
    }

    public void testBefore()
    {
        QixwebCalendar date = concreteInstance(1, 2, 2003);
        QixwebCalendar subsequentDate = date.add(Calendar.DAY_OF_MONTH, 1);
        assertTrue(date.before(subsequentDate));
        assertFalse(subsequentDate.before(date));
    }

    public void testAddDoesNotChangeOriginal()
    {
        QixwebCalendar aDay = concreteInstance(1, 2, 2003);
        aDay.add(Calendar.HOUR_OF_DAY, 100);
        assertEquals(concreteInstance(1, 2, 2003), aDay);
    }
}
