package org.qixweb.block;

public class LightInternalIteratorOnArray extends LightInternalIterator
{
    private Object[] itsObjects;
    private int cursor;

    protected LightInternalIteratorOnArray(Object[] someObjects)
    {
        super();
        itsObjects = someObjects;
        cursor = 0;
    }

    public Object currentValue()
    {
        return itsObjects[cursor++];
    }

    public boolean hasNext()
    {
        return cursor < itsObjects.length;
    }
}
