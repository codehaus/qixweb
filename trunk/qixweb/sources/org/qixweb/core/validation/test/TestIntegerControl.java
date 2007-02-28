package org.qixweb.core.validation.test;

import junit.framework.TestCase;

import org.qixweb.core.Parameters;
import org.qixweb.core.validation.IntegerControl;

public class TestIntegerControl extends TestCase
{
    public void testIsValid() throws Exception
    {
        Parameters parameters = new Parameters();
        parameters.set("validInteger", "2006");
        parameters.set("invalidInteger", "not numeric");

        IntegerControl integerControl = new IntegerControl(parameters);

        assertTrue(integerControl.isValid("validInteger"));
        assertFalse(integerControl.isValid("invalidInteger"));
    }
}
