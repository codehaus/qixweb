package org.qixweb.util.test;

import java.util.*;

import org.qixweb.util.ArrayComparator;

public class TestArrayComparator extends junit.framework.TestCase
{
    private String[][] itsDisneyFamily;

    private String[] itsQuiQuoQuaArray;

    protected void setUp()
    {
        itsQuiQuoQuaArray = new String[] { "Qui", "Quo", "Qua" };
        itsDisneyFamily = new String[][] { { "Qui", "Quo", "Qua" }, { "Minnie", "Pluto", "Paperino" } };
    }

    public void testAreEquals()
    {
        String[] sameElementsInTheSameOrder = { "Qui", "Quo", "Qua" };

        assertTrue("Arrays with the same elements and in the same order must be equal", ArrayComparator.areEquals(itsQuiQuoQuaArray, sameElementsInTheSameOrder));

        String[] sameElementsInADifferentOrder = { "Quo", "Qua", "Qui" };

        assertTrue("Arrays with the same elements but in a different order must be different", !ArrayComparator.areEquals(itsQuiQuoQuaArray, sameElementsInADifferentOrder));

        assertTrue("Two empty arrays must be equal", ArrayComparator.areEquals(new Object[0], new String[0]));

        String[] theArrayWithADifferentSize = { "Quo", "Qua", "Qui", "Pippo", "Pluto", "Paperino" };

        assertTrue("Arrays with the same elements but in a different order must be different", !ArrayComparator.areEquals(itsQuiQuoQuaArray, theArrayWithADifferentSize));
    }

    public void testAreEqualsWithNulls()
    {
        String[] elements = { "Qui", null, "Qua" };
        String[] sameElements = { "Qui", null, "Qua" };

        assertTrue(ArrayComparator.areEquals(elements, sameElements));
    }

    public void testAreEqualsOnList()
    {
        List list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");

        List sameList = new ArrayList();
        sameList.add("a");
        sameList.add("b");
        sameList.add("c");

        List listWithDifferentOrder = new ArrayList();
        listWithDifferentOrder.add("a");
        listWithDifferentOrder.add("c");
        listWithDifferentOrder.add("b");

        assertTrue("Arrays with the same elements and in the same order must be equal", ArrayComparator.areEquals(list, sameList));

        assertFalse("Arrays with the same elements but in a different order must be different", ArrayComparator.areEquals(list, listWithDifferentOrder));

        assertTrue("Two empty arrays must be equal", ArrayComparator.areEquals(new ArrayList(), new ArrayList()));

        assertTrue("Arrays with different size must be different", !ArrayComparator.areEquals(new ArrayList(), sameList));
        assertTrue("Arrays with different size must be different", !ArrayComparator.areEquals(sameList, new ArrayList()));
    }

    public void testAreEqualsOnMatrix()
    {
        String[][] sameElementsInTheSameOrder = { { "Qui", "Quo", "Qua" }, { "Minnie", "Pluto", "Paperino" } };

        assertTrue("Matrix with the same elements and in the same order must be equal", ArrayComparator.areEquals(itsDisneyFamily, sameElementsInTheSameOrder));

        String[][] sameRowsInADifferentOrder = { { "Minnie", "Pluto", "Paperino" }, { "Qui", "Quo", "Qua" } };
        assertTrue("Matrix with the rows but in a different order must be different", !ArrayComparator.areEquals(itsDisneyFamily, sameRowsInADifferentOrder));

        String[][] sameElementsInADifferentOrder = { { "Pluto", "Qui", "Qua" }, { "Minnie", "Quo", "Paperino" } };
        assertTrue("Matrix with the same elements but in a different order must be different", !ArrayComparator.areEquals(itsDisneyFamily, sameElementsInADifferentOrder));

        assertTrue("Two empty arrays must be equal", ArrayComparator.areEquals(new Object[0][0], new String[0][0]));

    }

