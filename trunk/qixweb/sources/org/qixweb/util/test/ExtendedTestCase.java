package org.qixweb.util.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.qixweb.core.WebLabel;
import org.qixweb.util.StringUtil;
import org.qixweb.util.XpLogger;

public abstract class ExtendedTestCase extends TestCase
{
    private PrintStream systemErr;
    private PrintStream systemOut;
    private ByteArrayOutputStream itsGrabbedErr;
    private ByteArrayOutputStream itsGrabbedOut;

    public ExtendedTestCase()
    {
        super();
        init();
    }

    public ExtendedTestCase(String aString)
    {
        super(aString);
        init();
    }

    public static void assertDoubleEquals(String aMessage, double anExpectedDouble, double anActualDouble)
    {
        assertEquals(aMessage, anExpectedDouble, anActualDouble, 0.0);
    }

    public static void assertDoubleEquals(double anExpectedDouble, double anActualDouble)
    {
        assertEquals("", anExpectedDouble, anActualDouble, 0.0);
    }

    public static void assertNotEquals(String aMessage, Object aNotExpectedObject, Object anObjectToVerify)
    {
        assertTrue(aMessage, !aNotExpectedObject.equals(anObjectToVerify));
    }

    public static void assertNotEquals(Object aNotExpectedObject, Object anObjectToVerify)
    {
        assertTrue(!aNotExpectedObject.equals(anObjectToVerify));
    }

    public static void assertFalse(String aMessage, boolean condition)
    {
        assertTrue(aMessage, !condition);
    }

    public static void assert_contains(String aMessage, String aString, String aSubstring)
    {
        assertTrue(aMessage + ": '" + aString + "' should contains '" + aSubstring + "'", StringUtils.contains(aString, aSubstring));
    }

    public static void assertEqualsIgnoringCase(String aMessage, String expected, String actual)
    {
        assertTrue(aMessage + ": '" + expected + "' should be equals (ignoring case) to '" + actual + "'", StringUtils.equalsIgnoreCase(expected, actual));
    }

    public static void assertEqualsIgnoringCase(String expected, String actual)
    {
        assertEqualsIgnoringCase("", expected, actual);
    }

    public static void assert_matchesRegex(String aMessage, String aString, String aRegex)
    {
        assertTrue(aMessage + ": '" + aString + "' should contains regex '" + aRegex + "'", StringUtil.string_containsRegex(aString, aRegex));
    }

    public static void assert_matchesRegex(String aString, String aRegex)
    {
        assert_matchesRegex("", aString, aRegex);
    }

    public static void assert_contains(String aString, String aSubstring)
    {
        assert_contains("", aString, aSubstring);
    }

    public static void assert_notContains(String aMessage, String aString, String aSubstring)
    {
        assertFalse(aMessage, StringUtils.contains(aString, aSubstring));
    }

    public static void assert_notContains(String aString, String aSubstring)
    {
        assert_notContains("", aString, aSubstring);
    }

    public static void assertEmpty(Object[] someObjects)
    {
        assertTrue("Length is " + someObjects.length + " instead of 0", someObjects.length == 0);
    }

    public static void assertEmpty(Collection collection)
    {
        assertEmpty("", collection);
    }
    
    public static void assertEmpty(String string)
    {
        assertTrue(StringUtils.isEmpty(string));
    }    
    
    public static void assertNotEmpty(Collection collection)
    {
        assertNotEmpty("", collection);
    }
    
    public static void assertNotEmpty(String description, Collection collection)
    {
        assertFalse(description, collection.isEmpty());
    }    


    public static void assertEmpty(String aMessage, Collection collection)
    {
        assertTrue(aMessage + "   length is " + collection.size() + " instead of 0", collection.isEmpty());
    }

    public static void assertEmpty(String aMessagge, Object[] someObjects)
    {
        assertTrue(aMessagge + ": length is " + someObjects.length + " instead of 0", someObjects.length == 0);
    }

    protected void setUp() throws Exception
    {
        init();
    }

    private void init()
    {
        systemOut = System.out;
        systemErr = System.err;
    }

    protected void tearDown() throws Exception
    {
        System.setOut(systemOut);
        XpLogger.resetConsoleAppenderLogger();
        System.setErr(systemErr);
    }

    public String grabbedErr()
    {
        return itsGrabbedErr.toString();
    }

    public void grabSystemErr()
    {
        itsGrabbedErr = new ByteArrayOutputStream();
        System.setErr(new PrintStream(itsGrabbedErr));
    }

    public String grabbedOut()
    {
        return itsGrabbedOut.toString();
    }

    public void grabSystemOutResettingLogger()
    {
        grabSystemOut();
        XpLogger.resetConsoleAppenderLogger();
    }

    public void grabSystemOut()
    {
        itsGrabbedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(itsGrabbedOut));
    }

    public static void assertEqualsElementsButInDifferentOrder(List firstList, List secondList)
    {
        assertNotEquals("Lists should not be equals considering order", firstList, secondList);
        assertEqualsIgnoringOrder("Normalizing order the lists should be equal", firstList, secondList);
    }

    public void assertEquals(String message, Object[] someObjects, Object[] otherObjects)
    {
        if (someObjects.length != otherObjects.length)
            fail(message + " - lenghts are different");

        for (int i = 0; i < someObjects.length; i++)
            assertEquals(message, someObjects[i], otherObjects[i]);
    }

    public static void assertEqualsIgnoringOrder(Collection expected, Collection actual)
    {
        assertEqualsIgnoringOrder("", expected, actual);
    }

    public static void assertEqualsIgnoringOrder(String message, Collection expected, Collection actual)
    {
        assertEquals(message, new HashSet(expected), new HashSet(actual));
    }

    public static void assertEquals(String aMessage, float expected, float actual)
    {
        assertEquals(aMessage, expected, actual, 0);
    }

    public static void assertEquals(float expected, float actual)
    {
        assertEquals(expected, actual, 0);
    }

    public static void assertEmpty(WebLabel label)
    {
        assertTrue(label.isEmpty());
    }
    
}
