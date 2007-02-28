package org.qixweb.core.test;

import org.qixweb.core.*;
import org.qixweb.util.test.ExtendedTestCase;

public class TestDefaultWebForm extends ExtendedTestCase
{

    private static final QixwebUser ROUSER = QixwebUser.createUserWith("name", "pwd", "", "", "a@b", "cc", false, false);
    private static final QixwebUser RWUSER = QixwebUser.createUserWith("name", "pwd", "", "", "a@b", "cc", false, true);

    public static class MyDefaultWebForm extends DefaultWebForm
    {
        public String field1 = "val1";
        public String field2 = "val2";

        public MyDefaultWebForm()
        {
        }

        public MyDefaultWebForm(QixwebUser user)
        {
            super(user);
        }

        protected QixwebUrl concreteActionUrl()
        {
            return new QixwebUrl(Object.class);
        }
    }

    private MyDefaultWebForm itsForm;

    protected void setUp() throws Exception
    {
        itsForm = new MyDefaultWebForm();
    }

    public void testEquals()
    {
        assertEquals(itsForm, new MyDefaultWebForm());
    }

    public void testToString()
    {
        assert_contains(itsForm.toString(), "val1");
        assert_contains(itsForm.toString(), "val2");
    }

    public void testDisabling()
    {
        assertTrue(itsForm.isEnabled());
        assertLinkIsEnabled(itsForm.actionUrl());

        itsForm.disable();
        assertFalse(itsForm.isEnabled());
        assertLinkIsDisabled(itsForm.actionUrl());
    }

    public void testDisableIfReadOnlyUser()
    {
        itsForm = new MyDefaultWebForm(RWUSER);
        assertTrue(itsForm.isEnabled());

        itsForm = new MyDefaultWebForm(ROUSER);
        assertFalse(itsForm.isEnabled());
    }
}
