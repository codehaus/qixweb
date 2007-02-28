package org.qixweb.core.validation.test;

import junit.framework.TestCase;

import org.qixweb.core.Parameters;
import org.qixweb.core.validation.ByteArrayControl;

public class TestByteArrayControl extends TestCase
{
    public void testIsValid() throws Exception
    {
        Parameters parameters = new Parameters();
        parameters.set("valid", new byte[] { 1, 2, 3 });
        parameters.set("empty", new byte[] {});

        ByteArrayControl textControl = new ByteArrayControl(parameters);

        assertTrue(textControl.isValid("valid"));
        assertFalse(textControl.isValid("notPresent"));
        assertFalse(textControl.isValid("empty"));

    }
}
