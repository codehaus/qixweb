package org.qixweb.block.test;
import org.qixweb.block.*;

public class TestAndPredicate extends junit.framework.TestCase
{
	public TestAndPredicate(String name)
	{
		super(name);
	}

	public void testAnd()
	{
		assertTrue(new AndPredicate(new AlwaysTruePredicate(), new AlwaysTruePredicate()).is(new Object()));
		assertTrue(!new AndPredicate(new AlwaysTruePredicate(), new AlwaysFalsePredicate()).is(new Object()));
		assertTrue(!new AndPredicate(new AlwaysFalsePredicate(), new AlwaysTruePredicate()).is(new Object()));
		assertTrue(!new AndPredicate(new AlwaysFalsePredicate(), new AlwaysFalsePredicate()).is(new Object()));
	}

	public void testAndPassingObject()
	{
		Predicate isFive = new Predicate()
		{
			public boolean is(Object theObject)
			{
				return theObject.equals("5");
			}
		};

		Predicate isLongOne = new Predicate()
		{
			public boolean is(Object theObject)
			{
				return ((String)theObject).length() == 1;
			}
		};

		assertTrue(new AndPredicate(isFive, isLongOne).is("5"));
		assertTrue(!new AndPredicate(isFive, isLongOne).is("55"));
	}
}