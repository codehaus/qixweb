package org.qixweb.block.test;

import junit.framework.TestCase;

import org.qixweb.block.IdentityFunction;


public class TestIdentityFunction extends TestCase
{
    public void testEvalReturnsSameObject() throws Exception
    {
        Object object = new Object();
        assertSame(object, new IdentityFunction().eval(object));
    }
}
