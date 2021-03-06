package org.qixweb.util.test;

import junit.framework.TestCase;

import org.qixweb.util.EqualsChecker;

public class TestEqualsChecker extends TestCase
{

    public void testEqualsConsideringNull()
    {
        String anyObject = "pollo";

        assertTrue("with itself", EqualsChecker.runConsideringNullOn(anyObject, anyObject));
        assertTrue("with null", EqualsChecker.runConsideringNullOn(null, null));
        assertTrue("object - null", !EqualsChecker.runConsideringNullOn(anyObject, null));
        assertTrue("null - object", !EqualsChecker.runConsideringNullOn(null, anyObject));
    }
}