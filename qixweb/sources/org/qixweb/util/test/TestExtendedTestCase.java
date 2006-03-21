package org.qixweb.util.test;

import java.util.ArrayList;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.qixweb.util.CollectionUtil;

public class TestExtendedTestCase extends TestCase
{

    public void testEmptyOnCollections() throws Exception
    {
        
        ArrayList arrayList = new ArrayList();
        try
        {
            ExtendedTestCase.assertEmpty(arrayList);            
        }
        catch (AssertionFailedError e)
        {
            fail("should be empty");
        }
        
        try
        {
            arrayList.add("ciccio");
            ExtendedTestCase.assertEmpty(arrayList);   
            fail("should not be empty");
        }
        catch (AssertionFailedError e)
        {
           
        }
    }
    
    public void testNotEquals()
    {
        ExtendedTestCase.assertNotEquals("The objects must be different", new Integer(2), new Integer(3));

        try
        {
            ExtendedTestCase.assertNotEquals("failure message", new Integer(2), new Integer(2));
            fail("The objects must be equal");
        }
        catch (AssertionFailedError expectedFailure)
        {
        }
    }

    public void testFalse()
    {
        ExtendedTestCase.assertFalse("The object must be false", false);

        try
        {
            ExtendedTestCase.assertFalse("message", true);
            fail("The object must be true");
        }
        catch (AssertionFailedError expectedFailure)
        {
        }
    }

    public void testContainingString()
    {
        ExtendedTestCase.assert_contains("ciao should be contained in: ciao davide", "ciao davide", "ciao");

        try
        {
            ExtendedTestCase.assert_contains("a message", "ciao davide", "pippo");
            fail("pippo should not be contained in: ciao davide");
        }
        catch (AssertionFailedError expectedFailure)
        {
        }
    }

    public void testContainingRegex()
    {
        ExtendedTestCase.assert_matchesRegex("'ci.+a' should be contained in: ciao davide", "ciao davide", "ci.+a");
        try
        {
            ExtendedTestCase.assert_matchesRegex("a message", "ciao\\davide", "\\d");
            fail("No decimal should be contained in: ciao\\davide");
        }
        catch (AssertionFailedError expectedFailure)
        {
        }
    }

    public void testAssertEqualsIgnoringOrder() throws Exception
    {
        try
        {
            // ExtendedTestCase.assertEqualsIgnoringOrder("a message", CollectionUtil.listWith("primo", "secondo"), CollectionUtil.listWith("secondo", "primo"));
        }
        catch (AssertionFailedError expectedFailure)
        {
            fail("'ci.+a' should be contained in: ciao davide");
        }
    }

    public void testAssertEqualsElementsButInDifferentOrder()
    {
        try
        {
            ExtendedTestCase.assertEqualsElementsButInDifferentOrder(CollectionUtil.listWith("primo", "secondo"), CollectionUtil.listWith("primo", "secondo"));
            fail("Should consider the two list different");
        }
        catch (AssertionFailedError e)
        {
        }
        ExtendedTestCase.assertEqualsElementsButInDifferentOrder(CollectionUtil.listWith("primo", "secondo"), CollectionUtil.listWith("secondo", "primo"));
    }

}
