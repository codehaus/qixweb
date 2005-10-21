package org.qixweb.util;
import java.util.*;

import org.qixweb.block.EqualsPredicate;
import org.qixweb.block.LightInternalIterator;

public class ArrayComparator
{
	private ArrayComparator()
	{
	}

	private static Integer[] toInteger(int[] someInts)
	{
		Integer[] asIntegers = new Integer[someInts.length];
		
		for (int i = 0; i < asIntegers.length; i++)
			asIntegers[i] = new Integer(someInts[i]);
	        
		return asIntegers;
	}
		
	public static boolean areEquals(int[] expectedInts, int[] actualInts)
	{
		return areEquals(expectedInts, actualInts, new DoNothingCompareFailureListener());
	}
    
	public static boolean areEquals(int[] expectedInts, int[] actualInts, CompareFailureListener aFailureListener)
	{
		return areEquals(toInteger(expectedInts), toInteger(actualInts), aFailureListener);
	}
	
	public static boolean areEquals(byte[] expected, byte[] actual, CompareFailureListener aFailureListener)
	{
		return areEquals(toByteArray(expected), toByteArray(actual), aFailureListener);
	}    

	public static boolean areEquals(final byte[] expected, final byte[] actual)
	{       
		return areEquals(expected, actual, new DoNothingCompareFailureListener());
	}

	private static Byte[] toByteArray(final byte[] someBytes)
	{
		Byte[] transformedSomeBytes = null;
        if (someBytes != null)
        {
            transformedSomeBytes = new Byte[someBytes.length];
    		for (int i = 0; i < transformedSomeBytes.length; i++)
    			transformedSomeBytes[i] = new Byte(someBytes[i]);
        }
        else
            transformedSomeBytes = new Byte[0];
        return transformedSomeBytes;
	}

	public static boolean areEquals(final Object[] someExpectedObjects, final Object[] someActualObjects, CompareFailureListener compareFailureListener)
	{
			boolean areEquals = false;			
			
			if (someExpectedObjects.length == someActualObjects.length)
			{
				areEquals = true;
				for (int i = 0; i < someExpectedObjects.length; i++)
				{
					if (!someExpectedObjects[i].equals(someActualObjects[i]))
					{
						areEquals = false;
						compareFailureListener.notifyDifferentElement(someExpectedObjects[i], someActualObjects[i], i);
						break;
					}
				}
			}
			else
				compareFailureListener.notifyDifferentLength(someExpectedObjects.length, someActualObjects.length);
				
			return areEquals;
	}
	
	public static boolean areEquals(final Object[] someExpectedObjects, final Object[] someActualObjects)
	{
		return areEquals(someExpectedObjects, someActualObjects, new DoNothingCompareFailureListener());		
	}
	
	public static boolean areEquals(final Iterator[] someExpectedObjects, final Iterator[] someActualObjects)
	{
		return areEquals(someExpectedObjects, someActualObjects, new DoNothingCompareFailureListener());
	}

	public static boolean areEquals(final Iterator[] someExpectedIterators, final Iterator[] someActualIterators, CompareFailureListener compareFailureListener)
	{
		boolean areEquals = false;

		if (someExpectedIterators.length == someActualIterators.length)
		{
			areEquals = true;
			for (int i = 0; i < someExpectedIterators.length; i++)
				areEquals = areEquals(someExpectedIterators[i], someActualIterators[i], compareFailureListener);
		}
		else
			compareFailureListener.notifyDifferentLength(someExpectedIterators.length, someActualIterators.length);

		return areEquals;
	}
	
	
	
	public static boolean areEquals(final Object[][] anExpectedMatrix, final Object[][] anActualMatrix)
	{
		return areEquals(anExpectedMatrix, anActualMatrix, new DoNothingCompareMatrixFailureListener());		
	}
	
	public static boolean areEquals(final Object[][] anExpectedMatrix, final Object[][] anActualMatrix, CompareMatrixFailureListenerWithMessage compareFailureListener)
	{
		boolean areEqual = true;
		
		if (anExpectedMatrix.length == anActualMatrix.length)
		{
			for (int i = 0; i < anExpectedMatrix.length && areEqual; i++)
			{
				compareFailureListener.setRow(i);
				areEqual = areEquals(anExpectedMatrix[i], anActualMatrix[i], compareFailureListener);
			}
		}
		else
		{		
			areEqual = false;
			compareFailureListener.notifyDifferentLength(anExpectedMatrix.length, anActualMatrix.length);
		}

		return areEqual;
	}

