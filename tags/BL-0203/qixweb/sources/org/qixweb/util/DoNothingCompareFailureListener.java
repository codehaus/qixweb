package org.qixweb.util;

import java.util.List;

public class DoNothingCompareFailureListener implements CompareFailureListener
{
    public void notifyDifferentElement(Object expected, Object actual, int elementIndex)
    {
    }

    public void notifyDifferentLength(int expectedLength, int actualLength)
    {
    }

    public void notifyElementsNotPresent(List expected)
    {
    }

    public String getFailureMessage()
    {
        return "";
    }

}
