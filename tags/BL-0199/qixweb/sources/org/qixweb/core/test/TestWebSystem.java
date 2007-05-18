package org.qixweb.core.test;

import junit.framework.TestCase;

import org.qixweb.core.*;

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
        assertEquals("Initially, the user is not in the workgroup", QixwebUser.ANONYMOUS, itsSystem.userBy("name"));

        QixwebUser user = QixwebUser.createUserWith("name", "", "", "", "", "", false, true);
        itsSystem.workgroup().add(user);
        assertEquals("The user should be in the workgroup", user, itsSystem.userBy("name"));
    }
}
