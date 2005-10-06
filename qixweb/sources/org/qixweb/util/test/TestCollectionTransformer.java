package org.qixweb.util.test;

import java.util.*;

import org.qixweb.util.ArrayAsserter;
import org.qixweb.util.CollectionTransformer;

import junit.framework.TestCase;


public class TestCollectionTransformer extends TestCase
{
    
    public void testEmptyVectorToArray()
    {
        Vector emptyVector = new Vector();
        ArrayAsserter.assertEqualsIgnoringOrder(new String[0], CollectionTransformer.toArray(emptyVector, String.class));
    }
    
	public void testEmptyListToArray()
	{
		List emptyList = new ArrayList();
		ArrayAsserter.assertEqualsIgnoringOrder(new String[0], CollectionTransformer.toArray(emptyList, String.class));
	}
    
    public void testVectorToArray()
    {
        String[] expectedArray = new String[] { "first", "second", "third" };
        Vector vector = new Vector();
        vector.addElement(expectedArray[0]);
        vector.addElement(expectedArray[1]);
        vector.addElement(expectedArray[2]);
        ArrayAsserter.assertEqualsIgnoringOrder(expectedArray, CollectionTransformer.toArray(vector, String.class));
    }
    
	public void testInvert()
	{
		String[] expectedArray = new String[] { "first", "second", "third" };
		ArrayAsserter.assertEqualsIgnoringOrder(new String[] { "third", "second", "first"}, CollectionTransformer.invert(expectedArray, String.class));
	}    
    
    public void testFlatWithMonodimensionalCollections()
	{
		ArrayAsserter.assertEqualsIgnoringOrder
		(
			new Object[0], CollectionTransformer.flatWithoutNulls(new Object[0], Object.class)
		);
		
		ArrayAsserter.assertEqualsIgnoringOrder
		(
			new Object[] {"qui"}, CollectionTransformer.flatWithoutNulls(new Object[] {"qui"}, Object.class)
		);
	}
	
	public void testFlatExcludingNullObjects()
	{
		ArrayAsserter.assertEqualsIgnoringOrder
		(
			new Object[] {"qui"}, CollectionTransformer.flatWithoutNulls(new Object[] {"qui", null}, Object.class)
		);

	}
	
	public void testFlatWithMultidimensionalCollections()
	{
		Object[] collection = new Object[3];
		collection[0] = "qui"; 
		collection[1] = new String[] {"quo", "qua", null}; 
		collection[2] = new String[][] {{"cip", null}, {"ciop"}}; 
		
		ArrayAsserter.assertEqualsIgnoringOrder
		(
			new String[] {"qui", "quo", "qua", "cip", "ciop"},
			CollectionTransformer.flatWithoutNulls(collection, String.class)
		);
	}
    
	public void testIteratorToArray()
	{
		String[] expectedArray = new String[] { "first", "second", "third" };
		Vector vector = new Vector();
		vector.addElement(expectedArray[0]);
		vector.addElement(expectedArray[1]);
		vector.addElement(expectedArray[2]);
		ArrayAsserter.assertEqualsIgnoringOrder(expectedArray, CollectionTransformer.toArray(vector.iterator(), String.class));
	}
	public void testIteratorToArrayList()
	{
		ArrayList expectedArrayList = new ArrayList(CollectionTransformer.toArrayList(new String[] { "first", "second", "third" }));
		assertEquals(expectedArrayList, CollectionTransformer.toArrayList(expectedArrayList.iterator()));
	}
	
	public void testIteratorArrayToMatrix()
	{
		String[][] expectedMatrix = new String[][] {{ "first", "second", "third" }, { "primo", "secondo", "terzo" }};
		Vector firstRow = new Vector();
		firstRow.addElement(expectedMatrix[0][0]);
		firstRow.addElement(expectedMatrix[0][1]);
		firstRow.addElement(expectedMatrix[0][2]);
		Vector secondRow = new Vector();
		secondRow.addElement(expectedMatrix[1][0]);
		secondRow.addElement(expectedMatrix[1][1]);
		secondRow.addElement(expectedMatrix[1][2]);
		
		Iterator[] iteratorArray = new Iterator[] { firstRow.iterator(), secondRow.iterator() };
		ArrayAsserter.assertEquals("Wrong values in the matrix", expectedMatrix, CollectionTransformer.toMatrix(iteratorArray, String.class));		
		assertEquals("The allocated matrix hasn't the specified type", (new String[0][0]).getClass(), CollectionTransformer.toMatrix(iteratorArray, String.class).getClass());		
	}    
    
	public void testArrayToArrayList()
	{
		String[] array = new String[] { "first", "second", "third" };
		
		ArrayList expectedArrayList = new ArrayList();
		expectedArrayList.add(array[0]);
		expectedArrayList.add(array[1]);
		expectedArrayList.add(array[2]);
		
		assertEquals(expectedArrayList, CollectionTransformer.toArrayList(array));
						
	}    
    
	public void testListToArray()
	{
		String[] expectedArray = new String[] { "first", "second", "third" };
		List list = new ArrayList();
		list.add(expectedArray[0]);
		list.add(expectedArray[1]);
		list.add(expectedArray[2]);
		ArrayAsserter.assertEqualsIgnoringOrder(expectedArray, CollectionTransformer.toArray(list, String.class));
	}
    
    public void testIntArrayToIntegerList()
    {
        int[] array = new int[] { 1, 100, 3000 };
        Vector expectedVector = new Vector();
        expectedVector.addElement(new Integer(array[0]));
        expectedVector.addElement(new Integer(array[1]));
        expectedVector.addElement(new Integer(array[2]));
        assertEquals(expectedVector, CollectionTransformer.toList(array));
    }
    
}
