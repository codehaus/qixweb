package org.qixweb.util.test;

import org.qixweb.core.WebUrl;
import org.qixweb.util.EscapedWebUrl;

public class TestEscapedWebUrl extends ExtendedTestCase
{
    public void testLabelIsEscaped()
    {
        WebUrl url = new EscapedWebUrl(new WebUrl("http://host:1234/something/servlet/XpServlet/?node=SomeNode&param=aValue"));
        assertEquals("http://host:1234/something/servlet/XpServlet/?node=SomeNode&amp;param=aValue", url.label());

        url = new EscapedWebUrl(new WebUrl("http://host:1234/something/servlet/XpServlet/?node=SomeNode&param=aValue", "Bed & Breakfast"));
        assertEquals("Bed &amp; Breakfast", url.label());
    }
    
    public void testIsNotADecorator()
    {
        WebUrl originalUrl = new WebUrl("http://www.some.url");
        WebUrl escapedUrl = new EscapedWebUrl(originalUrl);
        
        originalUrl.disable();
        
        assertLinkIsDisabled(originalUrl);
        assertLinkIsEnabled("The new url should not be affected by updates on the original", escapedUrl);        
    }
}
