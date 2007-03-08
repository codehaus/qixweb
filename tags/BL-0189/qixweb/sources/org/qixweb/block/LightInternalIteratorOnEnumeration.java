package org.qixweb.block;

import java.util.Enumeration;

public class LightInternalIteratorOnEnumeration extends LightInternalIterator
{
    private Enumeration itsObjects;
    private Object currentObject;

    protected LightInternalIteratorOnEnumeration(Enumeration someObjects)
    {
        super();
        itsObjects = someObjects;
    }

    public Object currentValue()
    {
        currentObject = itsObjects.nextElement();
        return currentObject;
    }

    public boolean hasNext()
    {
        return itsObjects.hasMoreElements();
    }

}