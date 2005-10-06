package org.qixweb.core.test;

import java.util.HashMap;
import java.util.Map;

import org.qixweb.core.WebUrl;
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

	public void testDestinationWithParameters()
	{
		WebUrl url = new WebUrl("www.myserv.com");
		url.setParameter("parameter1", "value1");
		url.setParameter("parameter2", "value2");
        url.setParameter("parameter3", 42);
	    
	    assertEquals("www.myserv.com?parameter1=value1&parameter2=value2&parameter3=42", url.destination());
	}

	public void testConstructAutomaticallyDecodeParameters()
	{
	    WebUrl webUrl = new WebUrl("www.myserv.com?parameter1=value1+with+spaces&parameter2=+value2+with+other+spaces");
	    assertEquals(webUrl.getParameter("parameter1"), "value1 with spaces");
	    assertEquals(webUrl.getParameter("parameter2"), " value2 with other spaces");
	}
	
	public void testEncodingParameters()
	{
		String expectedParameter = "parameter=value+with+spaces";
	
		itsUrl.setParameter("parameter", "value with spaces");
	    
		assert_contains("wrong destination composition", itsUrl.destination(), expectedParameter);	 	
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
		
		itsUrl.setParameter("colors", new String[]{"red", "blue", "yellow"});
	    assertEquals(1, itsUrl.parametersLength());
		ArrayAsserter.assertEquals("Wrong returned values", new String[]{"red", "blue", "yellow"}, itsUrl.getParameterValuesOf("colors"));		
	}

				
	public void testEquals()
	{
		WebUrl url = new WebUrl("www.google.it");
		WebUrl sameBaseUrl = new WebUrl("www.google.it");
		WebUrl differentBaseUrl = new WebUrl("www.yahoo.com");
		
		EqualsBehaviourVerifier.check("url with different base url should differ", url, sameBaseUrl, differentBaseUrl);
        EqualsBehaviourVerifier.check("url with different base url should have different hashcodes", new Integer(url.hashCode()), new Integer(sameBaseUrl.hashCode()), new Integer(differentBaseUrl.hashCode()));
		
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

	public void testGetParameter()
	{
		WebUrl url = new WebUrl("www.myserv.com?parameter1=value1&parameter2=value2&parameter3=");
	    
	    assertEquals(2, url.parametersLength());
		assertEquals("value1", url.getParameter("parameter1"));
		assertEquals("value2", url.getParameter("parameter2"));
        assertEquals(null, url.getParameter("parameter3"));
	}

	public void testBadConstruction()
	{
	    WebUrl url = new WebUrl("asdfasd?x=&y=");
	    assertEquals(0, url.parametersLength());
	}
}
