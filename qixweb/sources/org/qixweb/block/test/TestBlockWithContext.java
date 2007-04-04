package org.qixweb.block.test;

import org.qixweb.block.BlockWithContext;

import junit.framework.TestCase;


public class TestBlockWithContext extends TestCase
{

	public void testConstructor()
	{
		Object theContext = new Object();
		BlockWithContext theBlockWithContext = new BlockWithContext(theContext);
		assertSame("The context must be the same passed in the constructor", theContext, theBlockWithContext.context());
	}
}
