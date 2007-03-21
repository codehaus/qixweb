package org.qixweb.block;

public class BlockWithContext extends Block
{
    private Object itsContext;

    public BlockWithContext(Object aContext)
    {
        itsContext = aContext;
    }

    public Object context()
    {
        return itsContext;
    }
}
