package org.qixweb.core.validation.test;

import junit.framework.TestCase;

import org.qixweb.core.Parameters;
import org.qixweb.core.validation.DoubleControl;

public class TestDoubleControl extends TestCase
{
    public void testIsValid() throws Exception
    {
        Parameters parameters = new Parameters();
        parameters.set("validDouble", "2.022006");
        parameters.set("invalidDouble", "not numeric");
        
        DoubleControl doubleControl = new DoubleControl(parameters);
        
        assertTrue(doubleControl.isValid("validDouble"));
        assertFalse(doubleControl.isValid("invalidDouble"));
    }
}
