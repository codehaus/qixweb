package org.qixweb.util;

import java.util.List;

public class CompareFailureListenerWithMessage implements CompareFailureListener
{
	private String itsFailureMessage ="pippo";
	
	public void notifyDifferentElement(Object expected, Object actual, int elementIndex)
	{
		itsFailureMessage = "The " + elementIndex + "-element is different, expected " + expected + " but was " + actual;
	}
	
	public void notifyDifferentLength(int expectedLength, int actualLength)
	{
		itsFailureMessage = "Lengths are different, expected " + expectedLength + " but was " + actualLength;
	}	
	
	

	public String getFailureMessage()
	{
		return itsFailureMessage;
	}

	public void notifyElementsNotPresent(List expected)
	{	
		itsFailureMessage = "The elements " + expected + " are not present as expected";
	}

}