    public void testAreEqualsWithMatrixWithDiffferentSize()
    {
        String[][] matrixWithLessNumberOfRows = { { "Qui", "Quo", "Qua" } };
        assertTrue("Matrix with less number of rows must be differerent", !ArrayComparator.areEquals(itsDisneyFamily, matrixWithLessNumberOfRows));

        String[][] matrixWithMoreNumberOfRows = { { "Qui", "Quo", "Qua" }, { "Minnie", "Pluto", "Paperino" }, { "Paperoga", "Poldp", "Nonna Papera" } };
        assertTrue("Matrix with more number of rows must be differerent", !ArrayComparator.areEquals(matrixWithMoreNumberOfRows, itsDisneyFamily));

        String[][] matrixWithADifferentNumberOfColumns = { { "Quo" }, { "Qua" }, { "Qui" } };
        assertTrue("Matrix with different number of columns must be differerent", !ArrayComparator.areEquals(itsDisneyFamily, matrixWithADifferentNumberOfColumns));
    }

    public void testAreEqualsOnBytes()
    {
        byte[] array = { 's', 'p', 'q', 'x' };
        byte[] sameElementsInTheSameOrder = { 's', 'p', 'q', 'x' };

        assertTrue("Arrays with the same elements and in the same order must be equal", ArrayComparator.areEquals(array, sameElementsInTheSameOrder));

        byte[] sameElementsInADifferentOrder = { 'q', 's', 'p', 'x' };

        assertTrue("Arrays with the same elements but in a different order must be different", !ArrayComparator.areEquals(array, sameElementsInADifferentOrder));

        assertTrue("Two empty arrays must be equal", ArrayComparator.areEquals(new byte[0], new byte[0]));

        byte[] differentSize = { 'q', 's', 'p' };

        assertTrue("Arrays with different size must be different", !ArrayComparator.areEquals(array, differentSize));
    }

    public void testAreEqualsOnInts()
    {
        int[] array = { 1, 2, 3, 4 };
        int[] sameElementsInTheSameOrder = { 1, 2, 3, 4 };

        assertTrue("Arrays with the same elements and in the same order must be equal", ArrayComparator.areEquals(array, sameElementsInTheSameOrder));

        int[] sameElementsInADifferentOrder = { 1, 4, 3, 2 };

        assertTrue("Arrays with the same elements but in a different order must be different", !ArrayComparator.areEquals(array, sameElementsInADifferentOrder));

        assertTrue("Two empty arrays must be equal", ArrayComparator.areEquals(new int[0], new int[0]));

        int[] differentSize = { 1, 2, 4 };

        assertTrue("Arrays with different size must be different", !ArrayComparator.areEquals(array, differentSize));
    }

    public void testAreEqualsOnArraysOfIterators()
    {
        ArrayList list = newAbcList();
        ArrayList sameList = newAbcList();
        ArrayList listWithDifferentOrder = newAcbList();

        assertTrue("Arrays with the same elements and in the same order must be equal", ArrayComparator.areEquals(new Iterator[] { list.iterator() }, new Iterator[] { sameList.iterator() }));

        assertFalse("Arrays with the same elements but in a different order must be different", ArrayComparator.areEquals(new Iterator[] { list.iterator() }, new Iterator[] { listWithDifferentOrder
                .iterator() }));

        assertTrue("Two empty arrays must be equal", ArrayComparator.areEquals(new Iterator[0], new Iterator[0]));

        assertTrue("Arrays with different size must be different", !ArrayComparator.areEquals(new Iterator[0], new Iterator[] { sameList.iterator() }));
    }

    private ArrayList newAbcList()
    {
        ArrayList list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        return list;
    }

