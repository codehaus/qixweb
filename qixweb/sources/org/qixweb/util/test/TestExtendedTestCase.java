package org.qixweb.util.test;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;


public class TestExtendedTestCase extends TestCase
{

	public TestExtendedTestCase(String arg0)
	{
		super(arg0);
	}

	public void testNotEquals()
	{
		try
		{
			ExtendedTestCase.assertNotEquals("failure message", new Integer(2), new Integer(3));
		}
		catch (AssertionFailedError failure)
		{
			fail("The objects must be different");
		}

		try
		{
			ExtendedTestCase.assertNotEquals("failure message", new Integer(2), new Integer(2));

			fail("The objects must be equal");
		}
		catch (AssertionFailedError expectedFailure)
		{
		}
	}

	public void testFalse()
	{
		try
		{
			ExtendedTestCase.assertFalse("message", false);
		}
		catch (AssertionFailedError failure)
		{
			fail("The object must be false");
		}
		
		try
		{
			ExtendedTestCase.assertFalse("message", true);
			fail("The object must be true");
		}
		catch (AssertionFailedError expectedFailure)
		{
		}
	}

	public void testContainingString()
	{
		try
		{
			ExtendedTestCase.assert_contains("a message", "ciao davide", "ciao");
		}
		catch (AssertionFailedError expectedFailure)
		{
			fail("ciao should be contained in: ciao davide");
		}

		try
		{
			ExtendedTestCase.assert_contains("a message", "ciao davide", "pippo");
			fail("pippo should not be contained in: ciao davide");
		}
		catch (AssertionFailedError expectedFailure)
		{

		}
	}

	public void testContainingRegex()
	{
		try
		{
			ExtendedTestCase.assert_matchesRegex("a message", "ciao davide", "ci.+a");
		}
		catch (AssertionFailedError expectedFailure)
		{
			fail("'ci.+a' should be contained in: ciao davide");
		}

		try
		{
			ExtendedTestCase.assert_matchesRegex("a message", "ciao\\davide", "\\d");
			fail("No decimal should be contained in: ciao\\davide");
		}
		catch (AssertionFailedError expectedFailure)
		{

		}
	}

}
