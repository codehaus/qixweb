package org.qixweb.util;

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

public class ArrayAsserter
{
    private ArrayAsserter()
    {
    }

    public static void assertEquals(Object[] someExpectedData, Object[] someReceivedData)
    {
        assertEquals("", someExpectedData, someReceivedData);
    }

    public static void assertEquals(String aMessage, Iterator[] someExpectedData, Iterator[] someReceivedData)
    {
        CompareFailureListenerWithMessage failureListener = new CompareFailureListenerWithMessage();

        boolean areEquals = ArrayComparator.areEquals(someExpectedData, someReceivedData, failureListener);
        Assert.assertTrue(composeMessage(aMessage, failureListener.getFailureMessage()), areEquals);
    }

    public static void assertEquals(String aMessage, Iterator someExpectedData, Iterator someReceivedData)
    {
        CompareFailureListenerWithMessage failureListener = new CompareFailureListenerWithMessage();

        boolean areEquals = ArrayComparator.areEquals(someExpectedData, someReceivedData, failureListener);
        Assert.assertTrue(composeMessage(aMessage, failureListener.getFailureMessage()), areEquals);
    }

    public static void assertEquals(Iterator[] someExpectedData, Iterator[] someReceivedData)
    {
        assertEquals("", someExpectedData, someReceivedData);
    }

    public static void assertEquals(Iterator someExpectedData, Iterator someReceivedData)
    {
        assertEquals("", someExpectedData, someReceivedData);
    }

    public static void assertEquals(String aMessage, Object[] someExpectedData, Object[] someReceivedData)
    {
        CompareFailureListenerWithMessage failureListener = new CompareFailureListenerWithMessage();

        boolean areEquals = ArrayComparator.areEquals(someExpectedData, someReceivedData, failureListener);
        Assert.assertTrue(composeMessage(aMessage, failureListener.getFailureMessage()), areEquals);
    }

    public static void assertEquals(Object[][] someExpectedData, Object[][] someReceivedData)
    {
        assertEquals("", someExpectedData, someReceivedData);
    }

    public static void assertEquals(String aMessage, Object[][] someExpectedData, Object[][] someReceivedData)
    {
        CompareMatrixFailureListenerWithMessage failureListener = new CompareMatrixFailureListenerWithMessage();

        boolean areEquals = ArrayComparator.areEquals(someExpectedData, someReceivedData, failureListener);
        Assert.assertTrue(composeMessage(aMessage, failureListener.getFailureMessage()), areEquals);
    }

    private static String composeMessage(String aMessage, String cause)
    {
        String separator = aMessage.length() == 0 ? "" : " - ";
        String message = cause + separator + aMessage;
        return message;
    }

    public static void assertEqualsIgnoringOrder(Object[] someExpectedData, final Object[] someReceivedData)
    {
        assertEqualsIgnoringOrder("", someExpectedData, someReceivedData);
    }

    public static void assertEqualsIgnoringOrder(String aMessage, int[] someExpectedData, final int[] someReceivedData)
    {
        CompareFailureListenerWithMessage failureListener = new CompareFailureListenerWithMessage();

        boolean areEquals = ArrayComparator.areEqualsIgnoringOrder(someExpectedData, someReceivedData, failureListener);
        Assert.assertTrue(composeMessage(aMessage, failureListener.getFailureMessage()), areEquals);
    }

    public static void assertEqualsIgnoringOrder(String aMessage, Object[] someExpectedData, final Object[] someReceivedData)
    {
        CompareFailureListenerWithMessage failureListener = new CompareFailureListenerWithMessage();

        boolean areEquals = ArrayComparator.areEqualsIgnoringOrder(someExpectedData, someReceivedData, failureListener);
        Assert.assertTrue(composeMessage(aMessage, failureListener.getFailureMessage()), areEquals);
    }

    public static void assertObjectContainedIn(Object anObject, Object[] someObjects)
    {
        assertObjectContainedIn("", anObject, someObjects);
    }

    public static void assertObjectContainedIn(String aMessage, Object anObject, Object[] someObjects)
    {
        CompareFailureListenerWithMessage failureListener = new CompareFailureListenerWithMessage();

        boolean isContained = ArrayComparator.isObjectContainedIn(anObject, someObjects, failureListener);
        Assert.assertTrue(composeMessage(aMessage, failureListener.getFailureMessage()), isContained);

    }

    public static void assertObjectNotContainedIn(String aMessage, Object anObject, Object[] someObjects)
    {
        CompareFailureListenerWithMessage failureListener = new CompareFailureListenerWithMessage();

        boolean isContained = ArrayComparator.isObjectContainedIn(anObject, someObjects, failureListener);
        Assert.assertFalse(composeMessage(aMessage, anObject + " should not be present in array"), isContained);
    }

    public static void assertObjectNotContainedIn(Object anObject, Object[] someObjects)
    {
        assertObjectNotContainedIn("", anObject, someObjects);
    }

    public static void assertEquals(String aMessage, int[] expected, int[] received)
    {
        CompareFailureListenerWithMessage failureListener = new CompareFailureListenerWithMessage();

        boolean areEquals = ArrayComparator.areEquals(expected, received, failureListener);
        Assert.assertTrue(composeMessage(aMessage, failureListener.getFailureMessage()), areEquals);
    }

    public static void assertEquals(String aMessage, byte[] expected, byte[] received)
    {
        CompareFailureListenerWithMessage failureListener = new CompareFailureListenerWithMessage();

        boolean areEquals = ArrayComparator.areEquals(expected, received, failureListener);
        Assert.assertTrue(composeMessage(aMessage, failureListener.getFailureMessage()), areEquals);
    }

    public static void assertOnlyOneElementEquals(String aMessage, Object anExpectedData, Object[] someReceivedData)
    {
        assertEquals(aMessage, new Object[] { anExpectedData }, someReceivedData);
    }

    public static void assertOnlyOneElementEquals(Object anExpectedData, Object[] someReceivedData)
    {
        assertEquals("", new Object[] { anExpectedData }, someReceivedData);
    }

    public static void assertEquals(String aMessage, List expectedList, List actualList)
    {
        assertEquals(aMessage, expectedList.iterator(), actualList.iterator());
    }

    public static void assertDoesNotContainNullElements(Object[] anArray)
    {
        for (int i = 0; i < anArray.length; i++)
            Assert.assertNotNull(anArray[i]);
    }

}