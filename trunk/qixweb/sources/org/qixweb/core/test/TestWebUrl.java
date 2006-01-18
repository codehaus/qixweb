package org.qixweb.core.test;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.qixweb.core.WebUrl;
import org.qixweb.time.QixwebDate;
import org.qixweb.util.ArrayAsserter;
import org.qixweb.util.EqualsBehaviourVerifier;
import org.qixweb.util.test.ExtendedTestCase;


public class TestWebUrl extends ExtendedTestCase
{
	private WebUrl itsUrl;

	protected void setUp() throws Exception
	{
		itsUrl = new WebUrl("http://www.google.com");
	}

	public void testAddingMapOfParametersWithAlreadyPresentValues()
	{
		itsUrl.setParameter("key1", "oldval1");
	    assertEquals(1, itsUrl.parametersLength());

		Map newParametersMap = new HashMap();
		newParametersMap.put("key1", "newval1");
		
		itsUrl.setParameters(newParametersMap);
	    assertEquals(1, itsUrl.parametersLength());
		assertEquals("newval1", itsUrl.getParameter("key1"));
	}
		
	public void testAddingMapOfParameters()
	{
		assertNull(itsUrl.getParameter("key1"));
		assertNull(itsUrl.getParameter("key2"));
		assertNull(itsUrl.getParameter("key3"));
	    assertEquals(0, itsUrl.parametersLength());
		
		Map newParametersMap = new HashMap();
		newParametersMap.put("key1", "val1");
		newParametersMap.put("key2", "val2");
		newParametersMap.put("key3", "val3");
		
		itsUrl.setParameters(newParametersMap);
	    assertEquals(3, itsUrl.parametersLength());
		assertEquals("val1", itsUrl.getParameter("key1"));
		assertEquals("val2", itsUrl.getParameter("key2"));
		assertEquals("val3", itsUrl.getParameter("key3"));
	}
    
    public void testCreateDisabledUrlInCaseOfNull() throws Exception
    {
        WebUrl url = new WebUrl(null);
        assertFalse(url.isEnabled());
        assertEquals("", url.destination());
    }

	public void testDestinationWithParameters()
	{
		WebUrl url = new WebUrl("www.myserv.com");
		url.setParameter("parameter1", "value1");
		url.setParameter("parameter2", "value2");
        url.setParameter("parameter3", 42);
	    
	    assertEquals("www.myserv.com?parameter1=value1&parameter2=value2&parameter3=42", url.destination());
        assertEquals("?parameter1=value1&parameter2=value2&parameter3=42", url.parameters());        
	}
    
	public void testConstructAutomaticallyDecodeParameters()
	{
	    WebUrl webUrl = new WebUrl("www.myserv.com?parameter1=value1+with+spaces&parameter2=+value2+with+other+spaces");
	    assertEquals(webUrl.getParameter("parameter1"), "value1 with spaces");
	    assertEquals(webUrl.getParameter("parameter2"), " value2 with other spaces");
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
		itsUrl.setParameter("parameter", "value with spaces");
		assert_contains("wrong destination composition", itsUrl.destination(), "parameter=value+with+spaces");
        assertEquals("parameter value shouldn't change", itsUrl.getParameter("parameter"), "value with spaces");

        itsUrl.setParameter("parameter", "http://www.tiscali.it");
        assert_contains("wrong destination composition", itsUrl.destination(), "parameter=http%3A%2F%2Fwww.tiscali.it");      
        itsUrl.setParameter("parameter", "/bcxt/servlet/BcxtServlet/1137489980000?command=backoffice.StrutturaSearchCommand&localita=catania&pageindex=0");
        assert_contains("wrong destination composition", itsUrl.destination(), "parameter=%2Fbcxt%2Fservlet%2FBcxtServlet%2F1137489980000%3Fcommand%3Dbackoffice.StrutturaSearchCommand%26localita%3Dcatania%26pageindex%3D0");
	}
		
