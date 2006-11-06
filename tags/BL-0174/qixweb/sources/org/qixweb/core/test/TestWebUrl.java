package org.qixweb.core.test;

import org.qixweb.core.WebUrl;
import org.qixweb.time.QixwebDate;
import org.qixweb.util.EqualsBehaviourVerifier;
import org.qixweb.util.test.ExtendedTestCase;


public class TestWebUrl extends ExtendedTestCase
{
	private WebUrl itsUrl;

	protected void setUp() throws Exception
	{
		itsUrl = new WebUrl("http://www.google.com");
	}

    public void testCreateDisabledUrlInCaseOfNull() throws Exception
    {
        WebUrl url = new WebUrl(null);
        assertFalse(url.isEnabled());
        assertEquals("", url.destination());
    }

	public void testDestination()
	{
		WebUrl url = new WebUrl("http://www.myserv.com");
		url.parameters().set("parameter1", "value1");
		url.parameters().set("parameter2", "value2");
        url.parameters().set("parameter3", 42);
	    
	    assertEquals("http://www.myserv.com?parameter1=value1&parameter2=value2&parameter3=42", url.destination());
        assertEquals("http://www.myserv.com?parameter1=value1&amp;parameter2=value2&amp;parameter3=42", url.encodedDestination());
	}
    
	public void testConstructAutomaticallyDecodeParameters()
	{
	    WebUrl webUrl = new WebUrl("www.myserv.com?parameter1=value1+with+spaces&parameter2=+value2+with+other+spaces");
	    assertEquals(webUrl.parameters().get("parameter1"), "value1 with spaces");
	    assertEquals(webUrl.parameters().get("parameter2"), " value2 with other spaces");
	}

    public void testSetUrlBeforeParameters()
    {
        WebUrl webUrl = new WebUrl("www.myserv.com?param1=pippo");
        assertEquals("www.myserv.com?param1=pippo", webUrl.destination());
        webUrl.setUrlBeforeParameters("www.google.com");
        assertEquals("www.google.com?param1=pippo", webUrl.destination());
        webUrl.setUrlBeforeParameters("www.tiscali.it?param2=pluto");
        assertEquals("www.tiscali.it?param1=pippo", webUrl.destination());
    }
    
	public void testEncodingParameters()
	{
        itsUrl.parameters().set("parameter", "http://www.google.it/webapp/servlet/Servlet/1137489980000?command=package.Command&param=value");
        assert_contains("wrong destination composition", itsUrl.destination(), "parameter=http%3A%2F%2Fwww.google.it%2Fwebapp%2Fservlet%2FServlet%2F1137489980000%3Fcommand%3Dpackage.Command%26param%3Dvalue");      
	}

	public void testEquals()
	{
		WebUrl url = new WebUrl("www.google.it");
		WebUrl sameBaseUrl = new WebUrl("www.google.it");
		WebUrl differentBaseUrl = new WebUrl("www.yahoo.com");
		
		EqualsBehaviourVerifier.check("url with different base url should differ", url, sameBaseUrl, differentBaseUrl);
        EqualsBehaviourVerifier.checkHashCode(url, sameBaseUrl);
		
        WebUrl disabledUrl = new WebUrl("www.google.it");   disabledUrl.disable();
        assertNotEquals("disabled url should differ", url, disabledUrl);

        url.parameters().set("param", "value");
		sameBaseUrl.parameters().set("param", "value");
		differentBaseUrl.parameters().set("anotherParam", "value");
		
		EqualsBehaviourVerifier.check("url with different parameters should differ", url, sameBaseUrl, differentBaseUrl);
        
        WebUrl sameBaseUrlWithDifferentParameters = new WebUrl("www.google.it");
        sameBaseUrlWithDifferentParameters.parameters().set("anotherParam", "value");
        
        assertNotEquals(url, sameBaseUrlWithDifferentParameters);
	}
	
	public void testIsEnabled()
    {
        assertTrue("By default an url is enabled", itsUrl.isEnabled());
        itsUrl.disable();
        assertFalse("The url should be disabled", itsUrl.isEnabled());
    }
    
    public void testLabelDetection()
    {
        assertEquals("label", new WebUrl("http://url.com", "label").label());
        assertEquals("If label is not specified, the label should be the url", "http://url.com", new WebUrl("http://url.com").label());
        assertEquals("If given label is empty, the label should be the url", "http://url.com", new WebUrl("http://url.com", "").label());
        assertEquals("If given label is null, the label should be the url", "http://url.com", new WebUrl("http://url.com", null).label());
    }

    public void testLabelIsNotEncoded()
    {
        assertEquals("Pasta & Pizza", new WebUrl("http://url.com", "Pasta & Pizza").label());
    }
    
    public void testCompare() throws Exception
    {
        assertEquals(0, new WebUrl("url", "aaa").compareTo(new WebUrl("different url", "aaa")));
        assertEquals(-1, new WebUrl("url", "aaa").compareTo(new WebUrl("different url", "bbb")));
        assertEquals(1, new WebUrl("url", "bbb").compareTo(new WebUrl("different url", "aaa")));
    }
    
    public void testExtractingParameterAsCalendarDD_MM_YYYY()
    {
        itsUrl.parameters().set("key", "17/11/1970");
        assertEquals(new QixwebDate(17, 11, 1970), itsUrl.parameters().getAsCalendarDD_MM_YYYY("key"));
    }
    
}