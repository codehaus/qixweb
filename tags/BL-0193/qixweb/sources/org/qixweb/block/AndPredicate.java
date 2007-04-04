package org.qixweb.block;

public class AndPredicate implements Predicate
{

    private Predicate itsSecondPredicate;

    private Predicate itsFirstPredicate;

    public AndPredicate(Predicate firstPredicate, Predicate secondPredicate)
    {
        itsFirstPredicate = firstPredicate;
        itsSecondPredicate = secondPredicate;
    }

    public boolean is(Object theObject)
    {
        return itsFirstPredicate.is(theObject) && itsSecondPredicate.is(theObject);
    }

}