	public void testEncodingParameterWithMultipleValues()
	{
		String expectedParameter1 = "parameter=value1+with+spaces";
		String expectedParameter2 = "parameter=value2+with+spaces";		

		itsUrl.setParameter("parameter", new String[] {"value1 with spaces", "value2 with spaces"});

		String returnedDestination = itsUrl.destination();
		
		assert_contains("wrong encoding of parameter1", returnedDestination, expectedParameter1);	 	
		assert_contains("wrong encoding of parameter2", returnedDestination, expectedParameter2);		
	}

	public void testManyParameters()
	{
		itsUrl.setParameter("key1", "value1");
		itsUrl.setParameter("key2", 2);
		itsUrl.setParameter("key3", "value3");
        itsUrl.setParameter("key4", true);
	    assertEquals(4, itsUrl.parametersLength());
        
		assertEquals("value1", itsUrl.getParameter("key1"));
		assertEquals(2, Integer.parseInt(itsUrl.getParameter("key2")));
		assertEquals("value3", itsUrl.getParameter("key3"));
        assertTrue(Boolean.valueOf(itsUrl.getParameter("key4")).booleanValue());
	}
    
	public void testParameterWithMultipleValues()
	{
		assertEquals("A not set parameter should return an empty array", 0, itsUrl.getParameterValuesOf("colors").length);
		
        itsUrl.setParameter("colors", "red");
        verifyColorsParameterReturns(new String[]{"red"});      

        itsUrl.setParameter("colors", new String[]{"red", "blue", "yellow"});
        verifyColorsParameterReturns(new String[]{"red", "blue", "yellow"});      
	}
    
    public void testParameterConversion()
    {
        itsUrl.setParameter("one", new Integer(1));
        assertEquals("1", itsUrl.getParameter("one"));
        itsUrl.setParameter("two", 2);
        assertEquals("2", itsUrl.getParameter("two"));

        itsUrl.setParameter("three", new Double(3));
        assertEquals("3.0", itsUrl.getParameter("three"));
        itsUrl.setParameter("fourDotFive", 4.5);
        assertEquals("4.5", itsUrl.getParameter("fourDotFive"));
        
        itsUrl.setParameter("bignumber", 13266823452345L);
        assertEquals("13266823452345", itsUrl.getParameter("bignumber"));
    }    

    private void verifyColorsParameterReturns(String[] colorArray)
    {
        assertEquals(1, itsUrl.parametersLength());
        ArrayAsserter.assertEquals("Wrong returned values", colorArray, itsUrl.getParameterValuesOf("colors"));
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

        url.setParameter("param", "value");
		sameBaseUrl.setParameter("param", "value");
		differentBaseUrl.setParameter("anotherParam", "value");
		
		EqualsBehaviourVerifier.check("url with different parameters should differ", url, sameBaseUrl, differentBaseUrl);		
	}
	
	public void testParametersBeginningWith()
	{
		itsUrl.setParameter("prefixAlfa", "valueForAlfa");
		itsUrl.setParameter("prefixBeta", "valueForBeta");
		itsUrl.setParameter("prefixGamma", "valueForGamma");
	    assertEquals(3, itsUrl.parametersLength());
		
		Map expectedMap = new HashMap();
		expectedMap.put("Alfa", "valueForAlfa");
		expectedMap.put("Beta", "valueForBeta");
		expectedMap.put("Gamma", "valueForGamma");
		
		Map map = itsUrl.parametersBeginningWith("prefix");
		assertEquals(expectedMap, map);
	}

    public void testIsEnabled()
    {
        assertTrue("By default an url is enabled", itsUrl.isEnabled());
        itsUrl.disable();
        assertFalse("The url should be disabled", itsUrl.isEnabled());
    }
    
