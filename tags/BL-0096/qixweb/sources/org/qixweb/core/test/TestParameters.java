package org.qixweb.core.test;

import java.util.*;

import org.qixweb.core.Parameters;
import org.qixweb.core.WebUrl;
import org.qixweb.time.QixwebCalendar;
import org.qixweb.time.QixwebDate;
import org.qixweb.util.*;
import org.qixweb.util.test.ExtendedTestCase;


public class TestParameters extends ExtendedTestCase
{
    private Parameters itsParameters;

	protected void setUp() throws Exception
	{
        itsParameters = new Parameters(new HashMap());
	}

	public void testAddingMapOfParametersWithAlreadyPresentValues()
	{
		itsParameters.set("key1", "oldval1");
	    assertEquals(1, itsParameters.size());

		Map newParametersMap = new HashMap();
		newParametersMap.put("key1", "newval1");
		
		itsParameters.set(newParametersMap);
	    assertEquals(1, itsParameters.size());
		assertEquals("newval1", itsParameters.get("key1"));
	}
		
	public void testAddingMapOfParameters()
	{
		assertNull(itsParameters.get("key1"));
		assertNull(itsParameters.get("key2"));
		assertNull(itsParameters.get("key3"));
	    assertEquals(0, itsParameters.size());
		
		Map newParametersMap = new HashMap();
		newParametersMap.put("key1", "val1");
		newParametersMap.put("key2", "val2");
		newParametersMap.put("key3", "val3");
		
		itsParameters.set(newParametersMap);
	    assertEquals(3, itsParameters.size());
		assertEquals("val1", itsParameters.get("key1"));
		assertEquals("val2", itsParameters.get("key2"));
		assertEquals("val3", itsParameters.get("key3"));
	}
    
	public void testAllAsString()
	{
		itsParameters.set("parameter1", "value1");
		itsParameters.set("parameter2", 55d);
        itsParameters.set("parameter3", 42);
	    
        assertEquals("?parameter1=value1&parameter2=55.0&parameter3=42", itsParameters.allAsString());        
	}
    
    public void testGet()
    {
        itsParameters.set("parameter1", "value1");

        assertEquals("value1", itsParameters.get("parameter1"));
        assertEquals("value1", itsParameters.get("parameter1", "default"));
        assertNull("default", itsParameters.get("parameter2"));
        assertEquals("default", itsParameters.get("parameter2", "default"));
    }    
    
    public void testEncodingParameters()
	{
		itsParameters.set("parameter", "value with spaces");
		assert_contains("parameter should be encoded", itsParameters.allAsString(), "parameter=value+with+spaces");
        assertEquals("parameter value shouldn't change", itsParameters.get("parameter"), "value with spaces");

        itsParameters.set("parameter", "http://www.google.it");
        assert_contains("parameter should be encoded", itsParameters.allAsString(), "parameter=http%3A%2F%2Fwww.google.it");
        
        itsParameters.set("parameter", "/webapp/servlet/Servlet/1137489980000?command=package.Command&param=value");
        assert_contains("parameter should be encoded", itsParameters.allAsString(), "parameter=%2Fwebapp%2Fservlet%2FServlet%2F1137489980000%3Fcommand%3Dpackage.Command%26param%3Dvalue");
	}
		
	public void testEncodingParameterWithMultipleValues()
	{
		String expectedParameter1 = "parameter=value1+with+spaces";
		String expectedParameter2 = "parameter=value2+with+spaces";		

		itsParameters.set("parameter", new String[] {"value1 with spaces", "value2 with spaces"});

		String returnedDestination = itsParameters.allAsString();
		
		assert_contains("wrong encoding of parameter1", returnedDestination, expectedParameter1);	 	
		assert_contains("wrong encoding of parameter2", returnedDestination, expectedParameter2);		
	}

	public void testDistinctParametersByKey()
	{
		itsParameters.set("key1", "value1");
        itsParameters.set("key2", 2);
	    assertEquals(2, itsParameters.size());
        
		assertEquals("value1", itsParameters.get("key1"));
		assertEquals(2, Integer.parseInt(itsParameters.get("key2")));
	}
    
