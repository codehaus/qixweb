package org.qixweb.util;

import java.util.List;

public interface CompareFailureListener
{
    void notifyDifferentElement(Object expected, Object actual, int elementIndex);

    void notifyElementsNotPresent(List expected);

    void notifyDifferentLength(int expectedLength, int actualLength);

    String getFailureMessage();
}