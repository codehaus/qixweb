package org.qixweb.util;

import junit.framework.Assert;

public class EqualsBehaviourVerifier
{
	public static void check(Object target, Object sameTarget, Object differentTarget)
	{
        check("", target, sameTarget, differentTarget);
	}
 
    public static void check(String message, Object target, Object sameTarget, Object differentTarget)
    {
        Assert.assertEquals(message + ": an object must be equals to itself", target, target);
        Assert.assertEquals(message + ": two objects with the same data must be equals", target, sameTarget);
        Assert.assertTrue(message + ": two objects with the different data must be different", !target.equals(differentTarget));
    }

    public static void checkHashCode(Object target, Object sameTarget)
    {
        Assert.assertEquals("same object must have same hash code", target.hashCode(), target.hashCode());
        Assert.assertEquals("two objects with the same data must have same hash code", target.hashCode(), sameTarget.hashCode());
        Assert.assertFalse("Comparing target with an instance of another class should return false without exceptions", target.equals(EqualsBehaviourVerifier.class));
    }
}
