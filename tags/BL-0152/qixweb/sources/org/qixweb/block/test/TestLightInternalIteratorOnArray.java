package org.qixweb.block.test;

import org.qixweb.block.LightInternalIterator;

public class TestLightInternalIteratorOnArray extends TestLightInternalIterator
{
	public LightInternalIterator createIterator(Object[] theArray)
	{
		return LightInternalIterator.createOn(theArray);
	}
}
