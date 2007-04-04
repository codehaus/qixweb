package org.qixweb.block;

import java.util.Iterator;

public class LightInternalIteratorOnIterator extends LightInternalIterator
{
	private Iterator itsObjects;

















	private Object currentObject;

protected LightInternalIteratorOnIterator(Iterator someObjects) 
{
	super();
	itsObjects = someObjects;
}
public Object currentValue()
{
	currentObject = itsObjects.next();
	return currentObject;
}
public boolean hasNext()
{
	return itsObjects.hasNext();
}
}
