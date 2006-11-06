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
        Assert.assertEquals(message + ": two objects with the same data must be equal", target, sameTarget);
        Assert.assertEquals(message + ": equality should be reflexive", sameTarget, target);
        Assert.assertTrue(message + ": two objects with different data must be different", !target.equals(differentTarget));
        Assert.assertTrue(message + ": disequality should be reflexive", !differentTarget.equals(target));
    }

    public static void checkHashCode(Object target, Object sameTarget)
    {
        Assert.assertEquals("same object must have same hash code", target.hashCode(), target.hashCode());
        Assert.assertEquals("two objects with the same data must have same hash code", target.hashCode(), sameTarget.hashCode());
        Assert.assertFalse("Comparing target with an instance of another class should return false without exceptions", target.equals(EqualsBehaviourVerifier.class));
    }
}