    public void testLabelInConstruction()
    {
        WebUrl urlWithLabelInCostruction = new WebUrl("http://url.com", "label");
        assertEquals("label", urlWithLabelInCostruction.label());
        
        WebUrl urlWithoutLabelInCostruction = new WebUrl("http://url.com");
        assertEquals("If label is not specified, the label should be the url", "http://url.com", urlWithoutLabelInCostruction.label());
        
        WebUrl urlWithEmptyLabelInCostruction = new WebUrl("http://url.com", "");
        assertEquals("If given label is empty, the label should be the url", "http://url.com", urlWithEmptyLabelInCostruction.label());
        
        WebUrl urlWithNullLabelInCostruction = new WebUrl("http://url.com", null);
        assertEquals("If given label is null, the label should be the url", "http://url.com", urlWithNullLabelInCostruction.label());        
    }
    
    public class WebUrlDerived extends WebUrl
    {
        public WebUrlDerived(String anUrl)
        {
            super(anUrl);
            label("a new label");
        }
    }
    
    public void testLabelCanChangeInHierarchyConstructors()
    {
        WebUrl derived = new WebUrlDerived("http://foo.test");
        assertNotEquals("http://foo.test", derived.label());
    }

    public void testCompare() throws Exception
    {
        assertEquals(0, new WebUrl("url", "aaa").compareTo(new WebUrl("different url", "aaa")));
        assertEquals(-1, new WebUrl("url", "aaa").compareTo(new WebUrl("different url", "bbb")));
        assertEquals(1, new WebUrl("url", "bbb").compareTo(new WebUrl("different url", "aaa")));
    }
    
    public void testExtractingNullParametersAsIntCausesNumberFormatException()
    {
        try
        {
            new WebUrl("url").getParameterAsInt("k");
            fail("NumberFormatException should be raised");
        }
        catch (NumberFormatException e)
        {
            return;
        }
    }
    
    public void testExtractingParameterAsInt()
    {
        itsUrl.setParameter("key", 27);
        assertEquals(27, itsUrl.getParameterAsInt("key"));
    }

    public void testExtractingParameterAsIntWithDefault()
    {
        itsUrl.setParameter("key", "a non-numeric value");
        assertEquals(42, itsUrl.getParameterAsIntWithDefault("key", 42));
    }
    
    public void testExtractingNullParameterAsBooleanReturnsFalse()
    {
        assertFalse(new WebUrl("url").getParameterAsBoolean("k"));
    }
    
    public void testExtractingParameterAsBoolean()
    {
        itsUrl.setParameter("key", true);
        assertTrue(itsUrl.getParameterAsBoolean("key"));
        itsUrl.setParameter("key", "zfockl;");
        assertFalse(itsUrl.getParameterAsBoolean("key"));
    }
    public void testExtractingParameterAsDateWithPrefix()
    {
        itsUrl.setParameter("prefDay", 17);
        itsUrl.setParameter("prefMonth", 11);
        itsUrl.setParameter("prefYear", 1970);
        assertEquals(new QixwebDate(17, 11, 1970), itsUrl.getParameterAsDateWithPrefix("pref"));
    }
    public void testExtractingNullParameterAsDateWithPrefixCausesNumberFormatException()
    {
        try
        {
            new WebUrl("url").getParameterAsDateWithPrefix("k");
            fail("NumberFormatException should be raised");
        }
        catch (NumberFormatException e)
        {
            return;
        }
    }
    public void testExtractingParameterAsCalendarDD_MM_YYYY()
    {
        itsUrl.setParameter("key", "17/11/1970");
        assertEquals(new QixwebDate(17, 11, 1970), itsUrl.getParameterAsCalendarDD_MM_YYYY("key"));
    }
    public void testExtractingNullParameterAsCalendarDD_MM_YYYYCausesNullPointerException()
    {
        try
        {
            new WebUrl("url").getParameterAsCalendarDD_MM_YYYY("k");
            fail("NullPointerException should be raised");
        }
        catch (NullPointerException e)
        {
            return;
        }
    }
}