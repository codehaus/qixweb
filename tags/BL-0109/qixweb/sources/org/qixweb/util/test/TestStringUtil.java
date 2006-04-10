package org.qixweb.util.test;
import java.util.ArrayList;
import java.util.Collection;

import org.qixweb.util.StringUtil;

public class TestStringUtil extends ExtendedTestCase
{
	public void testStringContainsIgnoringCase()
	{
		assertTrue("'abcdefg' should contain 'ab'", StringUtil.containsIgnoringCase("abcdefg", "ab"));
        assertTrue("'abcdefg' should contain 'aB'", StringUtil.containsIgnoringCase("abcdefg", "aB"));
        assertTrue("'abcdefg' should contain 'AB'", StringUtil.containsIgnoringCase("abcdefg", "AB"));
		assertFalse("'abcdefg' should *not* contain 'z'", StringUtil.containsIgnoringCase("abcdefg", "z"));
	}
	
	public void testStringExtractFirst()
	{
		String aString = "abc12345def";
		assertNull(StringUtil.extractFirst(aString, "\\d{10}"));
		assertEquals("12345", StringUtil.extractFirst(aString, "\\d{5}"));
	}	
	
	public void testStringContainsRegex()
	{
		String aString = "abcwob";
		String containedRegex = "c.*b";
		String notContainedRegex = "b.+c";

		assertTrue("'" +aString+ "'" + "should contain" + "'" +containedRegex+ "'", StringUtil.string_containsRegex(aString, containedRegex));
		assertFalse("'" +aString+ "'" + "should not contain" + "'" +notContainedRegex+ "'", StringUtil.string_containsRegex(aString, notContainedRegex));
	}
	
	public void testStringToReplaceNotFound()
	{
		String string = "Pippo Pluto";
		assertEquals(string, StringUtil.replace_with_in("Paperino", "Pollo", string));
	}
	
	public void testReduceMultipleWhitespacesToOne()
	{
		assertEquals("Pippo Pluto Paperino", StringUtil.compactWhitespaces("Pippo   Pluto  Paperino"));
		assertEquals("Pippo Pluto Paperino", StringUtil.compactWhitespaces("           Pippo   Pluto Paperino"));
		assertEquals("", StringUtil.compactWhitespaces(""));
		assertEquals(null, StringUtil.compactWhitespaces(null));
	}	
	
	public void testReplace()
	{
		String string = "Pippo Pluto Paperino";
		assertEquals("Pippo Pluto Pollo", StringUtil.replace_with_in("Paperino", "Pollo", string));
		
		assertEquals("Gippo Gluto Gaperino", StringUtil.replace_with_in("P", "G", string));		
		
	}	

	public void testJoin()
	{
		assertEquals("", StringUtil.join(new String[0], ":"));
		assertEquals("anystring", StringUtil.join(new String[] {"anystring"}, ":"));
		assertEquals("Pippo:Pluto:Paperino", StringUtil.join(new String[] {"Pippo", "Pluto", "Paperino"}, ":"));
	}

    public void testJoinOnCollections()
    {
        Collection aCollectionOfNames = new ArrayList();
        aCollectionOfNames.add("Pippo");
        aCollectionOfNames.add("Pluto");
        aCollectionOfNames.add("Paperino");
        assertEquals("Pippo:Pluto:Paperino", StringUtil.join(aCollectionOfNames, ":"));
    }   
}