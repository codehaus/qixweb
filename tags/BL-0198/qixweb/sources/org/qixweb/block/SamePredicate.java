package org.qixweb.block;

public class SamePredicate implements Predicate
{
    private Object itsObjectToCompare;

    public SamePredicate(Object anObjectToCompare)
    {
        itsObjectToCompare = anObjectToCompare;
    }

    final public boolean is(Object each)
    {
        return itsObjectToCompare == each;
    }
}
