package org.qixweb.block.test;

import junit.framework.TestCase;

import org.qixweb.block.Predicate;
import org.qixweb.block.SamePredicate;

public class TestSamePredicate extends TestCase
{
    public void testOkWhenSameReference()
    {
        Object theObjectToCompare = new Object();
        Predicate objectSamePredicate = new SamePredicate(theObjectToCompare);
        assertTrue("the two references must be equals", objectSamePredicate.is(theObjectToCompare));
    }

    public void testFailWhenEqualsObjectsButDifferentReferences()
    {
        String theStringToCompare = "This is a string";
        Predicate stringSamePredicate = new SamePredicate(theStringToCompare);
        // @PMD:REVIEWED:StringInstantiation: by bop on 3/8/05 5:34 PM
        assertFalse("the two reference must be different", stringSamePredicate.is(new String("This is a string")));
    }
}