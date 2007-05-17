package org.qixweb.util.test;

import java.util.ArrayList;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.qixweb.util.ArrayAsserter;

public class TestArrayAsserter extends TestCase
{
    private String[] itsQuiQuoQuaArray;
    private String[] itsQuiQuiQuaArray;
    private ArrayList itsList;
    private ArrayList itsSameList;
    private ArrayList itsListWithDifferentOrder;
    private ArrayList itsEmptyList;

    protected void setUp()
    {
        itsQuiQuoQuaArray = new String[] { "Qui", "Quo", "Qua" };
        itsQuiQuiQuaArray = new String[] { "Qui", "Qui", "Qua" };

        itsEmptyList = new ArrayList();

        itsList = new ArrayList();
        itsList.add("a");
        itsList.add("b");
        itsList.add("c");

        itsSameList = new ArrayList();
        itsSameList.add("a");
        itsSameList.add("b");
        itsSameList.add("c");

        itsListWithDifferentOrder = new ArrayList();
        itsListWithDifferentOrder.add("a");
        itsListWithDifferentOrder.add("c");
        itsListWithDifferentOrder.add("b");
    }

    public void testAssertDoesNotContainNullElements()
    {
        try
        {
            ArrayAsserter.assertDoesNotContainNullElements(new Object[] { null });
        }
        catch (AssertionFailedError err)
        {
            assertTrue(true);
        }
        try
        {
            ArrayAsserter.assertDoesNotContainNullElements(new Object[] { "aString", null });
        }
        catch (AssertionFailedError err)
        {
            assertTrue(true);
        }
        try
        {
            ArrayAsserter.assertDoesNotContainNullElements(new Object[] { "aString", "anotherString" });
        }
        catch (AssertionFailedError err)
        {
            fail();
        }
    }

    public void testAreEqualsIgnoringOrderWithDuplicatedElement()
    {
        String[] sameElementsInADifferentOrder = { "Qui", "Qua", "Qui" };
        ArrayAsserter.assertEqualsIgnoringOrder(itsQuiQuiQuaArray, sameElementsInADifferentOrder);

        boolean expectedFailureHasOccured = false;
        try
        {
            String[] duplicatedElement = new String[] { "Qua", "Qua", "Quo" };

            ArrayAsserter.assertEqualsIgnoringOrder(itsQuiQuoQuaArray, duplicatedElement);
        }
        catch (AssertionFailedError err)
        {
            expectedFailureHasOccured = true;
            assertEquals("The elements [Qui] are not present as expected", err.getMessage());
        }
        assertTrue("The assert must fail", expectedFailureHasOccured);
    }

    public void testAreNotEqualsIgnoringOrderWithDuplicatedElement()
    {
        boolean expectedFailureHasOccured = false;
        try
        {
            String[] arrayWithDuplicatedElement = new String[] { "Qua", "Qua", "Qui" };

            ArrayAsserter.assertEqualsIgnoringOrder(itsQuiQuiQuaArray, arrayWithDuplicatedElement);
        }
        catch (AssertionFailedError err)
        {
            expectedFailureHasOccured = true;
            assertEquals("The elements [Qui] are not present as expected", err.getMessage());
        }
        assertTrue("The assert must fail", expectedFailureHasOccured);
    }

    public void testAreNotEqualsIgnoringOrder()
    {
        boolean expectedFailureHasOccured = false;
        try
        {
            String[] expectedArray = new String[] { "Qui" };
            String[] resultedArray = new String[] { "Qua" };

            ArrayAsserter.assertEqualsIgnoringOrder(expectedArray, resultedArray);
        }
        catch (AssertionFailedError err)
        {
            expectedFailureHasOccured = true;
            assertEquals("The elements [Qui] are not present as expected", err.getMessage());
        }
        assertTrue("The assert must fail", expectedFailureHasOccured);
    }

    public void testDifferentLengthElementsIgnoringOrder()
    {
        boolean expectedFailureHasOccured = false;
        try
        {
            String[] theArrayWithMoreElements = { "Qua", "Quo", "Qua", "nonna papera" };

            ArrayAsserter.assertEqualsIgnoringOrder(itsQuiQuoQuaArray, theArrayWithMoreElements);
        }
        catch (AssertionFailedError err)
        {
            expectedFailureHasOccured = true;
            assertEquals("Lengths are different, expected 3 but was 4", err.getMessage());
        }
        assertTrue("The assert must fail", expectedFailureHasOccured);
    }

