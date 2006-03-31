package org.qixweb.block;

import java.lang.reflect.Array;

public class ArrayCreator 
{
	private Object[] itsArray;
public ArrayCreator(int length, Class aClass) 
{
	itsArray = (Object[])Array.newInstance(aClass, length);
}
public Object[] create(Block aBlock)
{
	for (int i = 0; i < itsArray.length; i++)
	{
		itsArray[i] = aBlock.eval(new Integer(i));
	}
	return itsArray;
}
}
