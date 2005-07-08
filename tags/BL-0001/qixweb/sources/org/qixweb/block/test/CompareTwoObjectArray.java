package org.qixweb.block.test;


public class CompareTwoObjectArray 
{
public static boolean areEqualsConsideringOrder(Object[] anObjectArray, Object[] anotherObjectArray) 
{
	boolean areEquals = true;

	if((anObjectArray != null && anotherObjectArray == null) ||
	   (anObjectArray == null && anotherObjectArray != null))
	{
		areEquals = false;
	}
	else if (anObjectArray != null && anotherObjectArray != null)
	{
		if(anObjectArray.length != anotherObjectArray.length)
				areEquals = false;
		else
			for (int i = 0; i < anObjectArray.length; i++)
			{
				if(!anObjectArray[i].equals(anotherObjectArray[i]))
					areEquals = false;
			}
	}
	return areEquals;
}
}
