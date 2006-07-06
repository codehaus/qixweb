package org.qixweb.block.test;
import org.qixweb.block.EqualsPredicate;

import junit.framework.TestCase;


public class TestEqualsPredicate extends TestCase
{

	public void testIsMethodAnalogousToEquals()
	{
		String theStringToCompare = "This is a string";
		// @PMD:REVIEWED:StringInstantiation: by bop on 3/8/05 5:35 PM
		EqualsPredicate theStringEqualsPredicate = new EqualsPredicate(new String(theStringToCompare));
		assertTrue("the two string values must be equals", theStringEqualsPredicate.is("This is a string"));

		Object theObjectToCompare = new Object();
		EqualsPredicate theObjectEqualsPredicate = new EqualsPredicate(theObjectToCompare);
		assertTrue("by default, two object are equals when have the same reference", theObjectEqualsPredicate.is(theObjectToCompare));
	}
}