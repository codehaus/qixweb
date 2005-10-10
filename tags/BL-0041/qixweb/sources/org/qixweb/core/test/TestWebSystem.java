package org.qixweb.core.test;

import org.qixweb.core.*;

import junit.framework.TestCase;

public class TestWebSystem extends TestCase
{
    public static class MyWebSystem extends WebSystem
    {
        public QixwebWorkgroup workgroup()
        {
            return super.workgroup();
        }
    }
    private MyWebSystem itsSystem;

    protected void setUp() 
    {
        itsSystem = new MyWebSystem();
    }

    public void testWorkgroup()
    {
        assertNull(itsSystem.userBy("name"));
        QixwebUser user = QixwebUser.createUserWith("name", "", "", "", "", "", false, true);
        itsSystem.workgroup().add(user);
        assertEquals(user, itsSystem.userBy("name"));
    }
}
