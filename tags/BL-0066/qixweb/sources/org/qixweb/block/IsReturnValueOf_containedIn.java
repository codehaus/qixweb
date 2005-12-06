package org.qixweb.block;



public class IsReturnValueOf_containedIn implements Predicate
{
	private String itsMethodToCall;
	private Object[] itsExpectedReturnedValues;

	public IsReturnValueOf_containedIn(String methodToCall, Object[] expectedReturnedValues)
	{
		itsExpectedReturnedValues = expectedReturnedValues;
		itsMethodToCall = methodToCall;
	}

	public boolean is(Object each)
	{
		CallGetter callGetter = new CallGetter(itsMethodToCall);
		Object valueToCompare = callGetter.eval(each);	

		for (int i = 0; i < itsExpectedReturnedValues.length; i++)
		{
			if (valueToCompare.equals(itsExpectedReturnedValues[i]))
				return true;
		}
		return false;
	}
}