	public void testAllValuesOf()
	{
		assertEquals("A not set parameter should return an empty array", 0, itsParameters.getAllValuesOf("colors").length);
		
        itsParameters.set("colors", "red");
        verifyColorsParameterReturns(new String[]{"red"});      

        itsParameters.set("colors", new String[]{"red", "blue", "yellow"});
        verifyColorsParameterReturns(new String[]{"red", "blue", "yellow"});      
	}

    public void testAllValuesAsIntegers()
    {
        assertEquals("A not set parameter yet should return an empty array", 0, itsParameters.getAllValuesAsIntegerOf("id").length);
        
        itsParameters.set("id", "34");
        verifyIntegerParameterReturns("id", new Integer[]{new Integer(34)});

        itsParameters.set("id", new String[]{"27", "54", "92"});
        verifyIntegerParameterReturns("id", new Integer[]{new Integer(27), new Integer(54), new Integer(92)});
    }

    public void testAllValuesAsIntegersDiscardsInvalidValues()
    {
        itsParameters.set("id", "ciao");
        verifyIntegerParameterReturns("id", new Integer[0]);

        itsParameters.set("id", new String[]{"27", "ciao", "92"});
        verifyIntegerParameterReturns("id", new Integer[]{new Integer(27), new Integer(92)});
    }
    
    public void testNumericParameterCanBeGetAsString()
    {
        itsParameters.set("one", new Integer(1));
        assertEquals("1", itsParameters.get("one"));
        itsParameters.set("two", 2);
        assertEquals("2", itsParameters.get("two"));

        itsParameters.set("three", new Double(3));
        assertEquals("3.0", itsParameters.get("three"));
        itsParameters.set("fourDotFive", 4.5);
        assertEquals("4.5", itsParameters.get("fourDotFive"));
        
        itsParameters.set("bignumber", 13266823452345L);
        assertEquals("13266823452345", itsParameters.get("bignumber"));
    }    

    private void verifyColorsParameterReturns(Object[] expectedArray)
    {
        assertEquals(1, itsParameters.size());
        ArrayAsserter.assertEqualsIgnoringOrder("Wrong returned values", expectedArray, itsParameters.getAllValuesOf("colors"));
    }
    
    private void verifyIntegerParameterReturns(String parameterName, Object[] expectedIntegers)
    {
        assertEquals(1, itsParameters.size());
        ArrayAsserter.assertEqualsIgnoringOrder("Wrong returned values", expectedIntegers, itsParameters.getAllValuesAsIntegerOf(parameterName));
    }    

				
	public void testEquals()
	{
		Parameters parameters = new Parameters();
        Parameters sameParameters = new Parameters();
        Parameters differentParameters = new Parameters();
		
        parameters.set("param", "value");
		sameParameters.set("param", "value");
		differentParameters.set("anotherParam", "value");
		
		EqualsBehaviourVerifier.check("url with different parameters should differ", parameters, sameParameters, differentParameters);		
	}
	
	public void testParametersBeginningWith()
	{
		itsParameters.set("prefixAlfa", "valueForAlfa");
		itsParameters.set("prefixBeta", "valueForBeta");
		itsParameters.set("prefixGamma", "valueForGamma");
        itsParameters.set("omega", "one without prefix");
	    assertEquals("It should not group by prefix", 4, itsParameters.size());
		
		Map expectedMap = new HashMap();
		expectedMap.put("Alfa", "valueForAlfa");
		expectedMap.put("Beta", "valueForBeta");
		expectedMap.put("Gamma", "valueForGamma");
		
		Map map = itsParameters.allBeginningWith("prefix");
		assertEquals(expectedMap, map);
	}

    public class WebUrlDerived extends WebUrl
    {
        public WebUrlDerived(String anUrl)
        {
            super(anUrl);
            label("a new label");
        }
    }
    
    public void testExtractingNullParametersAsIntCausesNumberFormatException()
    {
        try
        {
            itsParameters.getAsInt("k");
            fail("NumberFormatException should be raised");
        }
        catch (NumberFormatException e)
        {
            return;
        }
    }
    
