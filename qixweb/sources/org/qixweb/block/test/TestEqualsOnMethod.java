package org.qixweb.block.test;

import junit.framework.TestCase;

import org.qixweb.block.EqualsOnMethod;

public class TestEqualsOnMethod extends TestCase
{
    public class Name
    {
        private String itsName;

        public Name(String name)
        {
            itsName = name;
        }

        public String name()
        {
            return itsName;
        }
    }

    public void testIs() throws Exception
    {
        assertFalse(new EqualsOnMethod("pippo", "name").is(new Name("pluto")));
        assertTrue(new EqualsOnMethod("pippo", "name").is(new Name("pippo")));
        try
        {
            new EqualsOnMethod("pippo", "notExistentMethod").is(new Name("pippo"));
            fail("Should throw an exception");
        }
        catch (RuntimeException e)
        {
        }
    }
}
