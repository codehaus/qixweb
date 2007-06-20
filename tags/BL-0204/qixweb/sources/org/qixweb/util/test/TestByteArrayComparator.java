package org.qixweb.util.test;

import org.qixweb.util.ArrayComparator;

public class TestByteArrayComparator extends junit.framework.TestCase
{

    private byte[] itsByteArray;

    protected void setUp()
    {
        itsByteArray = new byte[] { 12, 34, 56, 78 };
    }

    public void testAreEqualsConsideringOrderForEmptyByteArray()
    {
        assertTrue(ArrayComparator.areEquals(new byte[0], new byte[0]));
    }

    public void testAreEqualsConsideringOrderForNullByteArray()
    {
        assertTrue(ArrayComparator.areEquals((byte[]) null, null));

        assertTrue(!ArrayComparator.areEquals(null, itsByteArray));

        assertTrue(!ArrayComparator.areEquals(itsByteArray, null));
    }

    public void testAreEqualsConsideringOrderForTheSameByteArray()
    {
        assertTrue(ArrayComparator.areEquals(itsByteArray, itsByteArray));
    }

    public void testAreEqualsConsideringOrderWithDifferentByteElements()
    {
        byte[] theDifferentByteArray = new byte[] { 12, 34, 56, 90 };
        assertTrue(!ArrayComparator.areEquals(itsByteArray, theDifferentByteArray));
    }

    public void testAreEqualsConsideringOrderWithDifferentNumberOfByteElements()
    {
        byte[] theShorterByteArray = new byte[] { 12, 34, 78 };
        assertTrue(!ArrayComparator.areEquals(itsByteArray, theShorterByteArray));

        byte[] theLongerByteArray = new byte[] { 12, 34, 56, 78, 90 };
        assertTrue(!ArrayComparator.areEquals(itsByteArray, theLongerByteArray));
    }

    public void testAreEqualsConsideringOrderWithDifferentOrder()
    {
        byte[] theSameByteArrayWithDifferentOrder = new byte[] { 34, 56, 12, 78 };
        assertTrue(!ArrayComparator.areEquals(itsByteArray, theSameByteArrayWithDifferentOrder));
    }

    public void testAreEqualsConsideringOrderWithTheSameByteElements()
    {
        byte[] theSameByteArray = new byte[] { 12, 34, 56, 78 };
        assertTrue(ArrayComparator.areEquals(itsByteArray, theSameByteArray));
    }
}