    public void testAreEqualsIgnoringOrder()
    {
        String[] sameElementsInADifferentOrder = { "Quo", "Qua", "Qui" };
        ArrayAsserter.assertEqualsIgnoringOrder(itsQuiQuoQuaArray, sameElementsInADifferentOrder);

        boolean expectedFailureHasOccured = false;
        try
        {
            String[] differentWithSameLength = { "Quo", "Quo", "Quo" };
            ArrayAsserter.assertEqualsIgnoringOrder(itsQuiQuoQuaArray, differentWithSameLength);
        }
        catch (AssertionFailedError err)
        {
            expectedFailureHasOccured = true;
            assertEquals("The elements [Qui, Qua] are not present as expected", err.getMessage());
        }
        assertTrue("The assert must fail", expectedFailureHasOccured);
    }

    public void testDifferentLengthElements()
    {
        String[] theArrayWithLessElements = { "Qua", "Quo" };
        boolean expectedFailureHasOccured = false;
        try
        {
            ArrayAsserter.assertEquals(itsQuiQuoQuaArray, theArrayWithLessElements);
        }
        catch (AssertionFailedError err)
        {
            expectedFailureHasOccured = true;
            assertEquals("Lengths are different, expected 3 but was 2", err.getMessage());
        }
        assertTrue("The assert must fail", expectedFailureHasOccured);

        expectedFailureHasOccured = false;
        try
        {
            ArrayAsserter.assertEquals("Different lengths", itsQuiQuoQuaArray, theArrayWithLessElements);
        }
        catch (AssertionFailedError err)
        {
            expectedFailureHasOccured = true;
            assertEquals("Lengths are different, expected 3 but was 2 - Different lengths", err.getMessage());
        }
        assertTrue("The assert must fail", expectedFailureHasOccured);
    }

    public void testAreEquals()
    {
        String[] sameElementsInTheSameOrder = { "Qui", "Quo", "Qua" };
        ArrayAsserter.assertEquals("They must be equals", itsQuiQuoQuaArray, sameElementsInTheSameOrder);

        String[] sameElementsInADifferentOrder = { "Quo", "Qua", "Qui" };
        boolean expectedFailureHasOccured = false;
        try
        {
            ArrayAsserter.assertEquals("Different order", itsQuiQuoQuaArray, sameElementsInADifferentOrder);
        }
        catch (AssertionFailedError err)
        {
            expectedFailureHasOccured = true;
            assertEquals("The 0-element is different, expected Qui but was Quo - Different order", err.getMessage());
        }
        assertTrue("The assert must fail", expectedFailureHasOccured);

        expectedFailureHasOccured = false;
        try
        {
            ArrayAsserter.assertEquals(itsQuiQuoQuaArray, sameElementsInADifferentOrder);
        }
        catch (AssertionFailedError err)
        {
            expectedFailureHasOccured = true;
            assertEquals("The 0-element is different, expected Qui but was Quo", err.getMessage());
        }
        assertTrue("The assert must fail", expectedFailureHasOccured);
    }

    public void testAreEqualsOnList()
    {
        ArrayAsserter.assertEquals("Lists with the same elements and in the same order must be equal", itsList, itsSameList);

        try
        {
            ArrayAsserter.assertEquals("Different order", itsList, itsListWithDifferentOrder);
            fail("The assert must fail");
        }
        catch (AssertionFailedError err)
        {
            assertEquals("The 1-element is different, expected b but was c - Different order", err.getMessage());
        }

        ArrayAsserter.assertEquals("Two empty arrays must be equal", itsEmptyList, itsEmptyList);

        try
        {
            ArrayAsserter.assertEquals("Different size", itsEmptyList, itsSameList);
            fail("The assert must fail");
        }
        catch (AssertionFailedError err)
        {
            assertEquals("Lengths are different, expected 0 but was 1 - Different size", err.getMessage());
        }

        try
        {
            ArrayAsserter.assertEquals("Different size", itsSameList, itsEmptyList);
            fail("The assert must fail");
        }
        catch (AssertionFailedError err)
        {
            assertEquals("Lengths are different, expected 1 but was 0 - Different size", err.getMessage());
        }
    }

