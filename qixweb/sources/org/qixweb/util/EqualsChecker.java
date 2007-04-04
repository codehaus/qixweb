package org.qixweb.util;

public class EqualsChecker 
{
static public boolean runConsideringNullOn(Object anObject, Object anotherObject) 
{
	boolean returnValue;
	if (anObject == null && anotherObject == null)
		returnValue = true;
	else if (anObject != null && anotherObject == null)
		returnValue = false;
	else if (anObject == null && anotherObject != null)
		returnValue = false;
	else
		returnValue = anObject.equals(anotherObject);

	return returnValue;
}
}
