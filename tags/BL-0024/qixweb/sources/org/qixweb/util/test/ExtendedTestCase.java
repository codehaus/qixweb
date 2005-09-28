package org.qixweb.util.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.WriterAppender;
import org.qixweb.util.StringUtil;
import org.qixweb.util.XpLogger;

import junit.framework.TestCase;


public abstract class ExtendedTestCase extends TestCase
{
	protected PrintStream systemErr;
    protected PrintStream systemOut;
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
		assertTrue(aMessage +": '" + aString + "' should contains '" + aSubstring + "'", StringUtils.contains(aString, aSubstring));
	}

	public static void assert_matchesRegex(String aMessage, String aString, String aRegex)
	{
		assertTrue(aMessage +": '" + aString + "' should contains regex '" + aRegex +"'", StringUtil.string_containsRegex(aString, aRegex));
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
		assertTrue("Length is "+someObjects.length+" instead of 0", someObjects.length == 0);
	}
	
	public static void assertEmpty(String aMessagge, Object[] someObjects)
	{
		assertTrue(aMessagge+": length is "+someObjects.length+" instead of 0", someObjects.length == 0);
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
}
