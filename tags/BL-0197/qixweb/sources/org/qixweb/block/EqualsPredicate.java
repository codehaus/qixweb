package org.qixweb.block;

public class EqualsPredicate implements Predicate
{
    private Object itsObjectToCompare;

    public EqualsPredicate(Object anObjectToCompare)
    {
        itsObjectToCompare = anObjectToCompare;
    }

    final public boolean is(Object each)
    {
        return itsObjectToCompare.equals(each);
    }
}
