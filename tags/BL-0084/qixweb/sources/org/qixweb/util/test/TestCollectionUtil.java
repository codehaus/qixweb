package org.qixweb.util.test;

import java.util.*;

import org.qixweb.util.ArrayAsserter;
import org.qixweb.util.CollectionUtil;


public class TestCollectionUtil extends ExtendedTestCase
{
    
    public void testEmptyVectorToArray()
    {
        Vector emptyVector = new Vector();
        ArrayAsserter.assertEqualsIgnoringOrder(new String[0], CollectionUtil.toArray(emptyVector, String.class));
    }
    
	public void testEmptyListToArray()
	{
		List emptyList = new ArrayList();
		ArrayAsserter.assertEqualsIgnoringOrder(new String[0], CollectionUtil.toArray(emptyList, String.class));
        
        ArrayAsserter.assertEqualsIgnoringOrder(new Object[0], CollectionUtil.toArrayOnListOfSameType(emptyList));
	}
    
    public void testVectorToArray()
    {
        String[] expectedArray = new String[] { "first", "second", "third" };
        Vector vector = new Vector();
        vector.addElement(expectedArray[0]);
        vector.addElement(expectedArray[1]);
        vector.addElement(expectedArray[2]);
        ArrayAsserter.assertEqualsIgnoringOrder(expectedArray, CollectionUtil.toArray(vector, String.class));
    }
    
    public void testSetConvertedToListShouldNotImposeAnyOrder() throws Exception
    {
        LinkedHashSet set = new LinkedHashSet();
        set.add("1");
        set.add("2");
        LinkedHashSet sameSetWithDifferentIterationOrder = new LinkedHashSet();
        sameSetWithDifferentIterationOrder.add("2");
        sameSetWithDifferentIterationOrder.add("1");
        assertEqualsElementsButInDifferentOrder(CollectionUtil.toList(set), CollectionUtil.toList(sameSetWithDifferentIterationOrder));
    }
    
	public void testInvert()
	{
		String[] expectedArray = new String[] { "first", "second", "third" };
		ArrayAsserter.assertEqualsIgnoringOrder(new String[] { "third", "second", "first"}, CollectionUtil.invert(expectedArray, String.class));
	}    
    
    public void testFlatWithMonodimensionalCollections()
	{
		ArrayAsserter.assertEqualsIgnoringOrder
		(
			new Object[0], CollectionUtil.flatWithoutNulls(new Object[0], Object.class)
		);
		
		ArrayAsserter.assertEqualsIgnoringOrder
		(
			new Object[] {"qui"}, CollectionUtil.flatWithoutNulls(new Object[] {"qui"}, Object.class)
		);
	}
	
	public void testFlatExcludingNullObjects()
	{
		ArrayAsserter.assertEqualsIgnoringOrder
		(
			new Object[] {"qui"}, CollectionUtil.flatWithoutNulls(new Object[] {"qui", null}, Object.class)
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
			CollectionUtil.flatWithoutNulls(collection, String.class)
		);
	}
    
	public void testIteratorToArray()
	{
		String[] expectedArray = new String[] { "first", "second", "third" };
		Vector vector = new Vector();
		vector.addElement(expectedArray[0]);
		vector.addElement(expectedArray[1]);
		vector.addElement(expectedArray[2]);
		ArrayAsserter.assertEqualsIgnoringOrder(expectedArray, CollectionUtil.toArray(vector.iterator(), String.class));
	}
	public void testIteratorToList()
	{
		List expectedList = new ArrayList(CollectionUtil.toList(new String[] { "first", "second", "third" }));
		assertEquals(expectedList, CollectionUtil.toList(expectedList.iterator()));
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
		ArrayAsserter.assertEquals("Wrong values in the matrix", expectedMatrix, CollectionUtil.toMatrix(iteratorArray, String.class));		
		assertEquals("The allocated matrix hasn't the specified type", (new String[0][0]).getClass(), CollectionUtil.toMatrix(iteratorArray, String.class).getClass());		
	}    
    
	public void testArrayToList()
	{
		String[] array = new String[] { "first", "second", "third" };
		
		ArrayList expectedArrayList = new ArrayList();
		expectedArrayList.add(array[0]);
		expectedArrayList.add(array[1]);
		expectedArrayList.add(array[2]);
		
		assertEquals(expectedArrayList, CollectionUtil.toList(array));
						
	}    
    
	public void testListToArray()
	{
		String[] expectedArray = new String[] { "first", "second", "third" };
		List list = new ArrayList();
		list.add(expectedArray[0]);
		list.add(expectedArray[1]);
		list.add(expectedArray[2]);
		ArrayAsserter.assertEqualsIgnoringOrder(expectedArray, CollectionUtil.toArray(list, String.class));
        
        ArrayAsserter.assertEqualsIgnoringOrder(expectedArray, CollectionUtil.toArrayOnListOfSameType(list));
	}
    
    public void testListToSet()
    {
        assertEquals(new HashSet(), CollectionUtil.toSet(new ArrayList()));
        assertEquals(CollectionUtil.setWith("one"), CollectionUtil.toSet(CollectionUtil.listWith("one")));
        assertEquals(CollectionUtil.setWith("one", "two"), CollectionUtil.toSet(CollectionUtil.listWith("one", "two")));
        assertEquals(CollectionUtil.setWith("one", "two"), CollectionUtil.toSet(CollectionUtil.listWith("two", "one")));
    }    
    

    
    public void testIntArrayToIntegerList()
    {
        int[] array = new int[] { 1, 100, 3000 };
        Vector expectedVector = new Vector();
        expectedVector.addElement(new Integer(array[0]));
        expectedVector.addElement(new Integer(array[1]));
        expectedVector.addElement(new Integer(array[2]));
        assertEquals(expectedVector, CollectionUtil.toList(array));
    }
    
    public void testListCreation() throws Exception
    {
        List list = new ArrayList();
        list.add("one");
        assertEquals(list, CollectionUtil.listWith("one"));
        list.add("two");
        assertEquals(list, CollectionUtil.listWith("one", "two"));
        list.add("three");
        assertEquals(list, CollectionUtil.listWith("one", "two", "three"));
        list.add("four");
        assertEquals(list, CollectionUtil.listWith("one", "two", "three", "four"));
        list.add("five");
        assertEquals(list, CollectionUtil.listWith("one", "two", "three", "four", "five"));
        list.add("six");
        assertEquals(list, CollectionUtil.listWith("one", "two", "three", "four", "five", "six"));
    }
    
    public void testSetCreation() throws Exception
    {
        Set set = new HashSet();
        set.add("one");
        assertEquals(set, CollectionUtil.setWith("one"));
        set.add("two");
        assertEquals(set, CollectionUtil.setWith("one", "two"));
        assertEquals(set, CollectionUtil.setWith("two", "one"));
    }    
}
