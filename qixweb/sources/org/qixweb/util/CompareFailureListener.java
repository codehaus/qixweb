package org.qixweb.util;
public interface CompareFailureListener
{
	void notifyDifferentElement(Object expected, Object actual, int elementIndex);
	void notifyElementNotPresent(Object expected);	
	void notifyDifferentLength(int expectedLength, int actualLength);
	String getFailureMessage();
}