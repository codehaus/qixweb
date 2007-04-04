package org.qixweb.block.test;

import org.qixweb.block.LightInternalIterator;

public class TestLightInternalIteratorOnArray extends ParameterizedTestLightInternalIterator
{
	public LightInternalIterator createIterator(Object[] theArray)
	{
		return LightInternalIterator.createOn(theArray);
	}
}
