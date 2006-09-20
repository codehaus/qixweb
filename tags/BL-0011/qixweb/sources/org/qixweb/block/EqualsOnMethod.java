package org.qixweb.block;

public class EqualsOnMethod extends CallGetter implements Predicate
{
    private Object itsValueToFind;

    public EqualsOnMethod(Object valueToFind, String aMethodToCall)
    {
        super(aMethodToCall);
        itsValueToFind = valueToFind;
    }

    public boolean is(Object aEach)
    {
        return itsValueToFind.equals(eval(aEach));
    }
}
