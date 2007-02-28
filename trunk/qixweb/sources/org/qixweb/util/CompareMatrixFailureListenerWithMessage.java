package org.qixweb.util;

public class CompareMatrixFailureListenerWithMessage extends CompareFailureListenerWithMessage
{
    private int itsRow;

    public CompareMatrixFailureListenerWithMessage()
    {
        itsRow = -1;
    }

    public void setRow(int aRow)
    {
        itsRow = aRow;
    }

    public String getFailureMessage()
    {
        if (itsRow == -1)
            return super.getFailureMessage();
        else
            return super.getFailureMessage() + " on row " + itsRow;
    }
}