    public void testAreEqualsOnIterator()
    {
        ArrayAsserter.assertEquals("Lists with the same elements and in the same order must be equal", itsList.iterator(), itsSameList.iterator());

        try
        {
            ArrayAsserter.assertEquals("Different order", itsList.iterator(), itsListWithDifferentOrder.iterator());
            fail("The assert must fail");
        }
        catch (AssertionFailedError err)
        {
            assertEquals("The 1-element is different, expected b but was c - Different order", err.getMessage());
        }

        ArrayAsserter.assertEquals("Two empty arrays must be equal", itsEmptyList.iterator(), itsEmptyList.iterator());

        try
        {
            ArrayAsserter.assertEquals("Different size", itsEmptyList.iterator(), itsSameList.iterator());
            fail("The assert must fail");
        }
        catch (AssertionFailedError err)
        {
            assertEquals("Lengths are different, expected 0 but was 1 - Different size", err.getMessage());
        }

        try
        {
            ArrayAsserter.assertEquals("Different size", itsSameList.iterator(), itsEmptyList.iterator());
            fail("The assert must fail");
        }
        catch (AssertionFailedError err)
        {
            assertEquals("Lengths are different, expected 1 but was 0 - Different size", err.getMessage());
        }

    }

    public void testAreEqualsOnMatrix()
    {
        String[][] disneyFamily = new String[][] { { "Qui", "Quo", "Qua" }, { "Minnie", "Pluto", "Paperino" } };
        String[][] matrixWithTheSameElementsInTheSameOrder = { disneyFamily[0], disneyFamily[1] };
        ArrayAsserter.assertEquals("They must be equals", disneyFamily, matrixWithTheSameElementsInTheSameOrder);

        String[][] matrixWithSameRowsInADifferentOrder = { disneyFamily[1], disneyFamily[0] };
        try
        {
            ArrayAsserter.assertEquals("Different order", disneyFamily, matrixWithSameRowsInADifferentOrder);
            fail("The assert must fail");
        }
        catch (AssertionFailedError err)
        {
            assertEquals("The 0-element is different, expected Qui but was Minnie on row 0 - Different order", err.getMessage());
        }

        String[][] matrixWithDifferentNumberOfRows = { disneyFamily[0] };
        try
        {
            ArrayAsserter.assertEquals("Different order", disneyFamily, matrixWithDifferentNumberOfRows);
            fail("The assert must fail");
        }
        catch (AssertionFailedError err)
        {
            assertEquals("Lengths are different, expected 2 but was 1 - Different order", err.getMessage());
        }
    }

    public void testOnlyOneElementEquals()
    {
        String[] array = { "Qui" };

        ArrayAsserter.assertOnlyOneElementEquals("There must be only one element", "Qui", array);
        boolean expectedFailureHasOccured = false;
        try
        {
            ArrayAsserter.assertOnlyOneElementEquals("There must not be the element", "Quo", array);
        }
        catch (AssertionFailedError err)
        {
            expectedFailureHasOccured = true;
            assertEquals("The 0-element is different, expected Quo but was Qui - There must not be the element", err.getMessage());
        }
        assertTrue("The assert must fail", expectedFailureHasOccured);
    }

    public void testAssertObjectContainedIn()
    {
        String[] array = { "Qui", "Quo", "Qua" };

        ArrayAsserter.assertObjectContainedIn("Element must be present", "Quo", array);

        boolean expectedFailureHasOccured = false;
        try
        {
            ArrayAsserter.assertObjectContainedIn("Element must be present", "pippo", array);
        }
        catch (AssertionFailedError err)
        {
            expectedFailureHasOccured = true;
            assertEquals("The elements [pippo] are not present as expected - Element must be present", err.getMessage());
        }

        assertTrue("The assert must fail", expectedFailureHasOccured);
    }

    public void testAssertObjectNotContainedIn()
    {
        String[] array = { "Qui", "Quo", "Qua" };

        ArrayAsserter.assertObjectNotContainedIn("Element must not be present", "Pippo", array);

        boolean expectedFailureHasOccured = false;
        try
        {
            ArrayAsserter.assertObjectNotContainedIn("Element must not be present", "Quo", array);
        }
        catch (AssertionFailedError err)
        {
            expectedFailureHasOccured = true;
            assertEquals("Quo should not be present in array - Element must not be present", err.getMessage());
        }
        assertTrue("The assert must fail", expectedFailureHasOccured);
    }

}