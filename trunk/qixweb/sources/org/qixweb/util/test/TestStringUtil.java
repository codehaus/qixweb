package org.qixweb.util.test;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.RandomStringUtils;
import org.qixweb.util.StringUtil;

public class TestStringUtil extends ExtendedTestCase
{
    public void testFirst1024Chars()
    {
        assertEquals("anyStringWithNoMoreThan1024Chars", StringUtil.onlyFirst1024Chars("anyStringWithNoMoreThan1024Chars"));

        String stringWith1024Chars = RandomStringUtils.randomAlphabetic(1024);
        assertEquals(stringWith1024Chars, StringUtil.onlyFirst1024Chars(stringWith1024Chars));

        String stringWithMoreThan1024Chars = stringWith1024Chars + "OtherChars";
        assertEquals(stringWith1024Chars, StringUtil.onlyFirst1024Chars(stringWithMoreThan1024Chars));
    }

    public void testNthIndexOf() throws Exception
    {
        assertEquals(-1, StringUtil.nthIndexOf("ciccionissimo", 1, 'u'));

        assertEquals(1, StringUtil.nthIndexOf("ciccionissimo", 1, 'i'));
        assertEquals(4, StringUtil.nthIndexOf("ciccionissimo", 2, 'i'));
        assertEquals(7, StringUtil.nthIndexOf("ciccionissimo", 3, 'i'));
        assertEquals(10, StringUtil.nthIndexOf("ciccionissimo", 4, 'i'));
    }

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

        assertTrue("'" + aString + "'" + "should contain" + "'" + containedRegex + "'", StringUtil.string_containsRegex(aString, containedRegex));
        assertFalse("'" + aString + "'" + "should not contain" + "'" + notContainedRegex + "'", StringUtil.string_containsRegex(aString, notContainedRegex));
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
        assertEquals("anystring", StringUtil.join(new String[] { "anystring" }, ":"));
        assertEquals("Pippo:Pluto:Paperino", StringUtil.join(new String[] { "Pippo", "Pluto", "Paperino" }, ":"));
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