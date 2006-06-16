package org.qixweb.block.test;

import java.util.ArrayList;
import java.util.Collections;

import org.qixweb.block.LightInternalIterator;
import org.qixweb.util.CollectionTransformer;


public class TestLightInternalIteratorOnEnumeration extends ParameterizedTestLightInternalIterator
{
    public LightInternalIterator createIterator(Object[] theArray)
    {
        ArrayList theList = CollectionTransformer.toArrayList(theArray);
        return LightInternalIterator.createOn(Collections.enumeration(theList));
    }
}
