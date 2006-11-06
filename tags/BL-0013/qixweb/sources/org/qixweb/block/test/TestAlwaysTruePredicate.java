package org.qixweb.block.test;

import org.qixweb.block.AlwaysTruePredicate;
import org.qixweb.block.Predicate;

import junit.framework.TestCase;


public class TestAlwaysTruePredicate extends TestCase
{

	public void testIs()
	{
		Predicate thePredicate = new AlwaysTruePredicate();

		assertTrue("AlwaysTrueBlock should be...you guessed it...true!", thePredicate.is(null));
	}
}
