package org.qixweb.core.test;

import org.qixweb.core.WebLabel;

import junit.framework.TestCase;

public class TestWebLabel extends TestCase
{
    public void testNull() throws Exception
    {
        assertEquals("", new WebLabel(null).toString());
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
