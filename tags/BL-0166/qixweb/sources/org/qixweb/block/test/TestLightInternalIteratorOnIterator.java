package org.qixweb.block.test;

import java.util.List;

import org.qixweb.block.LightInternalIterator;
import org.qixweb.util.CollectionUtil;


public class TestLightInternalIteratorOnIterator extends AbstractTestLightInternalIterator
{
	public LightInternalIterator createIterator(Object[] theArray)
	{
        List theList = CollectionUtil.toList(theArray);
		return LightInternalIterator.createOn(theList.iterator());
	}
}
