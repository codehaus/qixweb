package org.qixweb.util;

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

	public void notifyElementNotPresent(Object expected)
	{	
		itsFailureMessage = "The element " + expected + " is not present as expected";
	}

}