    public void testAreEqualsOnIterators()
    {
        ArrayList list = newAbcList();
        ArrayList sameList = newAbcList();
        ArrayList listWithDifferentOrder = newAcbList();

        assertTrue("Arrays with the same elements and in the same order must be equal", ArrayComparator.areEquals(list.iterator(), sameList.iterator()));

        assertFalse("Arrays with the same elements but in a different order must be different", ArrayComparator.areEquals(list.iterator(), listWithDifferentOrder.iterator()));

        assertTrue("Two empty arrays must be equal", ArrayComparator.areEquals(new ArrayList().iterator(), new ArrayList().iterator()));

        assertTrue("Arrays with different size must be different", !ArrayComparator.areEquals(new ArrayList().iterator(), sameList.iterator()));
        assertTrue("Arrays with different size must be different", !ArrayComparator.areEquals(sameList.iterator(), new ArrayList().iterator()));
    }

    private ArrayList newAcbList()
    {
        ArrayList listWithDifferentOrder = new ArrayList();
        listWithDifferentOrder.add("a");
        listWithDifferentOrder.add("c");
        listWithDifferentOrder.add("b");
        return listWithDifferentOrder;
    }

    public void testAreEqualsIgnoringOrderForAnArrayWithTheSameElements()
    {
        String[] theArrayWithTheSameElements = { "Qua", "Quo", "Qui" };

        assertTrue(ArrayComparator.areEqualsIgnoringOrder(itsQuiQuoQuaArray, theArrayWithTheSameElements));
    }

    public void testAreEqualsIgnoringOrderForEmptyArray()
    {
        assertTrue(ArrayComparator.areEqualsIgnoringOrder(new String[0], new String[0]));
    }

    public void testAreEqualsIgnoringOrderForTheSameArray()
    {
        assertTrue(ArrayComparator.areEqualsIgnoringOrder(itsQuiQuoQuaArray, itsQuiQuoQuaArray));
    }

    public void testAreEqualsIgnoringOrderWithDifferentElements()
    {
        String[] arrayWithDifferentElements = { "Qua", "Quo", "Minnie" };

        assertTrue("different checked elements", !ArrayComparator.areEqualsIgnoringOrder(itsQuiQuoQuaArray, arrayWithDifferentElements));
        assertTrue("different expected elements", !ArrayComparator.areEqualsIgnoringOrder(arrayWithDifferentElements, itsQuiQuoQuaArray));
    }

    public void testAreEqualsIgnoringOrderWithDuplicatedElement()
    {
        String[] array = new String[] { "Qui", "Quo", "Qua" };
        String[] arrayWithDuplicatedElement = new String[] { "Qua", "Qua", "Quo" };

        assertFalse("duplicated element in checked", ArrayComparator.areEqualsIgnoringOrder(array, arrayWithDuplicatedElement));
        assertFalse("duplicated element in expected", ArrayComparator.areEqualsIgnoringOrder(arrayWithDuplicatedElement, array));

        array = new String[] { "Qui", "Qui", "Qua" };
        arrayWithDuplicatedElement = new String[] { "Qua", "Qua", "Qui" };

        assertFalse("duplicated element in both", ArrayComparator.areEqualsIgnoringOrder(array, arrayWithDuplicatedElement));
        assertFalse("duplicated element in both", ArrayComparator.areEqualsIgnoringOrder(arrayWithDuplicatedElement, array));
    }

    public void testAreEqualsIgnoringOrderWithOneElementLess()
    {
        String[] theArrayWithLessElements = { "Qua", "Quo" };

        assertTrue(!ArrayComparator.areEqualsIgnoringOrder(itsQuiQuoQuaArray, theArrayWithLessElements));
    }

    public void testIsObjectContained()
    {
        for (int i = 0; i < itsQuiQuoQuaArray.length; i++)
            assertTrue("the element '" + itsQuiQuoQuaArray[i] + "' must be contained", ArrayComparator.isObjectContainedIn(itsQuiQuoQuaArray[i], itsQuiQuoQuaArray));

        assertTrue("the element 'Paperino' must not be contained", !ArrayComparator.isObjectContainedIn("Paperino", itsQuiQuoQuaArray));
    }

    public void testIsObjectContainedForEmptyArray()
    {
        assertTrue("any element must not be contained in an empty array", !ArrayComparator.isObjectContainedIn("Pippo", new String[0]));
    }
}