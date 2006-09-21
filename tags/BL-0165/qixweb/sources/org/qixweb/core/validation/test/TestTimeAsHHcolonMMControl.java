package org.qixweb.core.validation.test;

import junit.framework.TestCase;

import org.qixweb.core.Parameters;
import org.qixweb.core.validation.TimeAsHHcolonMMControl;

public class TestTimeAsHHcolonMMControl extends TestCase
{
    public void testIsValid() throws Exception
    {
        Parameters parameters = new Parameters();
        parameters.set("validTime", "12:30");
        parameters.set("invalidTime", "12-10");
        
        TimeAsHHcolonMMControl timeControl = new TimeAsHHcolonMMControl(parameters);

        assertTrue(timeControl.isValid("validTime"));
        assertFalse(timeControl.isValid("invalidTimealidDate"));
    }
}
