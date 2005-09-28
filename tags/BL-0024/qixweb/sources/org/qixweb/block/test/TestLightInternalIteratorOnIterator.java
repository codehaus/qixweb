package org.qixweb.block.test;

import java.util.ArrayList;

import org.qixweb.block.LightInternalIterator;
import org.qixweb.util.CollectionTransformer;


public class TestLightInternalIteratorOnIterator extends ParameterizedTestLightInternalIterator
{
	public LightInternalIterator createIterator(Object[] theArray)
	{
        ArrayList theList = CollectionTransformer.toArrayList(theArray);
		return LightInternalIterator.createOn(theList.iterator());
	}
}
