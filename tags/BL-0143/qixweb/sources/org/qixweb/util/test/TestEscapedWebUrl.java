package org.qixweb.util.test;

import junit.framework.TestCase;

import org.qixweb.core.WebUrl;
import org.qixweb.util.EscapedWebUrl;

public class TestEscapedWebUrl extends TestCase
{
    public void testLabelIsEscaped()
    {
        WebUrl url = new EscapedWebUrl(new WebUrl("http://host:1234/something/servlet/XpServlet/?node=SomeNode&param=aValue"));
        assertEquals("http://host:1234/something/servlet/XpServlet/?node=SomeNode&amp;param=aValue", url.label());

        url = new EscapedWebUrl(new WebUrl("http://host:1234/something/servlet/XpServlet/?node=SomeNode&param=aValue", "Questa � accentata"));
        assertEquals("Questa &egrave; accentata", url.label());
    }
    
    public void testIsNotADecorator()
    {
        WebUrl originalUrl = new WebUrl("http://www.some.url");
        WebUrl escapedUrl = new EscapedWebUrl(originalUrl);
        
        originalUrl.disable();
        
        assertFalse(originalUrl.isEnabled());
        assertTrue("The new url should not be affected by updates on the original", escapedUrl.isEnabled());        
    }
}
