package org.qixweb.core.validation.test;

import junit.framework.TestCase;

import org.qixweb.core.Parameters;
import org.qixweb.core.validation.TextControl;

public class TestTextControl extends TestCase
{
    public void testIsValid() throws Exception
    {
        Parameters parameters = new Parameters();
        parameters.set("validText", "any text");
        parameters.set("emptyText", "    ");

        TextControl textControl = new TextControl(parameters);

        assertTrue(textControl.isValid("validText"));
        assertFalse(textControl.isValid("notPresent"));
        assertFalse(textControl.isValid("emptyPresent"));
    }
    
}