	public static boolean areEqualsIgnoringOrder(final Object[] someObjects, final Object[] otherObjects)
	{
		return areEqualsIgnoringOrder(someObjects, otherObjects, new DoNothingCompareFailureListener());
	}

	public static boolean areEqualsIgnoringOrder(final Object[] expectedObjects, final Object[] resultObjects, CompareFailureListener aFailureListener)
	{
		boolean areEquals = true;

		if (expectedObjects.length != resultObjects.length)
		{
			areEquals = false;
			aFailureListener.notifyDifferentLength(expectedObjects.length, resultObjects.length);
		}
		else
		{
			List present = CollectionUtil.toList(resultObjects);

			for (int i = 0; i < expectedObjects.length; i++)
			{
				if (present.contains(expectedObjects[i]))
					present.remove(expectedObjects[i]);
				else
				{
					areEquals = false;
					aFailureListener.notifyElementNotPresent(expectedObjects[i]);
					break;
				}
			}
		}
		return areEquals;
	}

	public static boolean isObjectContainedIn(final Object anObject, final Object[] someObjects)
	{
		return isObjectContainedIn(anObject, someObjects, new DoNothingCompareFailureListener());
	}
	
	public static boolean isObjectContainedIn(final Object anObject, final Object[] someObjects, CompareFailureListener aFailureListener)
	{
		LightInternalIterator theIterator = LightInternalIterator.createOn(someObjects);
		Object theObjectFound = theIterator.detect(new EqualsPredicate(anObject));
		if (theObjectFound == null)
			aFailureListener.notifyElementNotPresent(anObject);

		return theObjectFound != null;
	}

	public static boolean areEquals(Iterator expectedIterator, Iterator actualIterator)
	{
		return areEquals(expectedIterator, actualIterator, new DoNothingCompareFailureListener());
	}

	public static boolean areEquals(Iterator expectedIterator, Iterator actualIterator, CompareFailureListener compareFailureListener)
	{
		int position = 0;
		
		while (expectedIterator.hasNext() || actualIterator.hasNext())
		{
            if (!haveSameSize(expectedIterator, actualIterator, compareFailureListener, position))
                    return false;
			else 
			{
				Object elementOfFirstIterator = expectedIterator.next();
				Object elementOfSecondIterator = actualIterator.next();

				if (!elementOfFirstIterator.equals(elementOfSecondIterator))
				{
					compareFailureListener.notifyDifferentElement(elementOfFirstIterator, elementOfSecondIterator, position);
					return false;
				}
				position++;
			}
		}
		
		return true;
	}

	private static boolean haveSameSize(Iterator expectedIterator, Iterator actualIterator, CompareFailureListener compareFailureListener, int position)
    {
        if (expectedIterator.hasNext() && !actualIterator.hasNext())
        {
            compareFailureListener.notifyDifferentLength(position+1, position);
            return false;
        }
        else  if (!expectedIterator.hasNext() && actualIterator.hasNext())
        {
            compareFailureListener.notifyDifferentLength(position, position + 1);
            return false;
        }
        return true;
    }

   

	public static boolean areEqualsIgnoringOrder(int[] expectedInts, int[] actualInts, CompareFailureListenerWithMessage failureListener)
	{
		return areEqualsIgnoringOrder(toInteger(expectedInts), toInteger(actualInts), failureListener);
	}
	
	public static boolean areEqualsIgnoringOrder(int[] expectedInts, int[] actualInts)
	{
		return areEqualsIgnoringOrder(toInteger(expectedInts), toInteger(actualInts));
	}	

	public static boolean areEqualsIgnoringOrder(byte[] expectedInts, byte[] actualInts)
	{
		return areEqualsIgnoringOrder(toByteArray(expectedInts), toByteArray(actualInts));
	}

    public static boolean areEquals(List expectedList, List actualList)
    {
        return areEquals(expectedList.iterator(),actualList.iterator());
    }	
}