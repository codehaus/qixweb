package org.qixweb.block.test;

import org.qixweb.block.AlwaysFalsePredicate;
import org.qixweb.block.Predicate;

import junit.framework.TestCase;


public class TestAlwaysFalsePredicate extends TestCase
{	
	public void testIs()
	{
		Predicate thePredicate = new AlwaysFalsePredicate();

		assertTrue("AlwaysFalseBlock should be...you guessed it...false!", !thePredicate.is(null));
	}
}
