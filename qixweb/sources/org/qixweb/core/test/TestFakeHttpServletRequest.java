package org.qixweb.core.test;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;

import org.qixweb.util.ArrayComparator;


public class TestFakeHttpServletRequest extends junit.framework.TestCase  
{

	public void testSimulatePost() throws IOException, ClassNotFoundException
	{
		Object[] theExpectedObjects = new Object[] { "Pippo", "Pappo", "15" };
		FakeHttpServletRequest theRequest = new FakeHttpServletRequest();
		theRequest.simulatePost(theExpectedObjects);
	
		ObjectInputStream theObjectInputStream = new ObjectInputStream(theRequest.getInputStream());
	
		assertTrue("The object wasn't read correctly", 
			ArrayComparator.areEquals(theExpectedObjects, (Object[])theObjectInputStream.readObject()));
	}
	
	
	public void testSimulateRequestParameters() 
	{
		Properties theExpectedParameters = new Properties();
		theExpectedParameters.setProperty("ParamName", "Pippo");
		theExpectedParameters.setProperty("ParamSurname", "Pappo");
		theExpectedParameters.setProperty("ParamAge", "15");
	
	
		FakeHttpServletRequest theRequest = new FakeHttpServletRequest(theExpectedParameters);
		
		assertEquals("Pippo", theRequest.getParameter("ParamName"));
		assertEquals("Pappo", theRequest.getParameter("ParamSurname")); 
		assertEquals("15", theRequest.getParameter("ParamAge"));
		assertNull(theRequest.getParameter("ParamAddress"));
		
	}
	
	public void testSimulateHeaderParameter()
	{
		FakeHttpServletRequest theRequest = new FakeHttpServletRequest();
		theRequest.simulateHeader("parameter", "value");
		assertEquals("value", theRequest.getHeader("parameter"));
	}
}