package org.qixweb.util.test;

import org.qixweb.util.ClassUtil;

public class TestClassUtil extends ExtendedTestCase
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

    public void testNewInstance() throws Exception
    {
        grabSystemOutResettingLogger();
        assertEquals(new String("bye bye"), ClassUtil.newInstance(String.class, new Class[] { String.class }, new Object[] { "bye bye" }));
        assertNull("Should return null if the requested constructor not exists", ClassUtil.newInstance(String.class, new Class[] { Double.class }, new Object[] { new Double(3.3) }));
    }

}
