package org.qixweb.block.test;

import java.util.ArrayList;
import java.util.Vector;

import junit.framework.TestCase;

import org.qixweb.block.*;


public class TestLightInternalIteratorCreation extends TestCase
{

	public void testOnArray()
	{
		Object[] theArray = new Object[5];

		LightInternalIterator theIterator = LightInternalIterator.createOn(theArray);
		assertTrue("The instance should be LightInternalIteratorOnArray", theIterator instanceof LightInternalIteratorOnArray);
	}
	public void testOnEnumeration()
	{
		Vector theVector = new Vector();

		LightInternalIterator theIterator = LightInternalIterator.createOn(theVector.elements());
		assertTrue("The instance should be LightInternalIteratorOnEnumeration", theIterator instanceof LightInternalIteratorOnEnumeration);
	}
	public void testOnIterator()
	{
		Vector theVector = new Vector();

		LightInternalIterator theIterator = LightInternalIterator.createOn(theVector.iterator());
		assertTrue("The instance should be LightInternalIteratorOnIterator", theIterator instanceof LightInternalIteratorOnIterator);
	}
    public void testOnCollection()
    {
        ArrayList theList = new ArrayList();

        LightInternalIterator theIterator = LightInternalIterator.createOn(theList);
        assertTrue("The instance should be LightInternalIteratorOnIterator", theIterator instanceof LightInternalIteratorOnIterator);
    }
}
