package org.qixweb.block;

public class AlwaysTruePredicate implements Predicate 
{
	final public boolean is(Object each)
	{
		return true;
	}
}
