package org.qixweb.util.test;

import junit.framework.TestCase;

import org.qixweb.util.ClassUtil;

public class TestClassUtil extends TestCase
{
    public void testFullNameOfAClass() throws Exception
    {
        assertEquals("org.qixweb.util.test.TestClassUtil", ClassUtil.fullNameOf(TestClassUtil.class));
    }
    
    public void testShortNameOfAClass() throws Exception
    {
        assertEquals("TestClassUtil", ClassUtil.shortNameOf(TestClassUtil.class));
    }

}
