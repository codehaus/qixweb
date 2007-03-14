package org.qixweb.core.validation.test;

import junit.framework.TestCase;

import org.qixweb.core.Parameters;
import org.qixweb.core.validation.DateAsDDslashMMslashYYYYControl;

public class TestDateAsDDslashMMslashYYYYControl extends TestCase
{
    public void testIsValid() throws Exception
    {
        Parameters parameters = new Parameters();
        parameters.set("validDate", "02/02/2006");
        parameters.set("invalidDate", "30-40--50");

        DateAsDDslashMMslashYYYYControl dateControl = new DateAsDDslashMMslashYYYYControl(parameters);

        assertTrue(dateControl.isValid("validDate"));
        assertFalse(dateControl.isValid("invalidDate"));
    }
}
