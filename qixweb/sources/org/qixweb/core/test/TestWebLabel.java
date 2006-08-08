package org.qixweb.core.test;

import org.qixweb.core.WebLabel;

import junit.framework.TestCase;

public class TestWebLabel extends TestCase
{
    public void testNullIsEquivalentToEmptyString() throws Exception
    {
        assertEquals(new WebLabel(""), new WebLabel(null));
    }
    
    public void testIsNullSafe() throws Exception
    {
        Object nullObject = null;
        assertEquals(WebLabel.EMPTY, new WebLabel(nullObject));
        assertEquals(new WebLabel("123"), new WebLabel(new Integer(123)));
    }

    public void testEmpty() throws Exception
    {
        assertEquals(WebLabel.EMPTY, new WebLabel(""));
        assertTrue(WebLabel.EMPTY.isEmpty());
        assertFalse(new WebLabel("asdf").isEmpty());
        assertTrue(new WebLabel(null).isEmpty());
    }
    
    public void testEscapeHtml() throws Exception
    {
        assertEquals("&lt;&amp;", new WebLabel("<&").toString());
    }
    
    public void testCompare() throws Exception
    {
        WebLabel label = new WebLabel("first");
        WebLabel anotherLabel = new WebLabel("second");
        assertTrue(label.compareTo(anotherLabel) < 0);
        anotherLabel = new WebLabel("first");
        assertEquals(0, label.compareTo(anotherLabel));
    }
}