    public void testExtractingParameterAsIntOrInteger()
    {
        itsParameters.set("key", 27);
        assertEquals(27, itsParameters.getAsInt("key"));
        assertEquals(new Integer(27), itsParameters.getAsInteger("key"));
    }
    
    public void testExtractingParameterAsIntWithInvalidValue()
    {
        try
        {
            itsParameters.getAsInt("not_existent_value");
            fail("should throw an exception");
        }
        catch (Exception expectedException) {}

        itsParameters.set("alphaKey", "ciao");
        try
        {
            itsParameters.getAsInt("alphaKey");
            fail("should throw an exception");
        }
        catch (Exception expectedException) {}
    }
    
    public void testExtractingParameterAsIntegerWithInvalidValue()
    {
        assertNull(itsParameters.getAsInteger("not_existent_value"));

        itsParameters.set("alphaKey", "ciao");
        try
        {
            itsParameters.getAsInteger("alphaKey");
            fail("should throw an exception");
        }
        catch (Exception expectedException) {}
    }

    public void testExtractingParameterAsIntOrIntegerWithDefault()
    {
        assertEquals(42, itsParameters.getAsInt("key", 42));
        assertEquals(new Integer(42), itsParameters.getAsInteger("key", new Integer(42)));

        itsParameters.set("key", "a non-numeric value");
        assertEquals(42, itsParameters.getAsInt("key", 42));
        assertEquals(new Integer(42), itsParameters.getAsInteger("key", new Integer(42)));
    }
    
    public void testExtractingNullParameterAsBooleanReturnsFalse()
    {
        assertFalse(itsParameters.getAsBoolean("k"));
    }
    
    public void testExtractingParameterAsBoolean()
    {
        itsParameters.set("key", true);
        assertTrue(itsParameters.getAsBoolean("key"));
        itsParameters.set("key", "zfockl;");
        assertFalse(itsParameters.getAsBoolean("key"));
    }
    public void testExtractingParameterAsDateWithPrefix()
    {
        itsParameters.set("prefDay", 17);
        itsParameters.set("prefMonth", 11);
        itsParameters.set("prefYear", 1970);
        assertEquals(new QixwebDate(17, 11, 1970), itsParameters.getAsDateWithPrefix("pref"));
    }
    
    public void testExtractingNullParameterAsDateWithPrefixCausesNumberFormatException()
    {
        try
        {
            itsParameters.getAsDateWithPrefix("k");
            fail("NumberFormatException should be raised");
        }
        catch (NumberFormatException e)
        {
            return;
        }
    }
    
    public void testExtractingParameterAsCalendarDD_MM_YYYY()
    {
        itsParameters.set("key", "17/11/1970");
        assertEquals(new QixwebDate(17, 11, 1970), itsParameters.getAsCalendarDD_MM_YYYY("key"));
    }
    
    public void testExtractingNullParameterAsCalendarDD_MM_YYYYReturnNullObject()
    {
        assertSame(QixwebCalendar.NULL, itsParameters.getAsCalendarDD_MM_YYYY("not_existent_parameter"));
        }
    
    public void testAdd() throws Exception
    {
        Parameters source = new Parameters().set("first", 1).set("second", new String[] { "a", "b"});
        Parameters destination = new Parameters().set("third", 3);

        destination.addExcluding(source, new HashSet());
        
        Parameters expected = new Parameters().set("first", 1).set("second", new String[] { "a", "b"}).set("third", 3);
        assertEquals(expected, destination);
    }
    
    public void testAddExcludeSpecifiedParameters() throws Exception
    {
        Parameters source = new Parameters().set("first", 1).set("firstToExclude", 2).set("secondToExclude", 3);
        Parameters destination = new Parameters();

        destination.addExcluding(source, CollectionUtil.setWith("firstToExclude", "secondToExclude"));
        
        Parameters expected = new Parameters().set("first", 1);
        assertEquals(expected, destination);
    }
    
    public void testAddOverwriteExistentParameters() throws Exception
    {
        Parameters source = new Parameters().set("first", 1);
        Parameters destination = new Parameters().set("first", 2);

        destination.addExcluding(source, new HashSet());
        
        Parameters expected = new Parameters().set("first", 1);
        assertEquals(expected, destination);
    }
}