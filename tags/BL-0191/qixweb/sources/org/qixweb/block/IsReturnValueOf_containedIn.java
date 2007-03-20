package org.qixweb.block;

public class IsReturnValueOf_containedIn implements Predicate
{
    private String itsMethodToCall;
    private Object[] itsValues;

    public IsReturnValueOf_containedIn(String methodToCall, Object[] someValues)
    {
        itsValues = someValues;
        itsMethodToCall = methodToCall;
    }

    public boolean is(Object each)
    {
        CallGetter callGetter = new CallGetter(itsMethodToCall);
        Object valueToCompare = callGetter.eval(each);

        for (int i = 0; i < itsValues.length; i++)
        {
            if (valueToCompare.equals(itsValues[i]))
                return true;
        }
        return false;
    }
}
