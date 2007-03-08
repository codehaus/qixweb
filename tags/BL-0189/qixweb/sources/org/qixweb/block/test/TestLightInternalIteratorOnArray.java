package org.qixweb.block.test;

import org.qixweb.block.LightInternalIterator;

public class TestLightInternalIteratorOnArray extends AbstractTestLightInternalIterator
{
    public LightInternalIterator createIterator(Object[] theArray)
    {
        return LightInternalIterator.createOn(theArray);
    }
}
