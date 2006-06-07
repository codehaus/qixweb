package org.qixweb.util.test;

import junit.framework.TestCase;

import org.qixweb.util.ClassUtil;

public class TestClassUtil extends TestCase
{

    private class Inner
    {
    }
    
    public void testFullNameOfAClass() throws Exception
    {
        assertEquals("org.qixweb.util.test.TestClassUtil", ClassUtil.fullNameOf(TestClassUtil.class));
    }
    
    public void testShortNameOfAClass() throws Exception
    {
        assertEquals("TestClassUtil", ClassUtil.shortNameOf(TestClassUtil.class));
        assertEquals("Should return the outer class short name", "TestClassUtil", ClassUtil.shortNameOf(Inner.class));
    }
    
    
}
