package org.qixweb.block;

public class AlwaysFalsePredicate implements Predicate
{
	final public boolean is(Object each)
	{
		return false;
	}
}
