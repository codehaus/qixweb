package org.qixweb.util;

import java.lang.reflect.Array;
import java.util.*;

import org.qixweb.block.*;

public class CollectionUtil
{
    public static Set setWith(Object anElement)
    {
        Set asSet = new HashSet(1);
        asSet.add(anElement);
        return asSet;
    }
    
    public static Set toSet(Object[] someElements)
    {
        Set asSet = new HashSet(someElements.length);
        for (int i = 0; i < someElements.length; i++)
            asSet.add(someElements[i]);

        return asSet;
    }
    
    public static Set toSet(List someElements)
    {
        Set asSet = new HashSet(someElements.size());
        for (int i = 0; i < someElements.size(); i++)
            asSet.add(someElements.get(i));

        return asSet;
    }

    public static Set setWith(Object anElement, Object anotherElement)
    {
        Set asSet = new HashSet(2);
        asSet.add(anElement);
        asSet.add(anotherElement);
        return asSet;
    }

    public static List listWith(Object aFirstObject)
    {
        List list = new ArrayList();
        list.add(aFirstObject);
        return list;
    }    

    public static List listWith(Object aFirstObject, Object aSecondObject)
    {
        List list = listWith(aFirstObject);
        list.add(aSecondObject);
        return list;
    }
    
    public static List listWith(Object aFirstObject, Object aSecondObject, Object aThirdObject)
    {
        List list = listWith(aFirstObject, aSecondObject);
        list.add(aThirdObject);
        return list;
    }
    
    public static List listWith(Object aFirstObject, Object aSecondObject, Object aThirdObject, Object aFourthObject)
    {
        List list = listWith(aFirstObject, aSecondObject, aThirdObject);
        list.add(aFourthObject);
        return list;
    }    

    public static List listWith(Object aFirstObject, Object aSecondObject, Object aThirdObject, Object aFourthObject, Object aFifthObject)
    {
        List list = listWith(aFirstObject, aSecondObject, aThirdObject, aFourthObject);
        list.add(aFifthObject);
        return list;
    }
    
    public static List listWith(Object aFirstObject, Object aSecondObject, Object aThirdObject, Object aFourthObject, Object aFifthObject, Object aSixthObject)
    {
        List list = listWith(aFirstObject, aSecondObject, aThirdObject, aFourthObject, aFifthObject);
        list.add(aSixthObject);
        return list;
    }

    public static List toList(Object[] anArray)
    {
    	return	new ArrayList(Arrays.asList(anArray));
    }

    public static List toList(Iterator anIterator)
    {
    	return toList(LightInternalIterator.createOn(anIterator).select(new AlwaysTruePredicate(), Object.class));
    }

    public static List toList(int[] anIntArray)
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

    public static Object[] toArrayOnListOfSameType(List aList)
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
    			ArrayList list = (ArrayList) aRunningValue;
    			if (each instanceof Object[])
    			{
    				Object[] subCollection = (Object[]) each;
    				list.addAll(CollectionUtil.toList(flatWithoutNulls(subCollection, commonSuperClass)));
    			}
    			else if (each != null)
    				list.add(each);
    			return list;
    		}
    	});
    
    	return CollectionUtil.toArray(accumulator, commonSuperClass);
    }

    public static Object[] invert(Object[] array, Class aType)
    {
    	int length = array.length;
    	Object[] invertedArray = (Object[])Array.newInstance(aType, length);
    
    	for (int i = 0; i < invertedArray.length; i++)
    		invertedArray[i] = array[length - i - 1];
    
    	return invertedArray;
    }

    public static List toList(Collection aCollection)
    {
        return toList(aCollection.iterator());
    }

    public static List flatToList(Collection tree)
    {
        final ArrayList result = new ArrayList();
        LightInternalIterator.createOn(tree).forEach(new Procedure()
        {
            public void run(Object each)
            {
                if (each instanceof Collection)
                    result.addAll(flatToList((Collection)each));
                else
                    result.add(each);
            }
        });
        return result;
    }    

}
