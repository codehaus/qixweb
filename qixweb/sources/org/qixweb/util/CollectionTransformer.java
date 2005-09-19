package org.qixweb.util;

import java.lang.reflect.Array;
import java.util.*;

import org.qixweb.block.*;


public class CollectionTransformer
{

    
    public static ArrayList toArrayList(Object[] anArray)
	{
		return	new ArrayList(Arrays.asList(anArray));
	}
    
	public static ArrayList toArrayList(Iterator anIterator)
	{
		return toArrayList(LightInternalIterator.createOn(anIterator).select(new AlwaysTruePredicate(), Object.class));
	}
	 
    public static ArrayList toList(int[] anIntArray)
    {
        Integer[] asIntegers = new Integer[anIntArray.length];
        for (int i = 0; i < asIntegers.length; i++)
            asIntegers[i] = new Integer(anIntArray[i]);
            
        return new ArrayList(Arrays.asList(asIntegers));
    }
	public static Object[] toArray(List aList, Class aType)
	{
		Object[] sameElementsArray = (Object[])Array.newInstance(aType, aList.size());
		return aList.toArray(sameElementsArray);
	}
    
    public static Object[] toArray(List aList)
    {
        if (aList.isEmpty())
            return new Object[0];
        
        Class type = aList.get(0).getClass();
        Object[] sameElementsArray = (Object[])Array.newInstance(type, aList.size());
        return aList.toArray(sameElementsArray);
    }    
	
	public static Object[] toArray(Iterator anIterator, Class eachElementType)
	{
		Vector vector = new Vector();
		while (anIterator.hasNext())
			vector.add(anIterator.next());

		return toArray(vector, eachElementType);
	}

	public static Object[][] toMatrix(Iterator[] iterators, Class eachElementType)
	{
		Object[][] matrix = (Object[][])Array.newInstance(eachElementType, new int[] {iterators.length, 0});  
		for (int i = 0; i < iterators.length; i++)
			matrix[i] = toArray(iterators[i], eachElementType);
			
		return matrix;
	}	
	
	public static Object[] flatWithoutNulls(Object[] collection, final Class commonSuperClass)
	{
		ArrayList accumulator = new ArrayList();
		LightInternalIterator.createOn(collection).sumUp(accumulator, new BinaryFunction()
		{
			public Object eval(Object aRunningValue, Object each)
			{
				ArrayList accumulator = (ArrayList) aRunningValue;
				if (each instanceof Object[])
				{
					Object[] subCollection = (Object[]) each;
					accumulator.addAll(CollectionTransformer.toArrayList(flatWithoutNulls(subCollection, commonSuperClass)));
				}
				else if (each != null)
					accumulator.add(each);
				return accumulator;
			}
		});

		return CollectionTransformer.toArray(accumulator, commonSuperClass);
	}

	public static Object[] invert(Object[] array, Class aType)
	{
		int length = array.length;
		Object[] invertedArray = (Object[])Array.newInstance(aType, length);

		for (int i = 0; i < invertedArray.length; i++)
			invertedArray[i] = array[length - i - 1];

		return invertedArray;
	}
}
