package org.qixweb.block.test;

import org.qixweb.block.IdentityFunction;

import junit.framework.TestCase;


public class TestIdentityFunction extends TestCase
{
    public void testEvalReturnsSameObject() throws Exception
    {
        Object object = new Object();
        assertSame(object, new IdentityFunction().eval(object));
    }
}
