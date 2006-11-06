package org.qixweb.util;

public class DoNothingCompareMatrixFailureListener extends CompareMatrixFailureListenerWithMessage
{	
	public void notifyDifferentElement(Object expected, Object actual, int elementIndex)
	{
	}

	public void notifyDifferentLength(int expectedLength, int actualLength)
	{
	}

	public void notifyElementNotPresent(Object expected)
	{
	}

	public String getFailureMessage()
	{
		return "";
	}
}
