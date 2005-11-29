package org.qixweb.block.test;

import java.util.Collections;
import java.util.List;

import org.qixweb.block.LightInternalIterator;
import org.qixweb.util.CollectionUtil;


public class TestLightInternalIteratorOnEnumeration extends TestLightInternalIterator
{
    public LightInternalIterator createIterator(Object[] theArray)
    {
        List theList = CollectionUtil.toList(theArray);
        return LightInternalIterator.createOn(Collections.enumeration(theList));
    }
}
