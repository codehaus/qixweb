package org.qixweb.util.test;

import java.util.*;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.qixweb.core.QixwebUrl;
import org.qixweb.core.test.AnyNode;
import org.qixweb.util.CollectionUtil;

public class TestExtendedTestCase extends TestCase
{

    public void testDatesAreEqualsInRange() throws Exception
    {
        Calendar date1 = Calendar.getInstance();
        date1.setTimeInMillis(123456);

        Calendar date2 = Calendar.getInstance();
        date2.setTimeInMillis(123400);

        try
        {
            ExtendedTestCase.assertEquals(date1.getTime(), date2.getTime(), 56);
            ExtendedTestCase.assertEquals(date2.getTime(), date1.getTime(), 56);
        }
        catch (AssertionFailedError e)
        {
            fail("dates should be equal round specific precision");
        }

        try
        {
            ExtendedTestCase.assertEquals(date1.getTime(), date2.getTime(), 57);
            fail("dates should be not equal outside specific precision");
        }
        catch (AssertionFailedError expectedException)
        {
        }
    }

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
            arrayList.add("an Object");
            ExtendedTestCase.assertEmpty(arrayList);
            fail("should not be empty");
        }
        catch (AssertionFailedError e)
        {

        }
    }

    public void testNotEmptyOnCollections() throws Exception
    {
        ArrayList arrayList = new ArrayList();
        arrayList.add("an Object");
        try
        {
            ExtendedTestCase.assertNotEmpty(arrayList);
        }
        catch (AssertionFailedError e)
        {
            fail("should not be empty");
        }

        try
        {
            arrayList.remove(0);
            ExtendedTestCase.assertNotEmpty(arrayList);
            fail("should be empty");
        }
        catch (AssertionFailedError e)
        {

        }
    }

    public void testNotEmptyOnString() throws Exception
    {
        try
        {
            ExtendedTestCase.assertNotEmpty("xxx");
        }
        catch (AssertionFailedError e)
        {
            fail("should not be empty");
        }
    }

    public void testNotEmptyOnByteArray() throws Exception
    {
        try
        {
            ExtendedTestCase.assertNotEmpty(new byte[] { 1, 2, 3 });
        }
        catch (AssertionFailedError e)
        {
            fail("should not be empty");
        }

    }

    public void testEmptyByteArray() throws Exception
    {
        try
        {
            ExtendedTestCase.assertEmpty(new byte[0]);
        }
        catch (AssertionFailedError e)
        {
            fail("should be empty");
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

    public void testContainsOnCollection()
    {
        List list = CollectionUtil.listWith("first", "second");
        ExtendedTestCase.assert_contains(list, "first");
        ExtendedTestCase.assert_contains(list, "second");

        try
        {
            ExtendedTestCase.assert_contains(list, "third");
            fail("third should not be contained in the collection");
        }
        catch (AssertionFailedError expectedFailure)
        {
        }
    }

    public void testDoesNotContainOnCollection()
    {
        List list = CollectionUtil.listWith("first", "second");
        ExtendedTestCase.assert_doesNotContain(list, "third");

        try
        {

            ExtendedTestCase.assert_doesNotContain(list, "first");
            ExtendedTestCase.assert_doesNotContain(list, "second");
            fail("first and second should  be contained in the collection");
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
            ExtendedTestCase.assertEqualsIgnoringOrder("a message", CollectionUtil.listWith("primo", "secondo"), CollectionUtil.listWith("secondo", "primo"));
        }
        catch (AssertionFailedError expectedFailure)
        {
            fail();
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

    public void testAssertLinksContainLabel()
    {
        List linksList = CollectionUtil.listWith(new QixwebUrl(AnyNode.class, "aLabel"), new QixwebUrl(AnyNode.class, "anotherLabel"));
        try
        {
            ExtendedTestCase.assertLinksContainLabel(linksList, "aLabel");
            ExtendedTestCase.assertLinksContainLabel(linksList, "anotherLabel");
        }
        catch (AssertionFailedError e)
        {
            fail();
        }
        
        try
        {
            ExtendedTestCase.assertLinksContainLabel(linksList, "unexistingLabel");
            fail("should not contain unexistingLabel");
        }
        catch (AssertionFailedError e)
        {
        }
    }

}
