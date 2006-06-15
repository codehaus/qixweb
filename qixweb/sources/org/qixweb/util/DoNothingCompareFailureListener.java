package org.qixweb.util;

public class DoNothingCompareFailureListener implements CompareFailureListener
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
